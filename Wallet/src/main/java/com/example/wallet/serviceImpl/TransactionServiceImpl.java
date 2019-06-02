package com.example.wallet.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Transactional
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
	@Transactional(rollbackFor = RuntimeException.class)
	public Transaction createTransaction(Transaction transaction) throws BalanceLowException {
		User user=accountService.getUserById(transaction.getUserAccount().getId());
		double balance = user.getAccount().getAmount();
        balance= balance+transaction.getAmount();
        user.getAccount().setAmount(balance);
        try {
			accountService.save(user);
			accountRepository.save(user.getAccount());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (balance>= 0) {
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
	public List<Transaction> transfer(TransactionDTO walletDTO, Long toUserAccountId, Long fromUserAccountId)
			throws UserNotFoundException, BalanceLowException {
		List<Transaction> transactions = new ArrayList<>();

		if (accountService.userAccountByPK(fromUserAccountId) == null)
			throw new UserNotFoundException(String.format("userAccount with '%d' not found ", fromUserAccountId));
		if (accountService.userAccountByPK(toUserAccountId) == null) {
			throw new UserNotFoundException(String.format("userAccount with '%d' not found ", toUserAccountId));
		}
		Transaction sourceUserTransaction;
		Transaction destinationUserTransaction;

		walletDTO.setUserAccountId(fromUserAccountId);
		walletDTO.setAmount(walletDTO.getAmount());
		sourceUserTransaction = createTransaction(TransactionMapper.dtoToDO(walletDTO));
		transactions.add(sourceUserTransaction);

		walletDTO.setUserAccountId(toUserAccountId);
		walletDTO.setAmount(walletDTO.getAmount());
		destinationUserTransaction = createTransaction(TransactionMapper.dtoToDO(walletDTO));
		transactions.add(destinationUserTransaction);

		return transactions;
	}

}