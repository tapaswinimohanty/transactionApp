package com.test.transationApp.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private Long transactionId;

	@Column(name="amount")
	private BigDecimal amount;

    @DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name="timeStamp")
	private Instant timeStamp;
	
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTimeStamp;

	public Transaction() {
		super();
	}

	public Transaction(Instant timeStamp,  BigDecimal amount,LocalDateTime createdTimeStamp) {
		super();
		this.timeStamp = timeStamp;
		this.amount = amount;
		this.createdTimeStamp = createdTimeStamp;
	}
	public Transaction(Instant timeStamp, Long transactionId, BigDecimal amount) {
		super();
		this.timeStamp = timeStamp;
		this.transactionId = transactionId;
		this.amount = amount;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Instant timeStamp) {
		this.timeStamp = timeStamp;
	}

	public LocalDateTime getCreatedTimeStamp() {
		return createdTimeStamp;
	}


}
