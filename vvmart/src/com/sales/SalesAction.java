package com.sales;

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

public class SalesAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String data;
	private int sid, pid, cid, stafid, quantity, amount;
	
	
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
		
		boolean dexist = false;
		try{
			parse2();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from sales_orders where salesId = "+getSid();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.absolute(1)){
				dexist = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dexist){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Delete from sales_orders where salesId = "+ getSid();
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
			this.getResponse().getWriter().println("Sales Id not exist");
		}
	}
	// For PUT
	public void updateData() throws Exception{

		boolean dexist = false, staexist=false, cusexist=false, proexist=false;
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql1 = "Select * from sales_orders where salesId = "+getSid();
			ResultSet rs1 = stmt.executeQuery(sql1);
			if(rs1.next()){
				dexist = true;
			}
			
			String sql2 = "Select * from staffs where staffId = "+getStafid();
			ResultSet rs2 = stmt.executeQuery(sql2);
			if(rs2.next()){
				staexist = true;
			}
			
			String sql3 = "Select * from customers where customerId = "+getCid();
			ResultSet rs3 = stmt.executeQuery(sql3);
			if(rs3.next()){
				cusexist = true;
			}
			
			String sql4 = "Select * from products where productId = "+getPid();
			ResultSet rs4 = stmt.executeQuery(sql4);
			if(rs4.next()){
				proexist = true;
			}
		}
		catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		
		if(dexist && staexist && cusexist && proexist ){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Update sales_orders set productId = "+getPid()+", amount = "+getAmount()+", "
						+ "customerId = "+getCid()+", quantity = "+getQuantity()+", staffId = "+getStafid()+" "
								+ "where salesId = "+getSid();
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Updation success");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Updation error");
				e.printStackTrace();
			}
		}
		else{
			if(!dexist){
				this.getResponse().getWriter().println("Sales Id not exist");
			}
			if(!proexist){
				this.getResponse().getWriter().println("Product Id not exist");
			}
			if(!staexist){
				this.getResponse().getWriter().println("Staff Id not exist");
			}
			if(!cusexist){
				this.getResponse().getWriter().println("Customer Id not exist");
			}
		}
		
	}
	// For POST
	public void insertData() throws Exception{
		
		boolean dexist = false, staexist=false, cusexist=false, proexist=false; 
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql1 = "Select * from sales_orders where salesId = "+getSid();
			ResultSet rs1 = stmt.executeQuery(sql1);
			if(rs1.next()){
				dexist = true;
			}
			
			String sql2 = "Select * from staffs where staffId = "+getStafid();
			ResultSet rs2 = stmt.executeQuery(sql2);
			if(rs2.next()){
				staexist = true;
			}
			
			String sql3 = "Select * from customers where customerId = "+getCid();
			ResultSet rs3 = stmt.executeQuery(sql3);
			if(rs3.next()){
				cusexist = true;
			}
			
			String sql4 = "Select * from products where productId = "+getPid();
			ResultSet rs4 = stmt.executeQuery(sql4);
			if(rs4.next()){
				proexist = true;
			}
		}
		catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		
		if(dexist == false && staexist && cusexist && proexist ){
			try{
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Insert into sales_orders values ("+getSid()+", "+getPid()+", "+getAmount()+", "
						+ ""+getCid()+", "+getQuantity()+", "+getStafid()+")";
				
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
				this.getResponse().getWriter().println("Sales Id already exist");
			}
			if(!proexist){
				this.getResponse().getWriter().println("Product Id not exist");
			}
			if(!staexist){
				this.getResponse().getWriter().println("Staff Id not exist");
			}
			if(!cusexist){
				this.getResponse().getWriter().println("Customer Id not exist");
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
				// sid, pid, cid, quan, amount, stafid
				obj.put("sid", rs.getString("salesId"));
				obj.put("pid", rs.getString("productId"));
				obj.put("cid", rs.getString("customerId"));
				obj.put("quan", rs.getString("quantity"));
				obj.put("amount", rs.getString("amount"));
				obj.put("stafid", rs.getString("staffId"));

				arr.add(obj);
			}
			System.out.println(arr.toJSONString());
			this.getResponse().getWriter().println(arr);
		}
		catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
	}
	public static ResultSet getResultSet(){
		ResultSet rs =  null;
		try{
			Connection con = DBConnection.getConnection();
			String sql = "Select * from sales_orders";
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
		
		setSid(Integer.parseInt((String) obj.get("sid")));
		setPid(Integer.parseInt((String) obj.get("pid")));
		setCid(Integer.parseInt((String) obj.get("cid")));
		setQuantity(Integer.parseInt((String) obj.get("quan")));
		setAmount(Integer.parseInt((String) obj.get("amount")));
		setStafid(Integer.parseInt((String) obj.get("stafid")));
		
		System.out.println(sid+" "+pid+" "+cid+" "+amount+" "+quantity+" "+stafid);
	}
	
	private void parse2() throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setSid(Integer.parseInt((String) obj.get("sid")));
		
		System.out.println(sid);
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getStafid() {
		return stafid;
	}

	public void setStafid(int stafid) {
		this.stafid = stafid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
