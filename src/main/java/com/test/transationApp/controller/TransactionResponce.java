
package com.test.transationApp.controller;

import org.springframework.http.HttpStatus;


public class TransactionResponce {

	
	private HttpStatus statusCode;

	public TransactionResponce(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public TransactionResponce() {
		super();
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
