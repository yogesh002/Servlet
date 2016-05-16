package com.parishram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.parishram.common.Constants;
import com.parishram.dao.LoginDao;
import com.parishram.exception.InvalidInputException;
import com.parishram.model.LoginAppModel;

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (req.getSession(false) != null) {
			req.getSession(false).invalidate();
		}
		resp.sendRedirect("HTML/signup.html");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = null;
		PrintWriter out = resp.getWriter();
		try {
			ValidateController loginDelegate = new ValidateController();
			loginDelegate.handleInput(req);
			LoginAppModel loginAppModel = (LoginAppModel) req.getSession(false).getAttribute(Constants.SESSION_LOGIN_MODEL);
			LoginDao loginDao = new LoginDao(loginAppModel.getUserName(), loginAppModel.getPassword());
			Map<String, String> userCredentialsInDb = loginDao.provideLoginStatus();
			if (isUserCredentialsExistInDb(loginAppModel, req, userCredentialsInDb)) {
				rd = req.getRequestDispatcher(Constants.SUCCESS);
				req.getSession(false).setAttribute(Constants.USERNAME, loginAppModel.getUserName());
				rd.include(req, resp);
			}
		} catch (InvalidInputException e) {
			rd = req.getRequestDispatcher(Constants.LOGIN);
			rd.include(req, resp);
			out.write("<p style='color:black; font-weight: bold; text-align:center; padding: 10px; background-color: pink; width: 500px; margin: auto;'>" + e.getMessage() + "</p>");
		}
		// TODO: create a separate error page to handle database connection
		// issue
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isUserCredentialsExistInDb(LoginAppModel loginAppModel, HttpServletRequest request, Map<String, String> userCredentialsInDb) {
		// TODO: what if session is null?
		if (loginAppModel.getUserName().equalsIgnoreCase(userCredentialsInDb.get(Constants.USERNAME)) && loginAppModel.getPassword().equalsIgnoreCase(userCredentialsInDb.get(Constants.PASSWORD))) {
			return true;
		}
		return false;
	}

}
