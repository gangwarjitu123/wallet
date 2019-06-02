package com.example.wallet.service;

import java.util.List;

import com.example.wallet.dto.UserAccountDTO;
import com.example.wallet.entity.User;
import com.example.wallet.exceptions.UserNotFoundException;

/**
 * @author Deepak Garg
 *
 */

/** Service for Users */
public interface UserAccountService {

	/**
	 * Save a user account
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	User save(User t) throws Exception;

	/**
	 * Update a user account
	 * 
	 * @param user account
	 * @param user id
	 * @return
	 * @throws Exception
	 */
	User update(User t, Long id) throws Exception;

	/**
	 * @return list of users
	 */
	List<User> getList();

	/**
	 * @param accountId
	 * @return user account by account id
	 * @throws Exception
	 */
	User userAccountByPK(Long accountId) throws UserNotFoundException;
	

	User createUser(UserAccountDTO userAccountDTO);
	
	User getUserById(Long id);
}