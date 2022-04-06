package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBConnect {

	private static String jdbcURL = "jdbc:mysql://localhost:3306/WebApp";
	private static String jdbcUser = "root";
	private static String jdbcPass = "password";

	public static Connection getConnection() {

		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			System.out.println("Connected from DBConnect class");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;

	}
}
