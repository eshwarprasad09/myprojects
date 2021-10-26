package com.eshwarprasad;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class RequestDao {
	final int ID = 6;
	final int NAME = 1;
	final int EMAIL = 2;
	final int MESSAGE = 3;
	final int ACTIVE = 4;
	final int TIME_STAMP = 5;

	String jdbcURL = "jdbc:postgresql://localhost:5432/iplproject";
	String username = "postgres";
	String password = "root";

	public List<Request> getRequests() {
		List<Request> requests = new ArrayList<>();

		String query = "SELECT * FROM contactus";
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Request req = new Request();
				try {
					req.setName(resultSet.getString(NAME));
					req.setEmail(resultSet.getString(EMAIL));
					req.setMessage(resultSet.getString(MESSAGE));
					req.setActive(resultSet.getBoolean(ACTIVE));
					req.setTime(resultSet.getString(TIME_STAMP));
					req.setId(resultSet.getInt(ID));
					requests.add(req);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requests;
	}

	public boolean updateStatus(String value, int id) {
		String query = "UPDATE contactus SET active = ? WHERE id = ?";
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			PreparedStatement statement = connection.prepareStatement(query);
			if (value.equals("Archive")) {
				statement.setBoolean(1, false);
				statement.setInt(2, id);
			} else {
				statement.setBoolean(1, true);
				statement.setInt(2, id);
			}
			int rowData = statement.executeUpdate();
			if (rowData > 0) {
				return true;
			}
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
