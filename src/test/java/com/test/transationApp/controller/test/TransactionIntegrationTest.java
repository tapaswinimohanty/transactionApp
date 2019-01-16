package com.test.transationApp.controller.test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.transationApp.TransationAppApplication;
import com.test.transationApp.controller.DeleteTransactionDTO;
import com.test.transationApp.controller.ErrorInfo;
import com.test.transationApp.controller.TransactionDTO;
import com.test.transationApp.controller.TransactionResponce;
import com.test.transationApp.controller.TransactionStatisticsResponse;
import com.test.transationApp.service.ServiceException;
import com.test.transationApp.service.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		TransationAppApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionIntegrationTest {
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TestRestTemplate template;



	@Test
	public void shouldCreateProfileSuccessfully() throws ServiceException {
		String date = Instant.parse(Instant.now().atOffset(ZoneOffset.UTC).toString()).toString();
		TransactionDTO transactionDTO = new TransactionDTO("1223.77",date);
		ResponseEntity<TransactionResponce> responseEntity = template.postForEntity("/transactions", transactionDTO, TransactionResponce.class);
		Assert.assertEquals(HttpStatus.CREATED , responseEntity.getStatusCode());
	}
	
	@Test
	public void shouldReturnAnOlderThanSixtySecondError() throws ServiceException {
		String date = Instant.parse((Instant.now().minusSeconds(100)).atOffset(ZoneOffset.UTC).toString()).toString();
		TransactionDTO transactionDTO = new TransactionDTO("1223.77",date);
		ErrorInfo error =  template.postForEntity("/transactions", transactionDTO, ErrorInfo.class).getBody();
		Assert.assertEquals(HttpStatus.NO_CONTENT.toString(), error.getErrorKey());

	}
	
	@Test
	public void shouldReturnAnFutureTransactionError() throws ServiceException {
		String date = Instant.parse((Instant.now().plusSeconds(100)).atOffset(ZoneOffset.UTC).toString()).toString();
		TransactionDTO transactionDTO = new TransactionDTO("1223.77",date);
		ErrorInfo error =  template.postForEntity("/transactions", transactionDTO, ErrorInfo.class).getBody();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.toString(), error.getErrorKey());

	}
	
	
	@Test
	public void shouldReturnAnUnparsableTransactionError() throws ServiceException {
		String date = Instant.parse((Instant.now().plusSeconds(100)).atOffset(ZoneOffset.UTC).toString()).toString();
		TransactionDTO transactionDTO = new TransactionDTO("1223.77BC",date);
		ErrorInfo error =  template.postForEntity("/transactions", transactionDTO, ErrorInfo.class).getBody();

		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.toString(), error.getErrorKey());

	}

	
	
	@Test
	public void deleteAllTransaction() throws ServiceException {
		template.delete("/transactions");
		long count = transactionService.findAllTransaction();
		Assert.assertEquals(0L, count);

	}
	
	
	@Test
	public void testStatistics() throws ServiceException {
		transactionService.deleteAllTransaction(new DeleteTransactionDTO());
		Instant instant = Instant.now();

		transactionService.createTransaction(new TransactionDTO("1223.77788",Instant.now().atOffset( ZoneOffset.UTC ).toString()));
		transactionService.createTransaction(new TransactionDTO("123.7712",instant.minusSeconds(10).atOffset(ZoneOffset.UTC).toString()));
		transactionService.createTransaction(new TransactionDTO("143.77",instant.minusSeconds(18).atOffset(ZoneOffset.UTC).toString()));
		transactionService.createTransaction(new TransactionDTO("183.18",instant.minusSeconds(12).atOffset(ZoneOffset.UTC).toString()));

	
		TransactionStatisticsResponse response =  template.getForEntity("/statistics", TransactionStatisticsResponse.class).getBody();


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
	
	


	@After
	public void afterTests() throws ServiceException {
		transactionService.deleteAllTransaction(new DeleteTransactionDTO());

	}



}
