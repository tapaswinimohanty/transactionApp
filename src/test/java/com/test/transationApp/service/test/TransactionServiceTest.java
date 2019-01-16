/**
 * All rights are reserved. This code is developed by lokeshreddy2007@gmail.com.
 */
package com.test.transationApp.service.test;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.test.transationApp.TransationAppApplication;
import com.test.transationApp.controller.DeleteTransactionDTO;
import com.test.transationApp.controller.TransactionDTO;
import com.test.transationApp.controller.TransactionStatisticsResponse;
import com.test.transationApp.entity.Transaction;
import com.test.transationApp.repository.TransactionRepository;
import com.test.transationApp.service.OlderTransactionException;
import com.test.transationApp.service.ServiceException;
import com.test.transationApp.service.TransactionService;
import com.test.transationApp.service.ValidationException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = TransationAppApplication.class)
@Transactional(propagation = Propagation.REQUIRED)
public class TransactionServiceTest {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	public void testStatistics() throws ServiceException {
		transactionService.deleteAllTransaction(new DeleteTransactionDTO());
		Instant instant = Instant.now();
		transactionService.createTransaction(new TransactionDTO("1223.77788",Instant.now().atOffset( ZoneOffset.UTC ).toString()));
		transactionService.createTransaction(new TransactionDTO("123.7712",instant.minusSeconds(10).atOffset(ZoneOffset.UTC).toString()));
		transactionService.createTransaction(new TransactionDTO("143.77",instant.minusSeconds(18).atOffset(ZoneOffset.UTC).toString()));
		transactionService.createTransaction(new TransactionDTO("183.18",instant.minusSeconds(30).atOffset(ZoneOffset.UTC).toString()));

		TransactionStatisticsResponse response = transactionService.findtransactionStatistics();

		BigDecimal sum = new BigDecimal(  "1674.50");
		sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertEquals(sum, response.getSum());

		BigDecimal avg = new BigDecimal(  "418.625");
		avg = avg.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertEquals(avg, response.getAvg());		


		BigDecimal min = new BigDecimal( "123.77");
		min = min.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertEquals(min, response.getMin());

		BigDecimal max = new BigDecimal("1223.78");
		max = max.setScale(2, BigDecimal.ROUND_HALF_UP);
		Assert.assertEquals(max, response.getMax());


		Assert.assertEquals(new Long(4), response.getCount());


	}
	
	

	@Test	
	public void testPostTranscation() throws ServiceException {
		TransactionDTO transactiondto = new TransactionDTO();
		transactiondto.setAmount("121.99");
		transactiondto.setTimeStamp(Instant.now().toString());
		Long transcationId = transactionService.createTransaction(transactiondto);		
		Transaction newTxn = this.transactionRepository.findOne(transcationId);

		Assert.assertEquals(transcationId, newTxn.getTransactionId());


	}


	@Test
	public void testDeleteAllTransaction() throws Exception {
		TransactionDTO transactiondto = new TransactionDTO();
		transactiondto.setAmount("121.99");
		transactiondto.setTimeStamp(Instant.now().toString());

		transactionService.createTransaction(transactiondto);	


		TransactionDTO transactiondtoTemp = new TransactionDTO();
		transactiondtoTemp.setAmount("1233.99");
		transactiondtoTemp.setTimeStamp(Instant.now().toString()); 

		transactionService.createTransaction(transactiondtoTemp);	

		transactionService.deleteAllTransaction(new DeleteTransactionDTO());
		List<Transaction>  txnList = transactionRepository.findAll();
		assertTrue(txnList.isEmpty());
	}

	@Test(expected = OlderTransactionException.class)
	public void checkOlderTransaction ()	throws ServiceException {
		TransactionDTO transactiondto = new TransactionDTO();
		transactiondto.setAmount("121.99");
		transactiondto.setTimeStamp(Instant.now().minusSeconds(100).toString());
		transactionService. createTransaction(transactiondto);

	}

	@Test(expected = ValidationException.class)
	public void checkFutureTransaction ()	throws ServiceException {
		TransactionDTO transactiondto = new TransactionDTO();
		transactiondto.setAmount("121.99");
		transactiondto.setTimeStamp(Instant.now().plusSeconds(100).toString());
		transactionService. createTransaction(transactiondto);

	}


	@Test
	public void findTotalTransaction()	throws ServiceException {	
		TransactionDTO transactiondto = new TransactionDTO();
		transactiondto.setAmount("121.99");
		transactiondto.setTimeStamp(Instant.now().toString());
		transactionService. createTransaction(transactiondto);
		long count = transactionService. findAllTransaction();
		Assert.assertEquals(1, count);
	}

	@After
	public void afterTests() throws ServiceException {
		transactionService.deleteAllTransaction(new DeleteTransactionDTO());

	}


}
