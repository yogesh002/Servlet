package com.parishram.common;

/**
 * It contains all the constants defined in the LoginServlet web application
 * 
 * @author Yogesh
 *
 */
public class Constants {
	public static final String ATTRIBUTE_PASSWORD = "password";
	public static final String ATTRIBUTE_USERNAME = "userName";
	public static final String ATTRIBUTE_PAGEID = "pageid";
	public static final String ATTRIBUTE_LOGIN = "login";
	public static final String ATTRIBUTE_SIGNUP = "signup";
	public static final String SESSION_LOGIN_MODEL = "loginmodel";
	public static final String ATTRIBUTE_EXCEPTION = "exceptions";
	public static final String MESSAGE_TRY_AGAIN = "Please try again.";
	public static final String PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=]).{8}$";
	public static final String SUCCESS = "/HTML/profile.jsp";
	public static final String FAILURE = "/HTML/error.html";
	public static final String LOGIN = "/HTML/login.html";
}
