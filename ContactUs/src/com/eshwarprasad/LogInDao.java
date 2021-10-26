package com.eshwarprasad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

public class LogInDao {
	public boolean credentials(String userName, String userPassword) {
		 String jdbcURL = "jdbc:postgresql://localhost:5432/iplproject";
	     String name = "postgres";
	     String password = "root";
	     String query = "select * from users where username=? and userpassword=?";
	     try {
				Class.forName("org.postgresql.Driver");
				Connection conn = DriverManager.getConnection(jdbcURL, name, password);
				PreparedStatement st = conn.prepareStatement(query);
				st.setString(1, userName);
				st.setString(2, userPassword);
				ResultSet resultset = st.executeQuery();
				
				if(resultset.next()) {
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
	}
	     return false;
 }
	
}
