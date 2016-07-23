package com.parishram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import com.parishram.common.Constants;
import com.parishram.exception.InvalidInputException;
import com.parishram.model.UserSignUp;
import com.parishram.service.ProfileService;
import com.parishram.serviceimpl.ProfileServiceImpl;
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

	public Map<String, String> provideLoginStatus(WebApplicationContext context) throws ClassNotFoundException, SQLException, InvalidInputException {
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
			// Retrieve userDetails now
		//	retrieveUserDetails(userNameFromDb, passwordFromDb, context);
			return dataFromDatabase;
		}
	}

	private void retrieveUserDetails(String username, String password, WebApplicationContext context) {
		ProfileService profileService = (ProfileServiceImpl)context.getBean("profileServiceImpl");
		UserSignUp userSignUp = new UserSignUp();
		userSignUp.setPassword(password);
		userSignUp.setUserName(username);
		profileService.getAddress(userSignUp);
	/*	UserDetails userDetails = personalDetails.getAddress(userSignUp);
		userDetails.getState();
		userDetails.getCity();
		userDetails.getZip();*/
	}
}
