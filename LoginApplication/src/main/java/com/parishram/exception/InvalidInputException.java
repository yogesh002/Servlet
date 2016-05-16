package com.parishram.exception;

/**
 * Custom exception that is thrown in case the login input parameters from the
 * client is invalid
 * 
 * @author Yogesh
 *
 */
public class InvalidInputException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String message) {
		super(message);
	}
}
