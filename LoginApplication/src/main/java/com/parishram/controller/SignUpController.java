package com.parishram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.parishram.common.Constants;
import com.parishram.dao.SignupDao;
import com.parishram.exception.InvalidInputException;
import com.parishram.model.LoginAppModel;

public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(Constants.SIGNUP);
		rd.include(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = null;
		PrintWriter out = resp.getWriter();
		try {
			ValidateController loginDelegate = new ValidateController();
			loginDelegate.handleInput(req);
			LoginAppModel loginAppModel = (LoginAppModel) req.getSession(false).getAttribute(Constants.SESSION_LOGIN_MODEL);
			SignupDao signUpDao = new SignupDao(loginAppModel.getUserName(), loginAppModel.getPassword());
			int result = signUpDao.signUpForNewUser();
			// TODO: what if 0?
			if (result != 0) {
				rd = req.getRequestDispatcher("/JSP/success.jsp");
				req.getSession(false).setAttribute(Constants.USERNAME, loginAppModel.getUserName());
				rd.include(req, resp);
				out.write(
						"<p style='color:black; font-weight: bold; text-align:center; padding: 10px; background-color: pink; width: 500px; margin: auto;'>" + "ACCOUNT CREATED SUCCESSFULLY " + "</p>");
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
}
