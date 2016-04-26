package com.parishram.login;

/**
 * This is a simple POJO class that holds the request parameters from the
 * browser or the UI. <br>
 * The object of this class will be stored in session object later
 * 
 * @author Yogesh
 *
 */
public class LoginAppModel {
	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
