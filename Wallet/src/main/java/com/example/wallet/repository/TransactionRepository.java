package com.example.wallet.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.wallet.entity.Transaction;

/**
 * @author Deepak Garg
 *
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	/**
	 * gets transaction by id
	 * 
	 * @param transaction Ref id
	 * @return transaction
	 */
	@Query(nativeQuery = true, value = "select * from transaction where transaction_reference = ?")
	Optional<Transaction> getTransactionByRef(Long txnRef);

	/**
	 * gets balance in account
	 * 
	 * @param accountId
	 * @return balance
	 */
	@Query(nativeQuery = true, value = "select ifnull(sum(amount),0.00) from transaction where user_account_id = ?")
	double getBalance(Long accountId);

	/**
	 * gets list of transactions of particular account
	 * 
	 * @param accountId
	 * @return list of transactions
	 */
	@Query(nativeQuery = true, value = "select * from transaction where user_id = ?")
	List<Transaction> getTransactionsForUser(Long accountId);
}
