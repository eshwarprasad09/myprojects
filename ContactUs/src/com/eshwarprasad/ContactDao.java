
package com.eshwarprasad;
import java.sql.*;

public class ContactDao {

	public boolean dataEntry(String name, String email, String message) {
		 String jdbcURL = "jdbc:postgresql://localhost:5432/iplproject";
	     String username = "postgres";
	     String password = "root";
	     Timestamp time = new Timestamp(System.currentTimeMillis());
	     String timeStamp = String.valueOf(time);
	     String query = "INSERT INTO contactus VALUES(?, ?, ?, ?, ?)";
	    
	     try {
				Class.forName("org.postgresql.Driver");
				Connection conn = DriverManager.getConnection(jdbcURL, username, password);
				PreparedStatement st = conn.prepareStatement(query);
				st.setString(1, name);
				st.setString(2, email);
				st.setString(3, message);
				st.setBoolean(4, true);
				st.setString(5, timeStamp);
				int i = st.executeUpdate();
				if(i>0) {
					return true;
				}	
			} catch (Exception e) {
				e.printStackTrace();
	}
	     return false;
  }
}
