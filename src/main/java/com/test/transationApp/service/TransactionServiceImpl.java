package com.test.transationApp.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.test.transationApp.controller.DeleteTransactionDTO;
import com.test.transationApp.controller.TransactionDTO;
import com.test.transationApp.controller.TransactionStatisticsResponse;
import com.test.transationApp.entity.Transaction;
import com.test.transationApp.repository.TransactionRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Long createTransaction(TransactionDTO transactionDTO) throws  ServiceException {

		validateTransaction(transactionDTO);
		Transaction transaction = new Transaction(Instant.parse(transactionDTO.getTimeStamp()),new BigDecimal(transactionDTO.getAmount()),LocalDateTime.now());
		transaction = transactionRepository.save(transaction);
		return transaction.getTransactionId();

	}

	@Override
	public void deleteAllTransaction(DeleteTransactionDTO deletetransactionDTO) {
		transactionRepository.deleteAll();

	}


	private void validateTransaction(TransactionDTO transactionDTO) throws ServiceException {
		if (transactionDTO == null) {
			throw new RuntimeException("transaction Not found ");
		}

		if(Instant.parse(transactionDTO.getTimeStamp()).isAfter(Instant.parse(Instant.now().atOffset(ZoneOffset.UTC).toString()))){
			throw new ValidationException("Transction date is future date");

		}

		if(Duration.between(Instant.parse(transactionDTO.getTimeStamp()), Instant.now()).compareTo(Duration.ofSeconds(60)) > 0){
			throw new OlderTransactionException("this transaction is older transaction");
		}


	}




	@Override
	public TransactionStatisticsResponse findtransactionStatistics() {
		Instant timeStamp = Instant.now();
		TransactionStatisticsResponse transactionStatisticsResponse = new TransactionStatisticsResponse();

		Optional<List<Transaction>> transactionsListOptional = transactionRepository.findByTimeStampBetween((timeStamp.minus(60, ChronoUnit.SECONDS)),timeStamp); 

		transactionsListOptional .map(transactionsList -> {
			BigDecimal sum = transactionsList.stream().map(Transaction::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal average = sum.divide(new BigDecimal(transactionsList.size()),BigDecimal.ROUND_HALF_UP);
			average = average.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal max = transactionsList.stream().max(Comparator.comparing(transaction ->transaction.getAmount())).get().getAmount();
			max = max.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal min = transactionsList.stream().min(Comparator.comparing(transaction ->transaction.getAmount())).get().getAmount();
			min = min.setScale(2, BigDecimal.ROUND_HALF_UP);

			transactionStatisticsResponse .setSum(sum);
			transactionStatisticsResponse .setAvg(average);
			transactionStatisticsResponse .setMax(max);
			transactionStatisticsResponse .setMin(min);
			transactionStatisticsResponse .setCount(Long.valueOf(transactionsList.size()));

			return   transactionStatisticsResponse  ; 

		}
				).orElse(transactionStatisticsResponse);

		return transactionStatisticsResponse;
	}

	@Override
	public Long findAllTransaction() throws ServiceException {
		return transactionRepository.count();
	}

	@Override
	public TransactionStatisticsResponse findtransactionStatisticsForTimeStamp() {

		LocalDateTime timeStamp = LocalDateTime.now();
		TransactionStatisticsResponse transactionStatisticsResponse = new TransactionStatisticsResponse();

		Optional<List<Transaction>> transactionsListOptional = transactionRepository.findByCreatedTimeStampBetween((timeStamp.minus(60, ChronoUnit.SECONDS)),timeStamp); 

		transactionsListOptional .map(transactionsList -> {
			BigDecimal sum = transactionsList.stream().map(Transaction::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal average = sum.divide(new BigDecimal(transactionsList.size()),BigDecimal.ROUND_HALF_UP);
			average = average.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal max = transactionsList.stream().max(Comparator.comparing(transaction ->transaction.getAmount())).get().getAmount();
			max = max.setScale(2, BigDecimal.ROUND_HALF_UP);

			BigDecimal min = transactionsList.stream().min(Comparator.comparing(transaction ->transaction.getAmount())).get().getAmount();
			min = min.setScale(2, BigDecimal.ROUND_HALF_UP);

			transactionStatisticsResponse .setSum(sum);
			transactionStatisticsResponse .setAvg(average);
			transactionStatisticsResponse .setMax(max);
			transactionStatisticsResponse .setMin(min);
			transactionStatisticsResponse .setCount(Long.valueOf(transactionsList.size()));

			return   transactionStatisticsResponse  ; 

		}
				).orElse(transactionStatisticsResponse);

		return transactionStatisticsResponse;

	}









}
