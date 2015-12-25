package com.utils.excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB2conn {
	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:db2://192.168.0.97:50000/dev_db";
		String username = "dev_db";
		String password = "dev_db";
		String className = "com.ibm.db2.jcc.DB2Driver";
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("get conn  success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args){
		getConnection();
	}
}