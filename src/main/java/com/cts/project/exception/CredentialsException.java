package com.cts.project.exception;

//This exception will be thrown when credentials did not match
public class CredentialsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5167626202122427354L;

	public CredentialsException(String message) {
		super(message);
	}

}
