package com.tour;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.config.ConfigData;
import com.dump.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

import redis.clients.jedis.Jedis;

public class TournamentAction extends ActionSupport implements ServletResponseAware, ServletRequestAware, SessionAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SessionMap<String, Object> sessionMap;
	private String data, title, date, status, monthname, time, type, charid, tourid;
	private int amount, people;
	private float perkill, perfees, create_amt, balance, dinner, cmpnyfees;

	public Jedis red = new Jedis("localhost");

	@Override
	public String execute() throws Exception {

		String reqMethod = request.getMethod();
		if (reqMethod.equals("PUT")) {
			regisTour();
		}
		if (reqMethod.equals("POST")) {

			createTour();
		}
		if (reqMethod.equals("GET")) {
			getTournament();
		}
		if (reqMethod.equals("DELETE")) {
			getPartis();
		}

//		System.out.println("#### Session Check: name " + sessionMap.get("name"));
//		System.out.println("#### Session Check: log " + sessionMap.get("log"));
//		System.out.println("#### Session Check: charid " + sessionMap.get("charid"));
		return NONE;
	}

	private void getPartis() {

		try {
			String tourid = (String) sessionMap.get("tourid");
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			String sql = "select u.name, u.char_id, t.tour_id, t.kill from user_details u "
					+ "join tourregis_details t " + "on u.char_id = t.char_id where t.tour_id = " + tourid;

			ResultSet rs = st.executeQuery(sql);

			if (rs != null) {
				JSONArray arr = new JSONArray();
				JSONObject obj;
				while (rs.next()) {
					obj = new JSONObject();

					obj.put("name", rs.getString("name"));
					obj.put("charid", rs.getString("char_id"));
					obj.put("kill", rs.getString("kill"));

					arr.add(obj);
				}
				this.getResponse().getWriter().println(arr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void regisTour() throws Exception {

		String tourid = "", charid = "";

		tourid = (String) sessionMap.get("tourid");
		charid = (String) sessionMap.get("charid");

		if (!charid.equals("0")) {
			Random rand = new Random();
			boolean exist = false;

			try {
				Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				String sql = "Select * from tourregis_details where " + "tour_id = " + tourid + " and char_id = "
						+ charid;
				ResultSet rs = st.executeQuery(sql);
				if (rs.next()) {
					exist = true;
				} else {
					exist = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!exist) {
				int kill = rand.nextInt(5);

				int wamount = Integer.parseInt(getPerkill(tourid));
				int people = Integer.parseInt(getPeople(tourid));
				int useramt = Integer.parseInt(getWinAmt(charid));
				people = people + 1;
				if (people > 101) {
					this.getResponse().getWriter().print("Tournament was Filled");
					return;
				}
				wamount = wamount * kill;
				useramt = useramt + wamount;
				Connection con = DBConnection.getConnection();
				try {
					con.setAutoCommit(false);

					Statement st = con.createStatement();
					String insertsql = "Insert into tourregis_details values (" + tourid + "," + charid + "," + kill
							+ ")";
//					System.out.println("Regis: " + insertsql);
					int val1 = st.executeUpdate(insertsql);
					if (val1 != 0) {
//						System.out.println("Insert");
						String updateuser = "Update user_details set winamt = " + useramt + " " + "where char_id = "
								+ charid;

//						System.out.println("User: " + updateuser);
						int val2 = st.executeUpdate(updateuser);
						if (val2 != 0) {
//							System.out.println("User Updated");
							String updatepeople = "Update tournament_details set people = " + people + " "
									+ " where tour_id = " + tourid;
//							System.out.println("People: " + updatepeople);

							int val3 = st.executeUpdate(updatepeople);
							if (val3 != 0) {
//								System.out.println("People Updated");
								con.commit();
								this.getResponse().getWriter().print("success");
							} else {
								con.rollback();
								this.getResponse().getWriter().print("failed");
							}
						} else {
							con.rollback();
							this.getResponse().getWriter().print("failed");
						}
					} else {
						con.rollback();
						this.getResponse().getWriter().print("failed");
					}
					con.commit();
					con.setAutoCommit(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				this.getResponse().getWriter().print("Already Joined");
			}
		} else {
			this.getResponse().getWriter().print("login");
		}
		ConfigData config = new ConfigData();
		config.setTorunamentData(tourid);
	}

	private String getWinAmt(String charid2) {

		Connection con = DBConnection.getConnection();
		try {
			Statement st = con.createStatement();
			String sql = "Select * from user_details where char_id = " + charid2;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				return rs.getString("winamt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getPeople(String tourid2) {

		Connection con = DBConnection.getConnection();
		try {
			Statement st = con.createStatement();
			String sql = "Select * from tournament_details where tour_id = " + tourid2;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				return rs.getString("people");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getPerkill(String tourid2) {

		Connection con = DBConnection.getConnection();
		try {
			Statement st = con.createStatement();
			String sql = "Select * from tourprice_details where tour_id = " + tourid2;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				return rs.getString("perkill");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getTournament() {

		try {
			JSONArray cachearr = ConfigData.getTournamentData((String) sessionMap.get("tourid"));
//			System.out.println("ServletContext JSON: " + cachearr);
			this.getResponse().getWriter().println(cachearr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTour() throws Exception {
		Connection con = DBConnection.getConnection();
		try {
			parse();

			con.setAutoCommit(false);
			Statement st = con.createStatement();
			String fulldate = getDate() + " " + getMonthname() + " " + getTime();
			statuscheck();
			amountSpilt(getAmount());
			String toursql = "Insert into tournament_details values (default," + "'" + getTitle() + "'," + getCharid()
					+ "," + "'" + getStatus() + "'," + "'" + fulldate + "'," + getPeople() + "," + getAmount() + ","
					+ "'" + getType() + "')";

//			System.out.println(toursql);

			int val1 = st.executeUpdate(toursql);

			if (val1 != 0) {

				tourid = getTourId();

				if (tourid.equals(null)) {
					tourid = "1";
				}

				String pricesql = "Insert into tourprice_details values (" + tourid + "," + getAmount() + "," + perfees
						+ "," + perkill + "," + create_amt + "," + dinner + "," + cmpnyfees + ")";
//				System.out.println(pricesql);

				int val2 = st.executeUpdate(pricesql);
				if (val2 != 0) {
					this.getResponse().getWriter().print("success");
				} else {
					con.rollback();
					this.getResponse().getWriter().print("failed");
				}
			} else {
				con.rollback();
				this.getResponse().getWriter().print("failed");
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
	}

	private String getTourId() {

		try {
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			String sql = "Select max(tour_id) from tournament_details";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				return rs.getString("max");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "1";
	}

	private void amountSpilt(int amount) {

		perfees = amount / 100;
		perkill = perfees / 2;
		float totkillamount = perkill * 99;
		create_amt = (float) 15 / 100;
		create_amt = create_amt * amount;
		balance = amount - (totkillamount + create_amt);
		dinner = (float) 40 / 100;
		dinner = dinner * balance;
		cmpnyfees = amount - (totkillamount + create_amt + dinner);
	}

	private void statuscheck() {

		LocalDate curdate = LocalDate.now();
		int curday = curdate.getDayOfMonth();
		int curmonth = curdate.getMonthValue();
//		System.out.println(curday + " " + curmonth);
		int tempmonth = 0;
		int tempday = Integer.parseInt(date);
		switch (monthname) {
		case "JAN":
			tempmonth = 1;
			break;
		case "FEB":
			tempmonth = 2;
			break;
		case "MAR":
			tempmonth = 3;
			break;
		case "APR":
			tempmonth = 4;
			break;
		case "MAY":
			tempmonth = 5;
			break;
		case "JUN":
			tempmonth = 6;
			break;
		case "JUL":
			tempmonth = 7;
			break;
		case "AUG":
			tempmonth = 8;
			break;
		case "SEP":
			tempmonth = 9;
			break;
		case "OCT":
			tempmonth = 10;
			break;
		case "NOV":
			tempmonth = 11;
			break;
		case "DEC":
			tempmonth = 12;
			break;
		}

		if (tempmonth == curmonth) {
			if (tempday == curday) {
				status = "live";
			} else if (tempday < curday) {
				status = "closed";
				people = 100;
			} else if (tempday > curday) {
				status = "open";
			}
		} else if (tempmonth < curmonth) {
			status = "closed";
			people = 100;
		} else if (tempmonth > curmonth) {
			status = "open";
		}
	}

	private void parse() throws Exception {

//		System.out.println(data);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		setTitle((String) obj.get("title"));
		setDate((String) obj.get("date"));
		setMonthname((String) obj.get("month"));
		setTime((String) obj.get("time"));
		setType((String) obj.get("type"));
		setAmount(Integer.parseInt((String) obj.get("amount")));
		setStatus("Open");
		setCharid((String) sessionMap.get("charid"));
//		System.out.println(title + " " + date + " " + monthname + " " + time + " " + type + " " + amount + " " + status
//				+ " " + charid);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMonthname() {
		return monthname;
	}

	public void setMonthname(String monthname) {
		this.monthname = monthname;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public String getCharid() {
		return charid;
	}

	public void setCharid(String charid) {
		this.charid = charid;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		sessionMap = (SessionMap) map;
	}

}
