package com.staff;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dump.DBConnection;
import com.opensymphony.xwork2.ActionSupport;

public class StaffAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String data, name, mobile;
	private int id;
	
	@Override
	public String execute() throws Exception {
		
		String reqMethod = request.getMethod();
		System.out.println(reqMethod);
		if(reqMethod.equals("GET")){
			printData();
		}
		if(reqMethod.equals("POST")){
			insertData();
		}
		if(reqMethod.equals("PUT")){
			updateData();
		}
		if(reqMethod.endsWith("DELETE")){
			deleteData();
		}
		return NONE;
	}
	
	// For DELETE
	public void deleteData() throws Exception{
		
		boolean exist = false;
		try{
			parse2();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from staffs where staffId = "+getId();
			
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.absolute(1)){
				exist = true;
			}
		}
		catch (Exception e) {
			System.out.println("In check block");
			e.printStackTrace();
		}
		
		if(exist){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Delete from staffs where staffId = "+getId();
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Deletion Success");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Deletion Error");
				e.printStackTrace();
			}
		}
		else{
			this.getResponse().getWriter().println("Staff Id not exist");
		}
	}
	
	// For PUT
	public void updateData() throws Exception{
		
		boolean exist = false;
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from staffs WHERE staffId = "+getId());
			if(rs.absolute(1)){
				exist = true;
			}
			else{
				exist = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(exist){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Update staffs set staffName= '"+getName()+"', staffMobile= '"+getMobile()+"'"
						+ " where staffId = "+getId()+"";
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Updation Success");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Updation Error");
				e.printStackTrace();
			}
		}
		else{
			this.getResponse().getWriter().println("Staff Id not exist");
		}
	}
	
	// For POST
	public void insertData() throws Exception{
		
		boolean exist = false;
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from staffs WHERE staffId = "+getId());
			if(rs.absolute(1)){
				exist = true;
			}
			else{
				exist = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!exist){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Insert into staffs values ("+getId()+", '"+getName()+"', '"+getMobile()+"')";
				int val = stmt.executeUpdate(sql);
				if (val != 0) {
					this.getResponse().getWriter().println("Data Added Successfully.");
				}
			} catch (Exception e) {

				this.getResponse().getWriter().println("Data failed to add.");
				e.printStackTrace();
			}
		} else {
			this.getResponse().getWriter().println("Staff Id already exist.");
		}
	}
	
	// For GET
	public void printData(){
		try{
			ResultSet rs = getResultSet();
			JSONArray arr = new JSONArray();
			JSONObject obj;
			while(rs.next()){
				obj = new JSONObject();
				obj.put("id", rs.getString("staffId"));
				obj.put("name", rs.getString("staffName"));
				obj.put("mobile", rs.getString("staffMobile"));
				arr.add(obj);
			}
			System.out.println(arr.toJSONString());
			this.getResponse().getWriter().println(arr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet getResultSet() {
		ResultSet rs = null;
		try {
			Connection con = DBConnection.getConnection();
			String sql = "Select * from staffs";
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			System.out.println("null");
			return null;
		}
	}
	
	// JSON Parsers
	private void parse1() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);

		setName((String) obj.get("name"));
		setId(Integer.parseInt((String) obj.get("id")));
		setMobile((String) obj.get("mobile"));

		System.out.println(name + " " + id + " " + mobile + " ");
	}
	
	private void parse2() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setId(Integer.parseInt((String) obj.get("id")));
		
		System.out.println(id);
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
