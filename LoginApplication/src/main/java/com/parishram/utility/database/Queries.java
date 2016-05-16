package com.parishram.utility.database;

public enum Queries {
	GET_SCHEMA("USE LOGINAPPUSER;"), SELECT_LOGIN("SELECT USERNAME, PASSWORD FROM FIRST_TIME_USER WHERE USERNAME = ? AND PASSWORD = ?;"), SIGNUP(
			"INSERT INTO FIRST_TIME_USER(USERNAME, PASSWORD) VALUES (?, ?)");
	private String query;

	Queries(String query) {
		this.query = query;
	}

	public String getValue() {
		return query;
	}

}
