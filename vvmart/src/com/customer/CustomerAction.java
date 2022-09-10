package com.customer;

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

public class CustomerAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String data, globalMsg, cname, mobile, email;
	private int id;

	@Override
	public String execute() throws Exception {

		String reqMethod = request.getMethod();
		System.out.println(reqMethod);
		if (reqMethod.equals("GET")) {
			printData();
		}
		if (reqMethod.equals("POST")) {
			insertData();
		}
		if(reqMethod.equals("PUT")){
			updateData();
		}
		if(reqMethod.equals("DELETE")){
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
			ResultSet rs = stmt.executeQuery("SELECT * from customers WHERE customerId = "+getId()+"");
			if(rs.absolute(1)){
				exist = true;
			}
			else{
				exist = false;;
			}
		}
		catch(Exception e){
			exist = false;
		}
		
		if(exist){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Delete from customers where customerId = "+getId()+"";
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Deletion Success");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Deletion error");
				e.printStackTrace();
			}
		}
		else {
			this.getResponse().getWriter().println("Customer not exist");
		}
	}
	
	
	// For PUT
	public void updateData() throws Exception{
	
		boolean exist = false;

		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from customers WHERE customerId = "+getId());
			if(rs.absolute(1)){
				exist = true;
			}
			else{
				exist = false;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		if(exist){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Update customers set customerName = '"+getCname()+"', mobile = '"+getMobile()+"',"
						+ " email = '"+getEmail()+"' where customerId = "+getId();
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Data Update Successfully.");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Updation error");
				e.printStackTrace();
			}
		}
		else{
			this.getResponse().getWriter().println("Customer not exist");
		}
	}	
	
	// For POST
	private void insertData() throws Exception {

		boolean exist = false;

		try {
			parse1();

			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from customers where customerId =" + getId();
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				exist = true;
			} else {
				exist = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!exist) {

			try {
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();

				String sql = "insert into customers values (" + getId() + ", '" + getCname() + "', '" + getMobile()
						+ "', " + " '" + getEmail() + "')";

				int val = stmt.executeUpdate(sql);
				if (val != 0) {
					this.getResponse().getWriter().println("Data Added Successfully.");
				}
			} catch (Exception e) {

				this.getResponse().getWriter().println("Data failed to add.");
				e.printStackTrace();
			}
		} else {
			this.getResponse().getWriter().println("Data already exist.");
		}
	}

	// For GET
	public void printData() {

		try {
			ResultSet rs = getResultSet();
			JSONArray arr = new JSONArray();
			JSONObject obj;
			while (rs.next()) {
				obj = new JSONObject();
				obj.put("id", rs.getString("customerId"));
				obj.put("name", rs.getString("customerName"));
				obj.put("mobile", rs.getString("mobile"));
				obj.put("email", rs.getString("email"));
				arr.add(obj);
			}
			System.out.println(arr.toJSONString());
			this.getResponse().getWriter().println(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getResultSet() {
		ResultSet rs = null;
		try {
			Connection con = DBConnection.getConnection();
			String sql = "Select * from customers";
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

		setCname((String) obj.get("name"));
		setId(Integer.parseInt((String) obj.get("id")));
		setMobile((String) obj.get("mobile"));
		setEmail((String) obj.get("email"));

		System.out.println(cname + " " + id + " " + mobile + " " + email);
	}
	
	private void parse2() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setId(Integer.parseInt((String) obj.get("id")));
		
		System.out.println(id);
	}

	// Getters and Setters
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getGlobalMsg() {
		return globalMsg;
	}

	public void setGlobalMsg(String globalMsg) {
		this.globalMsg = globalMsg;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HttpServletRequest getRequest() {
		return request;
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
