package com.test.transationApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.test.transationApp.service.ServiceException;
import com.test.transationApp.service.TransactionService;

@Controller
public class TransactionController  {

	@Autowired
	private TransactionService  transactionService;
	
	@PostMapping(value = "/transactions")
	public ResponseEntity<TransactionResponce> createTransaction(@RequestBody TransactionDTO transactionDTO) throws ServiceException {
		transactionService.createTransaction(transactionDTO);
		return new ResponseEntity<TransactionResponce>(new TransactionResponce(),HttpStatus.CREATED);
	}

	
	
	
	@DeleteMapping(value = "/transactions")
	public ResponseEntity<TransactionResponce> deleteAllTransaction(@RequestBody DeleteTransactionDTO deletetransactionDTO) throws ServiceException {
		transactionService.deleteAllTransaction(deletetransactionDTO);
		return new ResponseEntity<TransactionResponce>(new TransactionResponce(), HttpStatus.NO_CONTENT);
	}

	
	@GetMapping(value = "/statistics")
	public ResponseEntity<TransactionStatisticsResponse> transactionStatistics() throws Exception {
		TransactionStatisticsResponse statisticsResponse = transactionService.findtransactionStatistics();
		return new ResponseEntity<TransactionStatisticsResponse>(statisticsResponse, HttpStatus.OK);

	}

	@GetMapping(value = "/statisticsForCurrentTime")
	public ResponseEntity<TransactionStatisticsResponse> transactionStatisticsForCurrentTimeStamp() throws Exception {
		TransactionStatisticsResponse statisticsResponse = transactionService.findtransactionStatisticsForTimeStamp();
		return new ResponseEntity<TransactionStatisticsResponse>(statisticsResponse, HttpStatus.OK);

	}
	


}
