package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;
import utils.JDBConnect;

public class UserDao {
	
	public int registerUser(User u) {
		
		String insert_sql = "insert into WebApp.users (first_name, last_name, username, password) values (?,?,?,?)";
		
		int result = 0;
		
		try {
			
			Connection con = JDBConnect.getConnection();
			PreparedStatement pstmt = con.prepareStatement(insert_sql);
			pstmt.setString(1, u.getFirstName());
			pstmt.setString(2, u.getLastName());
			pstmt.setString(3, u.getUsername());
			pstmt.setString(4, u.getPassword());
			
			System.out.println(pstmt);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
