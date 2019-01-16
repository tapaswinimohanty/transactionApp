package com.test.transationApp.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY) 
public class ValidationException extends ServiceException {

	private static final long serialVersionUID = 1L;


	private String errorKey = "422";

	public String getErrorKey() {
		return errorKey;
	}
	public ValidationException() {
		super();

	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);

	}

	public ValidationException(String message) {
		super(message);

	}

	public ValidationException(Throwable cause) {
		super(cause);

	}


}


