
package com.test.transationApp.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST) 
public class OlderTransactionException extends ServiceException {
	
	private static final long serialVersionUID = 1L;

	private String errorKey = "204";
	
	private  HttpStatus httpStatus;
	
	public String getErrorKey() {
		return errorKey;
	}


	public OlderTransactionException() {
		super();
	}

	public OlderTransactionException(HttpStatus httpStatus) {
		super();
		this.httpStatus = httpStatus;
	}

	public OlderTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

	public OlderTransactionException(String message) {
		super(message);
	}

	public OlderTransactionException(Throwable cause) {
		super(cause);
	}

}
