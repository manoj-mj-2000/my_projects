package com.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dump.DBConnection;

import redis.clients.jedis.Jedis;

public class ConfigData {

	private static HashMap<String, String> userData = new HashMap<>();
	private static HashMap<String, String> tourmntData = new HashMap<>();
	private static SessionMap<String, Object> sessionMap;

	private static Jedis red = new Jedis("localhost");

	public static JSONArray getUserData(String charid) {

		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();

		if(red.hget(charid, "name") != null || red.hget(charid, "name") != ""){
			obj.put("name", red.hget(charid, "name"));
			obj.put("charid", red.hget(charid, "charid"));
			obj.put("mobile", red.hget(charid, "mobile"));
			obj.put("winamt", red.hget(charid, "winamt"));
	
			arr.add(obj);
		}
		else{
			obj.put("name", userData.get("name"));
			obj.put("charid", userData.get("charid"));
			obj.put("mobile", userData.get("mobile"));
			obj.put("winamt", userData.get("winamt"));
	
			arr.add(obj);
		}
		
		return arr;
	}

	public HashMap<String, String> setUserData(ResultSet rs) {

		try {

			while (rs.next()) {
				userData.put("name", rs.getString("name"));
				userData.put("charid", rs.getString("char_id"));
				userData.put("mobile", rs.getString("mobile"));
				userData.put("winamt", rs.getString("winamt"));
				System.out.println("User Data: " + userData.toString());
			}
			System.out.println("ConfiData UserData: " + userData.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userData;
	}

	public void setUserData(String charId) {

		Connection con = DBConnection.getConnection();
		try {

			Statement st = con.createStatement();
			String sql = "Select * from user_details where char_id = " + charId;
			ResultSet rs = st.executeQuery(sql);
			setUserData(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONArray getTournamentData(String tourid) {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();

		if (tourid.equals(tourmntData.get("tourid"))) {
			obj.put("title", tourmntData.get("title"));
			obj.put("status", tourmntData.get("status"));
			obj.put("datet", tourmntData.get("datet"));
			obj.put("people", tourmntData.get("people"));
			obj.put("amount", tourmntData.get("amount"));
			obj.put("type", tourmntData.get("type"));
			obj.put("perkill", tourmntData.get("perkill"));
			obj.put("perfees", tourmntData.get("perfees"));
			obj.put("dinner", tourmntData.get("dinner"));
			obj.put("private", "false");
			arr.add(obj);
		}

		else {

			tourmntData = ConfigData.setTorunamentData(tourid);

			obj.put("title", tourmntData.get("title"));
			obj.put("status", tourmntData.get("status"));
			obj.put("datet", tourmntData.get("datet"));
			obj.put("people", tourmntData.get("people"));
			obj.put("amount", tourmntData.get("amount"));
			obj.put("type", tourmntData.get("type"));
			obj.put("perkill", tourmntData.get("perkill"));
			obj.put("perfees", tourmntData.get("perfees"));
			obj.put("dinner", tourmntData.get("dinner"));
			obj.put("private", "false");
			arr.add(obj);
		}

		return arr;
	}

	public static HashMap<String, String> setTorunamentData(String tourid) {

		Connection con = DBConnection.getConnection();
		try {

			System.out.println(tourid + " This is tournament id");
			Statement st = con.createStatement();
			String sql = "select t.title, t.status, t.date, t.people, t.amount, t.type, "
					+ "p.perkill, p.perfees, p.dinner_amt from tournament_details t "
					+ "join tourprice_details p on t.tour_id = p.tour_id where t.tour_id = " + tourid;

			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				tourmntData.put("tourid", tourid);
				tourmntData.put("title", rs.getString("title"));
				tourmntData.put("status", rs.getString("status"));
				tourmntData.put("datet", rs.getString("date"));
				tourmntData.put("people", rs.getString("people"));
				tourmntData.put("amount", rs.getString("amount"));
				tourmntData.put("type", rs.getString("type"));
				tourmntData.put("perkill", rs.getString("perkill"));
				tourmntData.put("perfees", rs.getString("perfees"));
				tourmntData.put("dinner", rs.getString("dinner_amt"));
			}
			System.out.println("ServletContext Tournament Data: " + tourmntData.toString());

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return tourmntData;
	}

}
