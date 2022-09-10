package com.dump;

import java.sql.*;

import redis.clients.jedis.Jedis;

public class DBConnection {

	private static Connection con;
	
	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tourpubg", "postgres", "");
			System.out.println("DB exist");
						
		} catch (Exception e) {
			createDatabase();
		}
	}

	public static Connection getConnection() {
		return con;
	}

	private static void createDatabase() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
			Statement st = con.createStatement();
			String sql = "Create Database tourpubg";
			int val = st.executeUpdate(sql);
			if(val != 0){
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tourpubg", "postgres", "");
				createTables();
			}
			else{
				System.out.println("Value 0 Database");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tourpubg", "postgres", "");
				createTables();
			}

		} catch (Exception e) {
			System.out.println("Something went wrong connection");
			e.printStackTrace();
		}

	}

	private static void createTables() {
		
		try{
			Statement st = con.createStatement();
			String userTab = "Create table user_details("
					+ "id serial not null,"
					+ "name varchar(50) not null,"
					+ "password varchar(50) not null,"
					+ "char_id bigint not null,"
					+ "mobile varchar(50) not null,"
					+ "winamt bigint,"
					+ "primary key(char_id),"
					+ "unique(name, password))";
			
			String tourTab = "Create table tournament_details("
					+ "tour_id serial not null,"
					+ "title varchar(50) not null,"
					+ "char_id bigint not null,"
					+ "status varchar(30) not null,"
					+ "date varchar(50) not null,"
					+ "people varchar(50) not null,"
					+ "amount bigint not null,"
					+ "type varchar(50) not null,"
					+ "primary key(tour_id),"
					+ "FOREIGN KEY (char_id) REFERENCES user_details (char_id))";
			
			String tourpriceTab = "create table tourprice_details ( tour_id integer not null,"
									+ "amount bigint not null, "
									+ "perfees integer not null, "
									+ "perkill integer not null, "
									+ "create_amt integer not null,"
									+ "dinner_amt integer not null, "
									+ "cmpny_fees integer not null,"
									+ "foreign key (tour_id) references tournament_details (tour_id))";
			
			String tourregisTab = "Create table tourregis_details ( "
					+ "tour_id integer not null, "
					+ "char_id bigint not null, "
					+ "kill integer not null, "
					+ "foreign key (tour_id) references tournament_details (tour_id),"
					+ "foreign key (char_id) references user_details (char_id))";
			
			System.out.println(userTab);
			System.out.println(tourTab);
			System.out.println(tourpriceTab);
			System.out.println(tourregisTab);
			
			int val = st.executeUpdate(userTab);
			if(val != 0){
				System.out.println("UserTable Created");
			}
			else{
				System.out.println("SValue 0 user");
			}
			
			int val2 = st.executeUpdate(tourTab);
			if(val2 != 0){
				System.out.println("TourTable Created");
			}
			else{
				System.out.println("SValue 0 tournament");
			}
			
			int val3 = st.executeUpdate(tourpriceTab);
			if(val3 != 0){
				System.out.println("TourPriceTable Created");
			}
			else{
				System.out.println("SValue 0 tournament_price");
			}
			
			int val4 = st.executeUpdate(tourregisTab);
			if(val4 != 0){
				System.out.println("TourRegisTable Created");
			}
			else{
				System.out.println("SValue 0 tournament_regis");
			}
			
			
			createRedis();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createRedis() {
		Jedis red = new Jedis("localhost");
		red.set("user", "none");
		red.set("log", "false");
		red.set("charid", "0");
		red.set("tourid", "0");
		
		red.expire("user", 43200);
		red.expire("log", 43200);
		red.expire("charid", 43200);
		red.expire("tourid", 43200);
		
		//Private
		
		red.set("puser", "none");
		red.set("plog", "false");
		red.set("pcharid", "0");
		
		red.expire("puser", 43200);
		red.expire("plog", 43200);
		red.expire("pcharid", 43200);
	}
}
