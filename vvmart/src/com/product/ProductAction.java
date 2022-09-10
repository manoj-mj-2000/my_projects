package com.product;

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

public class ProductAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

	private String pname, data;
	private int vid, id, quantity;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
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
		if(reqMethod.equals("DELETE")){
			deleteData();
		}
		return NONE;
	}

	// For DELETE
	private void deleteData()  throws Exception{
		
		boolean exist = false;
		try{
			parse2();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from products where productId = "+getId();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.absolute(1)){
				exist = true;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(exist){
			try{
				
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Delete from products where productId = "+getId();
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Deletion success");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Deletion error");
				e.printStackTrace();
			}
		}
		else{
			this.getResponse().getWriter().println("Product not exist");
		}
	}
	
	// For PUT
	private void updateData()  throws Exception{
		
		boolean dexist = false, fexist = false;
		try {
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from products where productId = " + getId();

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dexist = true;
			}

			String sql2 = "Select * from vendors where vendorId = " + getVid();
			ResultSet rs2 = stmt.executeQuery(sql2);
			if(rs2.absolute(1)){
				fexist = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dexist == true && fexist == true){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Update products set productName = '"+getPname()+"', quantity = "+getQuantity()+","
						+ " vendorId = "+getVid()+" where productId = "+getId();
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Updation Success");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Updation error");
				e.printStackTrace();
			}
		}
		else{
			if(dexist){
				this.getResponse().getWriter().println("Product Id already exist");
			}
			if(!fexist){
				this.getResponse().getWriter().println("Vendor Id not exist");
			}
		}
	}
	
	// For POST
	private void insertData()  throws Exception{
		
		boolean dexist = false, fexist = false;
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from products where productId = "+getId();
			
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				dexist = true; 
			}
			
			String sql2 = "Select * from vendors where vendorId = "+getVid();
			ResultSet rs2 = stmt.executeQuery(sql2);
			if(rs2.absolute(1)){
				fexist = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dexist == false && fexist == true){
			
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Insert into products values ("+getId()+", '"+getPname()+"', "+getQuantity()+", "
						+ ""+getVid()+")";
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Data added successfully");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Failed to add");
				e.printStackTrace();
			}
		}
		else{
			if(dexist){
				this.getResponse().getWriter().println("Product Id already exist");
			}
			if(!fexist){
				this.getResponse().getWriter().println("Vendor Id not exist");
			}
		}
		
	}
	
	// For GET
	private void printData() throws Exception{

		try{
			ResultSet rs = getResultSet();
			JSONArray arr = new JSONArray();
			JSONObject obj;
			while(rs.next()){
				obj = new JSONObject();
				obj.put("id", rs.getString("productId"));
				obj.put("name", rs.getString("productName"));
				obj.put("quan", rs.getString("quantity"));
				obj.put("vid", rs.getString("vendorId"));
				arr.add(obj);
			}
			System.out.println(arr.toJSONString());
			this.getResponse().getWriter().println(arr);
		}
		catch (Exception e) {
			
		}
	}
	
	public static ResultSet getResultSet(){
		ResultSet rs =  null;
		try{
			Connection con = DBConnection.getConnection();
			String sql = "Select * from products";
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
		
		setPname((String) obj.get("name"));
		setId(Integer.parseInt((String) obj.get("id")));
		setQuantity(Integer.parseInt((String) obj.get("quan")));
		setVid(Integer.parseInt((String) obj.get("vid")));
		
		System.out.println(pname+" "+id+" "+quantity+" "+vid);
	}
	
	private void parse2() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setId(Integer.parseInt((String) obj.get("id")));
		
		System.out.println(id);
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

}
