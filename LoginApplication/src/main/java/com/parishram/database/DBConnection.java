package com.parishram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.parishram.common.InvalidInputException;

public class DBConnection {
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root123";

	private static Connection connection = null;

	private DBConnection() {

	}

	private static DBConnection dbConnection;

	public static DBConnection getDbConnection() {
		return getInstance();
	}

	public static void setDbConnection(DBConnection dbConnection) {
		DBConnection.dbConnection = dbConnection;
	}

	public static DBConnection getInstance() {
		dbConnection = new DBConnection();
		return dbConnection;
	}

	public static boolean isConnectionSuccessful() throws SQLException, ClassNotFoundException, InvalidInputException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		if (connection != null) {
			return true;
		} else {
			throw new InvalidInputException("Please provide correct database connection url, username or password.");
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
