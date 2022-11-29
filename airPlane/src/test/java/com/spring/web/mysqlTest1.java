package com.spring.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Test;

public class mysqlTest1 {

	@Test
	public void test() {
		java.sql.Statement state = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/airport?allowPublicRetrieval=true&useSSL=false","root","1004");
			state = conn.createStatement();
			System.out.println("conn "+conn);
		
			String sql;
			sql = "SELECT * from airline";
			ResultSet rs = state.executeQuery(sql);
			
			while(rs.next()) {
				String a = rs.getString("shorts");
				String b = rs.getString("full");
				System.out.print(a+", ");
				System.out.print(b+"\n");
			}
			rs.close();
			state.close();
			conn.close();
		
		}catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
