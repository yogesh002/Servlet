package com.parishram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

/**
 * The purpose of this class is to establish a database connection.
 * 
 * @author Yogesh Ghimire
 *
 */
public class DBConnection {
	private static final String JDBC_URL = getURL();
	private static final String USERNAME = getUserName();
	private static final String PASSWORD = getPassword();
	private static final String DRIVER = getDriver();

	/**
	 * This method returns a {@code connection} after establishing a successful
	 * database connection. The {@code connection} is used later to retrieve
	 * {@link PreparedStatement} to execute {@code SQL} queries.
	 * 
	 * @return connection The {@link Connection} is returned only after a
	 *         successful database connection is established.
	 * @throws SQLException
	 *             The {@link SQLException} is thrown if the {@link #USERNAME}
	 *             or {@link #PASSWORD} or {@link #JDBC_URL} is invalid.
	 * @throws ClassNotFoundException
	 *             The {@link ClassNotFoundException} is thrown if the
	 *             {@link #DRIVER} is invalid.
	 */
	public Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		return connection;
	}

	/**
	 * This method returns the {@link #JDBC_URL} which is required to establish
	 * a database connection in {@link #getDatabaseConnection}
	 * 
	 * @return the {@link String} which is the url required to establish the
	 *         database connection.
	 */
	private static String getURL() {
		return "jdbc:mysql://localhost:3306/";
	}

	/**
	 * This method returns the {@link #DRIVER} which is required to establish
	 * a database connection in {@link #getDatabaseConnection}
	 * 
	 * @return the {@link String} which is the {@code driver} required parameter
	 *         to establish the database connection.
	 */
	private static String getDriver() {
		return "com.mysql.jdbc.Driver";
	}

	/**
	 * This method returns the {@link #PASSWORD} which is required parameter to
	 * establish a database connection in {@link #getDatabaseConnection}
	 * 
	 * @return the {@link String} which is the {@code password} required to
	 *         establish the database connection.
	 */
	private static String getPassword() {
		return "root123";
	}

	/**
	 * This method returns the {@link #USERNAME} which is required parameter to
	 * establish a database connection in {@link #getDatabaseConnection}
	 * 
	 * @return the {@link String} which is the {@code username} required to
	 *         establish the database connection.
	 */
	private static String getUserName() {
		return "root";
	}
}
