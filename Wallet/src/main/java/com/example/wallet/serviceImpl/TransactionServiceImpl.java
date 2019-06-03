package com.example.wallet.serviceImpl;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.dto.TransactionDTO;
import com.example.wallet.dto.mapper.TransactionMapper;
import com.example.wallet.entity.Account;
import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.User;
import com.example.wallet.exceptions.BalanceLowException;
import com.example.wallet.exceptions.UserNotFoundException;
import com.example.wallet.repository.AccountRepository;
import com.example.wallet.repository.TransactionRepository;
import com.example.wallet.service.TransactionService;
import com.example.wallet.service.UserAccountService;
import com.google.common.collect.Lists;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserAccountService accountService;
	@Autowired
	AccountRepository accountRepository;
	

	/**
	 * retrieve transactions by their transaction reference this operations is used
	 * to validate if a transaction ref has been used previously
	 */
	@Override
	public Transaction transactionByRef(Long txnRef) throws UserNotFoundException {
		return transactionRepository.getTransactionByRef(txnRef).orElseThrow(
				() -> new UserNotFoundException(String.format("transaction with ref '%d' doesnt exist", txnRef)));
	}

	/**
	 * this operations registers a transaction on behalf of user debit/credits, it
	 * also validates if a user has insufficient funds if a debit is to be made
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Transaction createTransaction(Transaction transaction) throws BalanceLowException {
		User user=accountService.getUserById(transaction.getUserAccount().getId());
		double balance = user.getAccount().getAmount();
        balance= balance+transaction.getAmount();
        user.getAccount().setAmount(balance);
        try {
			accountService.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (balance>= 0) {
			transaction.setAccount(user.getAccount());
			transaction.setUserAccount(user);
			return transactionRepository.save(transaction);
		}

		throw new BalanceLowException(String.format("user's balance is %.2f and cannot perform a transaction of %.2f ",
				balance, transaction.getAmount()));

	}

	@Override
	public Transaction update(Transaction transaction, Long id) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<Transaction> getList() {
		return Lists.newArrayList(transactionRepository.findAll());
	}

	@Override
	public List<Transaction> transactionsByUserAccountID(Long accountId) {
		return transactionRepository.getTransactionsForUser(accountId);
	}

	@Override
	public double balanceByUserAccountID(Long accountId) {
		return transactionRepository.getBalance(accountId);
	}

	@Override
	public List<Transaction> transactions() {
		return Lists.newArrayList(transactionRepository.findAll());
	}

	@Override
	public Transaction save(Transaction transaction) throws Exception {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public HashMap<String,String> transfer(TransactionDTO walletDTO, Long toUserAccountId, Long fromUserAccountId) {
		User to=accountService.getUserById(toUserAccountId);
		User from=accountService.getUserById(fromUserAccountId);
		double fromBalance=from.getAccount().getAmount()-walletDTO.getAmount();
		from.getAccount().setAmount(fromBalance);

		double toBalance=to.getAccount().getAmount()+walletDTO.getAmount();
		to.getAccount().setAmount(toBalance);
		Transaction transaction = new Transaction();
		transaction.setUserAccount(from);
		transaction.setAccount(from.getAccount());
		transaction.setAmount(walletDTO.getAmount());
		transaction.setTransactionDate(new Date());
		transaction.setTransactionType(1);
		transaction.setTransactionReference(UUID.randomUUID().toString());
		try {
			accountService.save(from);
			accountService.save(to);
			 Transaction tarTransaction1=transactionRepository.save(transaction);
			HashMap<String,String> map= new HashMap<>();
			 map.put("referanceId",tarTransaction1.getTransactionReference());
			 map.put("message","transaction process successfully");
			return  map;
		}catch (Exception e){
			log.error("error in transaction");
		}

		return  null;
	}

	@Override
	public List<HashMap<String, String>> history(Long id) throws UserNotFoundException, BalanceLowException {
		List<Transaction> list=transactionRepository.getTransactionsForUser(id);
		List<HashMap<String,String>> response= new ArrayList<>();
		for (Transaction transaction : list) {
			  HashMap<String,String> map= new HashMap<>();
			  map.put("phone",transaction.getUserAccount().getPhone().toString());
			  map.put("amountTransfer",String.valueOf(transaction.getAmount()));
			  map.put("date",transaction.getTransactionDate().toString());
			  map.put("transactionReference",transaction.getTransactionReference());
			  response.add(map);
		}
		return response;
	}

}