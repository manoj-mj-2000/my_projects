package com.invoice;

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

public class InvoiceAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private String data;
	private int billno, amount;
	private float tax, discount;

	@Override
	public String execute() throws Exception {
		
		String reqMethtod = request.getMethod();
		System.out.println(reqMethtod);
		if(reqMethtod.equals("GET")){
			printData();
		}
		if(reqMethtod.equals("PUT")){
			updateData();
		}
		if(reqMethtod.equals("DELETE")){
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
			String sql = "Select * from invoices where billno = "+getBillno();
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
				String sql = "Delete from invoices where billno = "+getBillno();
				
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
			this.getResponse().getWriter().println("Bill Number not exist");
		}
	}
	// For PUT
	public void updateData() throws Exception{
		
		boolean dexist = false;
		float taxamt, discamt, netAmount, totamt; 
		try{
			parse1();
			Connection con = DBConnection.getConnection();
			Statement stmt = con.createStatement();
			String sql = "Select * from invoices where billno = "+getBillno();
			
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				dexist = true;
				setAmount(Integer.parseInt(rs.getString("amount")));
				System.out.println(getAmount());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(dexist){
			try{
				
				taxamt = (amount * (tax/100));
				discamt = (amount * (discount / 100));
				netAmount = amount - discamt;
				totamt = netAmount + taxamt;
				
				Connection con = DBConnection.getConnection();
				Statement stmt = con.createStatement();
				String sql = "Update invoices set netAmount = "+netAmount+", tax = "+taxamt+", discount ="+discamt+""
						+ ", totalAmount = "+totamt+" where billno="+getBillno()+"";
				
				int val = stmt.executeUpdate(sql);
				if(val != 0){
					this.getResponse().getWriter().println("Invoice was updated");
				}
			}
			catch (Exception e) {
				this.getResponse().getWriter().println("Something went wrong");
				e.printStackTrace();
			}
		}
		else{
			this.getResponse().getWriter().println("Bill Number not exist");
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
				obj.put("billno", rs.getString("billno"));
				obj.put("cid", rs.getString("customerId"));
				obj.put("staid", rs.getString("staffId"));
				obj.put("amount", rs.getString("amount"));
				obj.put("pid", rs.getString("productId"));
				obj.put("salid", rs.getString("salesId"));
				obj.put("cid", rs.getString("customerId"));
				obj.put("quan", rs.getString("quantity"));
				obj.put("netamt", rs.getString("netAmount"));
				obj.put("tax", rs.getString("tax"));
				obj.put("disc", rs.getString("discount"));
				obj.put("tot", rs.getString("totalAmount"));
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
			String sql = "Select * from invoices";
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
		
		setBillno(Integer.parseInt((String) obj.get("bill")));
		setTax(Integer.parseInt((String) obj.get("tax")));
		setDiscount(Integer.parseInt((String) obj.get("disc")));
		
		System.out.println(billno+" "+tax+" "+discount+" ");
	}
	
	private void parse2() throws Exception {
	
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(data);
		
		setBillno(Integer.parseInt((String) obj.get("bill")));
		System.out.println(billno);
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getBillno() {
		return billno;
	}

	public void setBillno(int billno) {
		this.billno = billno;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
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
