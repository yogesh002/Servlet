package com.parishram.database;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.parishram.common.Constants;
import com.parishram.common.InvalidInputException;
import com.parishram.login.LoginAppModel;

/**
 * 
 * @author Yogesh
 *
 */
public class QueriesUtil extends HttpServlet {
	private static final long serialVersionUID = -249008173002917391L;
	private static Logger logger = Logger.getLogger(QueriesUtil.class);
	private static final String SELECT = "select";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("Inside doGet");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Connection connection = null;
		try {
			// TODO: make dynamic table contents
			DBConnection dbConnection = new DBConnection();
			connection = dbConnection.getDatabaseConnection();
			checkUserNameForLoginAndSignup(request, connection, response, out);
		} catch (Exception e) {
			handleException(e, request, response, out);
		}

		finally {
			try {
				closeConnection(connection, out);
			} catch (Exception e) {
				out.write(
						"<p style='color:black; font-weight: bold; text-align:center; padding: 10px; background-color: pink; width: 500px; margin: auto;'>"
								+ e.getMessage() + "</p>");
				logger.debug("The exception caught while closing the resources: " + e.getMessage());
			}
		}

	}

	private void handleException(Exception e, HttpServletRequest paramServletRequest,
			HttpServletResponse paramServletResponse, PrintWriter out) throws ServletException, IOException {
		RequestDispatcher rd = null;
		if (e instanceof InvalidInputException) {
			paramServletRequest.setAttribute(Constants.ATTRIBUTE_EXCEPTION, e);
			rd = paramServletRequest.getRequestDispatcher(Constants.LOGIN);
			if (rd != null) {
				rd.include(paramServletRequest, paramServletResponse);
				out.write(
						"<p style='color:black; font-weight: bold; text-align:center; padding: 10px; background-color: pink; width: 500px; margin: auto;'>"
								+ e.getMessage() + Constants.MESSAGE_TRY_AGAIN + "</p>");
			}

		} else {
			rd = paramServletRequest.getRequestDispatcher(Constants.FAILURE);
			if (rd != null) {
				rd.include(paramServletRequest, paramServletResponse);
				out.write(
						"<p style='color:black; font-weight: bold; text-align:center; padding: 10px; background-color: pink; width: 500px; margin: auto;'>"
								+ e.getMessage() + "</p>");
			}
		}
	}

	private void closeConnection(Connection connection, PrintWriter out) throws SQLException {
		if (connection != null) {
			connection.close();
			logger.debug("Connection closed successfully");
		}
		if (out != null) {
			out.close();
			logger.debug("Printwriter closed successfully");
		}

	}

	private PreparedStatement createPreparedStatement(Connection connection, String query) throws SQLException {
		return connection.prepareStatement(query);

	}

	private List<String> addQueriesRelatedToInsert(HttpServletRequest paramServletRequest,
			HttpServletResponse paramServletResponse) {
		List<String> queries = new ArrayList<String>();
		queries.add(getDatabase());
		queries.add(getInsertQuery(paramServletRequest, paramServletResponse));
		return queries;

	}

	private String getDatabase() {
		return "use loginappuser;";
	}

	private String getInsertQuery(HttpServletRequest request, HttpServletResponse response) {
		// TODO: remove 0 and record number of times the link is clicked
		return "insert into first_time_user(id, username, password) values(0" + " ,'" + request.getParameter("userName")
				+ "', '" + request.getParameter("password") + "');";
	}

	private ArrayList<String> addQueriesRelatedToSelect() {
		ArrayList<String> listOfQueries = new ArrayList<String>();
		String retrieveDatabase = getDatabase();
		String usernamePasswordExists = getSelectStatement();
		listOfQueries.add(retrieveDatabase);
		listOfQueries.add(usernamePasswordExists);
		return listOfQueries;
	}

	private void executeQueries(Map<String, String> columnValues, Connection connection)
			throws ServletException, IOException, InvalidInputException, SQLException {
		ArrayList<String> listOfQueries = addQueriesRelatedToSelect();
		for (String query : listOfQueries) {
			handleQuery(columnValues, connection, query);
		}
	}

	private void handleQuery(Map<String, String> columnValues, Connection connection, String query)
			throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int rs = 0;
		preparedStatement = createPreparedStatement(connection, query);
		if (isQuerySelect(query)) {
			resultSet = preparedStatement.executeQuery();
			if (resultSet != null) {
				addColumnDatasInMap(resultSet, columnValues);
			}

		} else {
			logger.debug("Not select query: ");
			rs = preparedStatement.executeUpdate();
			getSqlReturn(rs);
		}

	}

	private void addColumnDatasInMap(ResultSet resultSet, Map<String, String> columnValues) throws SQLException {
		String userName = "";
		String password = "";
		while (resultSet.next()) {
			// TODO: make 1, 2 etc dynamic
			userName = resultSet.getString(1);
			password = resultSet.getString(2);
			columnValues.put(userName, password);
		}
	}

	private void checkUserNameForLoginAndSignup(HttpServletRequest request, Connection connection,
			HttpServletResponse response, PrintWriter out) throws Exception {
		Map<String, String> columnValues = new HashMap<String, String>();
		executeQueries(columnValues, connection);
		if (request.getParameter(Constants.ATTRIBUTE_USERNAME) != null) {
			handleSignInSignUp(columnValues, response, request, connection, out);
		}
	}

	private void handleSignInSignUp(Map<String, String> columnValues, HttpServletResponse response,
			HttpServletRequest request, Connection connection, PrintWriter out) throws Exception {
		Set<Entry<String, String>> usernamePassword = null;
		String pageId = request.getParameter(Constants.ATTRIBUTE_PAGEID);
		usernamePassword = columnValues.entrySet();
		LoginAppModel model = null;
		if (request.getSession() != null) {
			model = (LoginAppModel) request.getSession(false).getAttribute(Constants.SESSION_LOGIN_MODEL);
			Set<String> userNames = columnValues.keySet();
			if (model != null && !pageId.isEmpty() && pageId != null
					&& pageId.equalsIgnoreCase(Constants.ATTRIBUTE_LOGIN)) {
				validateUserNamePasswordForLoginFlow(userNames, model, usernamePassword, request, response, out);
			} else if (userNames.contains(request.getParameter(Constants.ATTRIBUTE_USERNAME)) && !pageId.isEmpty()
					&& pageId != null && pageId.equalsIgnoreCase(Constants.ATTRIBUTE_SIGNUP)) {
				throw new InvalidInputException(
						"Please signUp with another username because that user already exists. ");
			} else {
				signUpSuccess(request, response, connection, out);
			}
		}

	}

	private void validateUserNamePasswordForLoginFlow(Set<String> userNames, LoginAppModel model,
			Set<Entry<String, String>> usernamePassword, HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws InvalidInputException, ServletException, IOException {
		if (!userNames.contains(model.getUserName())) {
			throw new InvalidInputException("Please enter a valid user name. That username does not exist.");
		} else if (model.getUserName() != null) {
			String userNameFromMap = "";
			String passwordFromMap = "";
			for (Entry<String, String> entry : usernamePassword) {
				userNameFromMap = entry.getKey();
				passwordFromMap = entry.getValue();
				if (model.getUserName().equalsIgnoreCase(userNameFromMap)) {
					validateUserWithPassWordEntered(model, passwordFromMap, request, response, out);
				}

			}

		}

	}

	private void validateUserWithPassWordEntered(LoginAppModel model, String passwordFromMap,
			HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws InvalidInputException, ServletException, IOException {
		String userName = "";
		if (!model.getPassword().equalsIgnoreCase(passwordFromMap)) {
			throw new InvalidInputException("The password does not match to that in the server. ");
		} else {
			if (request.getSession(false) != null) {
				if (model != null) {
					userName = model.getUserName();
					request.getSession(false).setAttribute(Constants.ATTRIBUTE_USERNAME, userName);
					RequestDispatcher rd = request.getRequestDispatcher(Constants.SUCCESS);
					rd.include(request, response);
				}
			}
		}

	}

	private void signUpSuccess(HttpServletRequest request, HttpServletResponse response, Connection connection,
			PrintWriter out) throws Exception {
		PreparedStatement preparedStatement = null;
		int resultSet = 0;
		List<String> queriesList = addQueriesRelatedToInsert(request, response);
		for (String result : queriesList) {
			preparedStatement = connection.prepareStatement(result);
			resultSet = preparedStatement.executeUpdate();
			getSqlReturn(resultSet);
		}
		out.write("Account created successfully. ");
	}

	private void getSqlReturn(int resultSet) {
		if (resultSet > 0) {
			logger.debug("The row count for SQL DML is: " + resultSet);
		} else {
			logger.debug("SQL statement returns nothing");
		}

	}

	private boolean isQuerySelect(String query) {
		return query.startsWith(SELECT) ? true : false;
	}

	private String getSelectStatement() {
		return "select userName, password from first_time_user;";
	}
}
