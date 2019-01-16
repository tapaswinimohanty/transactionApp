
package com.test.transationApp.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.transationApp.entity.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Optional<List<Transaction>> findByTimeStampBetween(Instant from, Instant to);

	Optional<List<Transaction>> findByCreatedTimeStampBetween(LocalDateTime fromTime, LocalDateTime toTimeStamp);


}
