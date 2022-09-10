package com.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.config.ConfigData;
import com.dump.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

import redis.clients.jedis.Jedis;


public class UserAction extends ActionSupport implements ServletResponseAware, ServletRequestAware, SessionAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SessionMap<String, Object> sessionMap;
	private String data, name, pass, mobile, charId;
	public Jedis red = new Jedis("localhost");
		
	@Override
	public String execute() throws Exception {
		
		String reqMethod = request.getMethod();
		if(reqMethod.equals("GET")){
			checkUser();
		}
		if(reqMethod.equals("POST")){
			createUser();
		}
		if(reqMethod.equals("PUT")){
			getUser();
		}
		if(reqMethod.equals("DELETE")){
			getUserTour();
		}
		return NONE;
	}

	private void getUserTour() {
		
		try{
			String charid = (String) sessionMap.get("charid");
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			String sql = "select * from tournament_details "
					+ "where tour_id in "
					+ "(select tour_id from tourregis_details "
					+ "where char_id = "+charid+")";
			
			ResultSet rs = st.executeQuery(sql);
			
			if(rs != null){
				JSONArray arr = new JSONArray();
				JSONObject obj;
				
				while(rs.next()){
					obj = new JSONObject();
					
					obj.put("tourid", rs.getString("tour_id"));
					obj.put("title", rs.getString("title"));
					obj.put("status", rs.getString("status"));
					obj.put("date", rs.getString("date"));
					obj.put("people", rs.getString("people"));
					obj.put("amount", rs.getString("amount"));
					obj.put("type", rs.getString("type"));
					
					arr.add(obj);
				}
				this.getResponse().getWriter().print(arr);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getUser() {
		
		try{
				
			JSONArray cachearr = ConfigData.getUserData((String) sessionMap.get("charid"));
			
//			System.out.println("ServletContext JSON: "+cachearr);
			this.getResponse().getWriter().print(cachearr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createUser() throws Exception {
		
		parser1();
		Connection con = DBConnection.getConnection();
		boolean exist = false;
		
		try{
			Statement st = con.createStatement();
			String sql = "Select * from user_details"
					+ " where name = '"+getName()+"' and"
					+ " password = '"+getPass()+"' and"
					+ " char_id = "+getCharId();
//			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				exist = true;
			} else {
				exist = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!exist){
			try{
				Statement st = con.createStatement();
				String sql = "Insert into user_details values (default,"
								+"'"+ getName() +"',"
								+"'"+ getPass() +"',"
								+ getCharId() +","
								+"'"+ getMobile() +"',"
								+ "0)";
//				System.out.println(sql);
				int val = st.executeUpdate(sql);
				if(val != 0){
					
					//Session Assi
					sessionMap.put("name", getName());
					sessionMap.put("log", "true");
					sessionMap.put("charid", getCharId());
					
					red.hset(getCharId(), "name", getName());
					red.hset(getCharId(), "pass", getPass());
					red.hset(getCharId(), "charid", getCharId());
					red.hset(getCharId(), "mobile", getMobile());
					red.hset(getCharId(), "winamt", "0");
					
					ConfigData config = new ConfigData();
					config.setUserData(getCharId());
					
					this.getResponse().getWriter().print("success");
				}
				else{
					this.getResponse().getWriter().print("Try another Username and Password");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else{
			this.getResponse().getWriter().print("failed");
		}
	}

	private void checkUser() throws Exception {
		
		parser2();
		Connection con = DBConnection.getConnection();
		
		try{
			Statement st = con.createStatement();
			String sql = "Select * from user_details "
					+ "where name = '"+getName()+"' and password = '"+getPass()+"'";
//			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				
				// Session Assign
				
				sessionMap.put("name", rs.getString("name"));
				sessionMap.put("log", "true");
				sessionMap.put("charid", rs.getString("char_id"));
				
				red.hset(rs.getString("char_id"), "name", rs.getString("name"));
				red.hset(rs.getString("char_id"), "pass", rs.getString("password"));
				red.hset(rs.getString("char_id"), "charid", rs.getString("char_id"));
				red.hset(rs.getString("char_id"), "mobile", rs.getString("mobile"));
				red.hset(rs.getString("char_id"), "winamt", rs.getString("winamt"));
				
				ConfigData config = new ConfigData();
				config.setUserData(rs.getString("char_id"));
				
				this.getResponse().getWriter().print("success");
			}
			else{
				this.getResponse().getWriter().print("failed");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//JSON Parsers
	
	private void parser1() throws Exception{
		
//		System.out.println(data);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setName((String) obj.get("name"));
		setCharId((String) obj.get("cid"));
		setPass((String) obj.get("pass"));
		setMobile((String) obj.get("mobl"));
	}
	
	private void parser2() throws Exception{
		
//		System.out.println(data);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setName((String) obj.get("user"));
		setPass((String) obj.get("pass"));
	}
	
	// Getters and Setters
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCharId() {
		return charId;
	}

	public void setCharId(String charId) {
		this.charId = charId;
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
