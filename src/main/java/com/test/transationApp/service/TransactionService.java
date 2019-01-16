package com.test.transationApp.service;


import com.test.transationApp.controller.DeleteTransactionDTO;
import com.test.transationApp.controller.TransactionDTO;
import com.test.transationApp.controller.TransactionStatisticsResponse;


public interface TransactionService {


	Long createTransaction(TransactionDTO transactionDTO) throws ServiceException;

	void deleteAllTransaction(DeleteTransactionDTO deletetransactionDTO) throws ServiceException;

	TransactionStatisticsResponse findtransactionStatistics() ;

	Long findAllTransaction() throws ServiceException;

	TransactionStatisticsResponse findtransactionStatisticsForTimeStamp();



}
