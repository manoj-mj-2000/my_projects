package com.vendor;

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

public class VendorAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String data, name, pname, email;
	private int id;

	@Override
	public String execute() throws Exception {
		
		String reqMethod = request.getMethod();
		if(reqMethod.equals("GET")){
			printData();
		}
		if(reqMethod.equals("POST")){
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
			ResultSet rs = stmt.executeQuery("SELECT * from vendors WHERE vendorId = "+getId()+"");
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
				String sql = "Delete from vendors where vendorId = "+getId()+"";
				
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
			this.getResponse().getWriter().println("Vendor Id not exist");
		}
	}
	
	// For PUT
	public void updateData() throws Exception{
		
		boolean exist = false;
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from vendors WHERE vendorId = "+getId());
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
				String sql = "Update vendors set vendorName = '"+getName()+"', vendorProduct='"+getPname()+"', "
						+ "vendorEmail='"+getEmail()+"' where vendorId = "+getId();
				
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
			this.getResponse().getWriter().println("Vendor Id not exist");
		}
	}
	
	// For POST
	public void insertData() throws Exception{
		
		boolean exist = false;
		try {
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from vendors WHERE vendorId = " + getId());
			if (rs.absolute(1)) {
				exist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!exist){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Insert into vendors values ("+getId()+", '"+getName()+"', '"+getPname()+"', "
						+ "'"+getEmail()+"')";
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Data Added Successfully");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Failed to add data");
				e.printStackTrace();
			}
		}
		else{
			this.getResponse().getWriter().println("Vendor Id already exist");
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
				obj.put("id", rs.getString("vendorId"));
				obj.put("name", rs.getString("vendorName"));
				obj.put("pname", rs.getString("vendorProduct"));
				obj.put("email", rs.getString("vendorEmail"));
				arr.add(obj);
			}
			System.out.println(arr.toJSONString());
			this.getResponse().getWriter().println(arr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet getResultSet(){
		ResultSet rs =  null;
		try{
			Connection con = DBConnection.getConnection();
			String sql = "Select * from vendors";
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		}
		catch(Exception e){
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
		setPname((String) obj.get("pname"));
		setEmail((String) obj.get("email"));

		System.out.println(name + " " + id + " " + pname + " " + email);
	}
	
	private void parse2() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setId(Integer.parseInt((String) obj.get("id")));
		
		System.out.println(id);
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

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
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
