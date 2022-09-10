package com.dump;

import com.config.ConfigData;
import com.opensymphony.xwork2.ActionSupport;

import redis.clients.jedis.Jedis;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CheckAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, SessionAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SessionMap<String, Object> sessionMap;
	public Jedis red = new Jedis("localhost");
	private String status, people = "", date, data, id, filter;
	private int tourid;

	@Override
	public String execute() throws Exception {

		String reqMethod = request.getMethod();
		if (reqMethod.equals("POST")) {
			getTournaments();
		}
		if (reqMethod.equals("GET")) {
			check();
		}
		if (reqMethod.equals("DELETE")) {
			checkout();
		}
		if (reqMethod.equals("PUT")) {
			setTour();
		}
		
		return NONE;
	}

	
	private void setTour() {
		try {
			parseid();
			
				sessionMap.put("tourid", id);
//				System.out.println(red.get("tourid"));
				ConfigData config = new ConfigData();
				config.setTorunamentData(id);
				this.getResponse().getWriter().print("success");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseid() {
		try {
//			System.out.println(data);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(data);

			setId((String) obj.get("tid"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getTournaments() {
		try {

			ResultSet rs = getResultSet();
			if (rs != null) {
				JSONArray arr = new JSONArray();
				JSONObject obj;
				while (rs.next()) {
					obj = new JSONObject();

					obj.put("tid", rs.getString("tour_id"));
					obj.put("title", rs.getString("title"));
					obj.put("date", rs.getString("date"));
					obj.put("people", rs.getString("people"));
					obj.put("status", rs.getString("status"));
					obj.put("amount", rs.getString("amount"));
					obj.put("type", rs.getString("type"));

					arr.add(obj);
				}
				this.getResponse().getWriter().println(arr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void statuscheck(String dbdate) {

		LocalDate curdate = LocalDate.now();
		int curday = curdate.getDayOfMonth();
		int curmonth = curdate.getMonthValue();
		// System.out.println(curday +" "+curmonth);

		String tempday = dbdate.substring(0, 2);
		tempday = tempday.trim();
		int tempdayint = Integer.parseInt(tempday);
		String tempmonthstr = dbdate.substring(2, 6);
		tempmonthstr = tempmonthstr.trim();
		int tempmonth = 0;
		switch (tempmonthstr) {
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
			if (tempdayint == curday) {
				status = "live";
			} else if (tempdayint < curday) {
				status = "closed";
				people = "100";
			} else if (tempdayint > curday) {
				status = "open";
			}
		} else if (tempmonth < curmonth) {
			status = "closed";
			people = "100";
		} else if (tempmonth > curmonth) {
			status = "open";
		}
	}

	private ResultSet getResultSet() {
		try {
			parsefilter();
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			String sql = "Select * from tournament_details where status = '" + getFilter() + "' order by tour_id";
			ResultSet rs = st.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parsefilter() {
		try {
//			System.out.println(data);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(data);

			setFilter((String) obj.get("filter"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void checkout() throws Exception {

		String charid = (String) sessionMap.get("charid");
		red.del(charid);
		red.del("tourid");
				
		//Session Invalidate
		sessionMap.invalidate();
		
		this.getResponse().getWriter().print("checkout");
	}

	private void check() {
		try {
			Connection con = DBConnection.getConnection();
			System.out.println("DB creation success");

			Statement st = con.createStatement();
			String sql = "Select * from tournament_details order by tour_id";
			ResultSet rs = st.executeQuery(sql);

			if (rs != null) {
				while (rs.next()) {
					tourid = Integer.parseInt(rs.getString("tour_id"));
					date = rs.getString("date");
					statuscheck(date);
					String update = "";
					if (status.equals("closed")) {
						update = "Update tournament_details set " + "status = '" + status + "', " + "people = 100 "
								+ "where tour_id = " + tourid;
					} else {
						update = "Update tournament_details set " + "status = '" + status + "' " + "where tour_id = "
								+ tourid;
					}
					// System.out.println(update);
					if (updateTour(update) != 0) {
						continue;
					} else {
						System.out.println("Got error on update " + update);
					}

				}
			}

//			redischeck();
//			System.out.println("#### Session Check: name "+sessionMap.get("name"));
//			System.out.println("#### Session Check: log "+sessionMap.get("log"));
//			System.out.println("#### Session Check: charid "+sessionMap.get("charid"));
			
			String name = (String) sessionMap.get("name");
			String log = (String) sessionMap.get("log");
			String charid = (String) sessionMap.get("charid");
			
			if(name != null && log != null){
				if (log.equals("true")) {
					this.getResponse().getWriter().print("logged");
					return;
				} else {
					this.getResponse().getWriter().print("nolog");
					return;
				}
			}
			else{
				this.getResponse().getWriter().print("nolog");
			}

		} catch (Exception e) {
//			System.out.println("No Data");
			e.printStackTrace();
		}
	}


	private static void redischeck() {
		Jedis red = new Jedis("localhost");

		String user = red.get("user");
		String charid = red.get("charid");
		String log = red.get("log");
		String tourid = red.get("tourid");

		if (user == null && charid == null && log == null) {
			red.set("user", "none");
			red.set("log", "false");
			red.set("charid", "0");
			red.set("tourid", "0");

			red.expire("user", 43200);
			red.expire("log", 43200);
			red.expire("charid", 43200);
			red.expire("tourid", 43200);
		} else {
			red.expire("user", 43200);
			red.expire("log", 43200);
			red.expire("charid", 43200);
			red.expire("tourid", 43200);
		}

	}

	private int updateTour(String sql) throws Exception {

		Connection con = DBConnection.getConnection();
		Statement st = con.createStatement();

		int val = st.executeUpdate(sql);
		return val;
	}

	// public void

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setSession(Map<String, Object> map) {
		
		sessionMap = (SessionMap) map;
	}

}
