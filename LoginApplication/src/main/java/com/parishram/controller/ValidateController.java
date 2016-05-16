package com.parishram.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.parishram.common.Constants;
import com.parishram.exception.InvalidInputException;
import com.parishram.model.LoginAppModel;

/**
 * The main purpose of this class is to validate the username and password.
 * 
 * @author Yogesh
 *
 */
public class ValidateController {

	/**
	 * This method checks if the username or the password is blank. If they are
	 * blank,
	 * 
	 * <pre>
	 * InvalidInputException
	 * </pre>
	 * 
	 * is thrown. If they are not blank, the username and the password is
	 * validated and stored in a session object {@code model}
	 * 
	 * @param req
	 * @throws InvalidInputException
	 */
	public void handleInput(HttpServletRequest req) throws InvalidInputException {
		String userName = req.getParameter(Constants.ATTRIBUTE_USERNAME);
		String password = req.getParameter(Constants.ATTRIBUTE_PASSWORD);
		LoginAppModel model = null;
		if (userName.equalsIgnoreCase(null) || req.getParameter(Constants.ATTRIBUTE_USERNAME).equalsIgnoreCase("")) {
			throw new InvalidInputException("Please enter user name");
		}
		if (password.equalsIgnoreCase(null) || req.getParameter(Constants.ATTRIBUTE_PASSWORD).equalsIgnoreCase("")) {
			throw new InvalidInputException("Please enter password");
		} else {
			model = validateInput(userName, password);
			setSessionObject(req, model);
		}
	}

	/**
	 * This method validates the input from user - username and password.
	 * Following are considered during validation:<br>
	 * <ul>
	 * <li>{@code userName} should be 0-8 characters long</li>
	 * <li>{@code password} should be 8 characters long exactly</li>
	 * <li>{@code password} should contain a combination of <strong>atleast
	 * one</strong> smallcase, one uppercase, one numeric, and one special
	 * character each</li>
	 * </ul>
	 * </br>
	 * Once, the username and password is validated, it is stored in a session
	 * object {@code model}
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws InvalidInputException
	 */
	private LoginAppModel validateInput(String userName, String password) throws InvalidInputException {
		LoginAppModel model = new LoginAppModel();
		if (userName.length() < 3 || userName.length() > 8) {
			throw new InvalidInputException("The User Name should be between 0-8 characters long.");
		} else {
			model.setUserName(userName);
		}
		if (password.length() != 8) {
			throw new InvalidInputException("Password should be 8 characters long.");
		}
		if (!isPatternMatch(password)) {
			throw new InvalidInputException("Password should contain a combination of one uppercase letter, lowercase letter, a number, and a special character");
		} else {
			model.setPassword(password);
		}
		return model;
	}

	/**
	 * This code returns true if the password matches to a combination as
	 * described in {@link Constants#PATTERN} otherwise false.
	 * 
	 * @param password
	 * @return
	 */
	private boolean isPatternMatch(String password) {
		Pattern pattern = Pattern.compile(Constants.PATTERN);
		Matcher match = pattern.matcher(password);
		return match.matches();
	}

	/**
	 * This method sets the {@code model} object in the session
	 * 
	 * @param req
	 * @param model
	 */
	private void setSessionObject(HttpServletRequest req, LoginAppModel model) {
		HttpSession session = req.getSession(true);
		session.setAttribute(Constants.SESSION_LOGIN_MODEL, model);
	}
}
