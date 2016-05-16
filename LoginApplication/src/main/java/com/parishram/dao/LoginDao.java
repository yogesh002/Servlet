package com.parishram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.parishram.common.Constants;
import com.parishram.exception.InvalidInputException;
import com.parishram.utility.database.DBConnection;
import com.parishram.utility.database.Queries;

public class LoginDao {
	private final String userNameFromForm;
	private final String passwordFromForm;

	public LoginDao(String userNameFromForm, String passwordFromForm) {
		this.userNameFromForm = userNameFromForm;
		this.passwordFromForm = passwordFromForm;
	}

	private static Logger LOGGER = Logger.getLogger(LoginDao.class);

	public Map<String, String> provideLoginStatus() throws ClassNotFoundException, SQLException, InvalidInputException {
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.getDatabaseConnection();
		PreparedStatement prepStmtForDbSchema = connection.prepareStatement(Queries.GET_SCHEMA.getValue());
		prepStmtForDbSchema.executeQuery();
		PreparedStatement prepStmtForLoginCredentials = connection.prepareStatement(Queries.SELECT_LOGIN.getValue());
		prepStmtForLoginCredentials.setString(1, userNameFromForm);
		prepStmtForLoginCredentials.setString(2, passwordFromForm);
		ResultSet resultSet = prepStmtForLoginCredentials.executeQuery();
		Map<String, String> dataFromDatabase = null;
		if (resultSet == null) {
			LOGGER.debug("The username or password does not exist in the database");
			throw new InvalidInputException("The username/password does not exist. Please try again.");
		} else {
			String userNameFromDb = "";
			String passwordFromDb = "";
			while (resultSet.next()) {
				userNameFromDb = resultSet.getString(1);
				passwordFromDb = resultSet.getString(2);
			}
			dataFromDatabase = new HashMap<String, String>();
			dataFromDatabase.put(Constants.USERNAME, userNameFromDb);
			dataFromDatabase.put(Constants.PASSWORD, passwordFromDb);
			return dataFromDatabase;
		}
	}
}
