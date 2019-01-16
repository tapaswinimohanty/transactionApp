package com.test.transationApp.entity.test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.transationApp.controller.TransactionStatisticsResponse;
import com.test.transationApp.entity.Transaction;
import com.test.transationApp.repository.TransactionRepository;
import com.test.transationApp.service.ServiceException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrasactionRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TransactionRepository repository;

	@Test
	public void testCreate() throws Exception {
		Instant oldTime = Instant.now();
		Transaction oldTxn = (Transaction) entityManager.persist(new Transaction(Instant.now(), new BigDecimal(1223.77788),LocalDateTime.now()));

		Transaction newTxn = this.repository.findOne(oldTxn.getTransactionId());
		assertThat(oldTxn.getAmount()).isEqualTo(newTxn.getAmount());
		assertThat(oldTxn.getTimeStamp().equals(oldTime));
	}

	

	


	@Test
	public void testDelete() throws Exception {
		entityManager.persist(new Transaction(Instant.now(), new BigDecimal(1223.77788),LocalDateTime.now()));
		entityManager.persist(new Transaction(Instant.now(), new BigDecimal(123.7712),LocalDateTime.now()));

		repository.deleteAll();
		List<Transaction>  txnList = repository.findAll();
		assertTrue(txnList.isEmpty());
	}

	@Test
	public void findTotalTransaction ()	throws ServiceException {	
		entityManager.persist(new Transaction(Instant.now(), new BigDecimal(121.99),LocalDateTime.now()));
		long count = repository.count();
		Assert.assertEquals(1, count);
	}



	@After
	public void afterTests() throws ServiceException {
		repository.deleteAll();

	}


}

