package com.dump;

import java.sql.*;

public class DBConnection {
	
	private static Connection con;
	
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mMarket", "root", "");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return con;
	}
	
	
}
