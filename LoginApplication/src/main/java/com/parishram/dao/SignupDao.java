package com.parishram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.parishram.exception.InvalidInputException;
import com.parishram.utility.database.DBConnection;
import com.parishram.utility.database.Queries;

public class SignupDao {
	private final String userName;
	private final String password;

	public SignupDao(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public int signUpForNewUser() throws ClassNotFoundException, SQLException, InvalidInputException {
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.getDatabaseConnection();
		PreparedStatement prepStmtForDbSchema = connection.prepareStatement(Queries.GET_SCHEMA.getValue());
		prepStmtForDbSchema.executeQuery();
		PreparedStatement prepStmtForSelect = connection.prepareStatement(Queries.SELECT_LOGIN.getValue());
		prepStmtForSelect.setString(1, userName);
		prepStmtForSelect.setString(2, password);
		ResultSet resultset = prepStmtForSelect.executeQuery();
		String userNameFromDb = "";
		while (resultset.next()) {
			userNameFromDb = resultset.getString(1);
		}
		if (userNameFromDb.equalsIgnoreCase(userName)) {
			throw new InvalidInputException("This username already exists. Please choose another one.");
		} else {
			PreparedStatement prepStmtForInsertQuery = connection.prepareStatement(Queries.SIGNUP.getValue());
			prepStmtForInsertQuery.setString(1, userName);
			prepStmtForInsertQuery.setString(2, password);
			return prepStmtForInsertQuery.executeUpdate();
		}
	}
}
