package com.utils.excel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class excelconn {
	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.0.98:1521:hdorcl";
		String username = "hd_dev";
		String password = "hd_dev";
		String className = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, username, password);
			// System.out.println("get conn  success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}