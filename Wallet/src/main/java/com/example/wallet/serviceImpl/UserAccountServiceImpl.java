package com.example.wallet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.wallet.dto.UserAccountDTO;
import com.example.wallet.dto.mapper.UserAccountMapper;
import com.example.wallet.entity.Account;
import com.example.wallet.entity.User;
import com.example.wallet.exceptions.UserNotFoundException;
import com.example.wallet.repository.UserAccountRepository;
import com.example.wallet.service.UserAccountService;
import com.google.common.collect.Lists;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public User userAccountByPK(Long userAccountId) throws UserNotFoundException {
		return userAccountRepository.findById(userAccountId).orElseThrow(
				() -> new UserNotFoundException(String.format("userAccount with '%d' not found ", userAccountId)));
	}

	/**
	 * this operations registers a user and creates and userAccount for him/her with
	 * minimal details
	 */
	@Override
	@Transactional
	public User save(User userAccount) throws Exception {
		if (userAccount.getUserName() != null) {
			if (userAccount.getUserName().length() < 5) {
				throw new Exception("user name is should be 5 characters of more");
			}
			return userAccountRepository.save(userAccount);
		}
		throw new Exception("user name is mandatory");
	}

	/**
	 * this operation updates a users userAccount information and checks for
	 * concurrent user modification
	 */
	@Override
	@Transactional
	public User update(User userAccount, Long userAccountId) throws Exception {
		if (userAccount.getUserName() != null) {
			userAccount.setId(userAccountId);
			try {
				return userAccountRepository.save(userAccount);
			} catch (Exception e) {
				throw new Exception("Try again");
			}
		}
		throw new Exception("user name is mandatory");

	}

	/**
	 * this operation gets all userAccount lists and their respective transaction
	 * transactions
	 */
	@Override
	public List<User> getList() {
		return Lists.newArrayList(userAccountRepository.findAll());
	}
	@Override
	public User createUser(UserAccountDTO userAccountDto) {
		 User userAccount = UserAccountMapper.dtoToDO(userAccountDto);
		 Account account = new Account();
		 account.setAmount(0);
		 userAccount.setAccount(account);
		return  userAccountRepository.save(userAccount);
	}

	@Override
	@Transactional
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return userAccountRepository.findById(id).get();
	}
   

}