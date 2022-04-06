package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.LoginBean;
import utils.JDBConnect;

public class LoginDao {
	
	boolean status = false;
	String sql = "select * from WebApp.users where username = ? and password = ? ";

	public boolean validate (LoginBean loginBean) {
		
		
		try {
			
			Connection con = JDBConnect.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginBean.getUsername());
			pstmt.setString(2, loginBean.getPassword());
			
			System.out.println(pstmt);
			
			ResultSet rs = pstmt.executeQuery();
			status = rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
}
