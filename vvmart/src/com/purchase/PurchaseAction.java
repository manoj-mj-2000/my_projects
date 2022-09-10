package com.purchase;

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

public class PurchaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private String data;
	private int id, pid, quantity, vid;
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
	public void deleteData() throws Exception{
		
		boolean exist = false;
		try{
			parse2();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from purchase_orders where purchaseId = "+getId();
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
				String sql = "Delete from purchase_orders where purchaseId = "+getId();
				
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
		else{
			this.getResponse().getWriter().println("Purchase Id not exist");
		}
	}
	
	// For PUT
	public void updateData() throws Exception{
		
		boolean dexist=false,vexist=false,pexist=false;
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt1 = con.createStatement();
			String sql1 = "Select * from purchase_orders where purchaseId ="+getId();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			if(rs1.next()){
				dexist = true;
			}
			
			String sql2 = "Select * from vendors where vendorId = "+getVid();
			ResultSet rs2 = stmt1.executeQuery(sql2);
			if(rs2.absolute(1)){
				vexist = true;
			}
			
			String sql3 = "Select * from products where productId = "+getPid();
			ResultSet rs3 = stmt1.executeQuery(sql3);
			if(rs3.absolute(1)){
				pexist = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dexist && vexist && pexist ){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Update purchase_orders set productId = "+getPid()+", quantity="+getQuantity()+", "
						+ "vendorId = "+getVid()+" where purchaseId = "+getId();
				
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
			if(!dexist){
				this.getResponse().getWriter().println("Purchase Id already exist");
			}
			if(!vexist){
				this.getResponse().getWriter().println("Vendor Id not exist");
			}
			if(!pexist){
				this.getResponse().getWriter().println("Product Id not exist");
			}
			else{
				this.getResponse().getWriter().println("Something wrong");
			}
		}
	}
	
	// For POST
	public void insertData() throws Exception{
		
		boolean dexist =false, vexist = false, pexist = false;
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt1 = con.createStatement();
			String sql1 = "Select * from purchase_orders where purchaseId ="+getId();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			if(rs1.next()){
				dexist = true;
			}
			
			String sql2 = "Select * from vendors where vendorId = "+getVid();
			ResultSet rs2 = stmt1.executeQuery(sql2);
			if(rs2.absolute(1)){
				vexist = true;
			}
			
			String sql3 = "Select * from products where productId = "+getPid();
			ResultSet rs3 = stmt1.executeQuery(sql3);
			if(rs3.absolute(1)){
				pexist = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dexist == false && vexist == true && pexist == true){
			try{
				
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Insert into purchase_orders values ("+getId()+", "+getQuantity()+", "+getVid()+","
						+ " "+getPid()+")";
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Data added successfully");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Failed to add data");
				e.printStackTrace();
			}
		}
		else{
			if(dexist){
				this.getResponse().getWriter().println("Purchase id already exist");
			}
			if(vexist == false){
				this.getResponse().getWriter().println("Vendor id not exist");
			}
			if(pexist == false){
				this.getResponse().getWriter().println("Product id not exist");
			}
			else{
				this.getResponse().getWriter().println("Something went wrong");
			}
		}
	}
	
	// For GET
	public void printData() throws Exception{
		
		try{
			ResultSet rs = getResultSet();
			JSONArray arr = new JSONArray();
			JSONObject obj;
			while(rs.next()){
				obj = new JSONObject();
				obj.put("id", rs.getString("purchaseId"));
				obj.put("pid", rs.getString("productId"));
				obj.put("quan", rs.getString("quantity"));
				obj.put("vid", rs.getString("vendorId"));
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
			String sql = "Select * from purchase_orders";
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
		
		setPid(Integer.parseInt((String) obj.get("pid")));
		setId(Integer.parseInt((String) obj.get("id")));
		setQuantity(Integer.parseInt((String) obj.get("quan")));
		setVid(Integer.parseInt((String) obj.get("vid")));
		
		System.out.println(id+" "+pid+" "+quantity+" "+vid);
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
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
