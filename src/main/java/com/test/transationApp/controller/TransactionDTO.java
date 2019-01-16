package com.test.transationApp.controller;

public class TransactionDTO {

	private String amount;

	private String timeStamp;

	public TransactionDTO() {
		super();
	}

	public TransactionDTO(String amount, String timeStamp) {
		super();
		this.amount = amount;
		this.timeStamp = timeStamp;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
}
