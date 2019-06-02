package com.example.wallet.dto.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.wallet.dto.UserAccountDTO;
import com.example.wallet.entity.User;

/**
 * @author Deepak Garg
 *
 */

/** The Class to map Database Objects and Data Transfer Objects */
public class UserAccountMapper {

	/** converts DTO to Database Object */
	public static User dtoToDO(UserAccountDTO a) {
		User account = new User.UserAccountBuilder()
				.setId(a.getId())
				.setUserName(a.getUserName())
				.setEmail(a.getEmail())
				.setPhone(a.getPhone())
				.build();
		return account;
	}

	/** Converts Database Object to DTO */
	public static UserAccountDTO doToDTO(User a) {
		double balance = a.getTransactions().stream().mapToDouble(o -> o.getAmount()).sum();
		UserAccountDTO dto = new UserAccountDTO.UserAccountDTOBuilder().setId(a.getId())
				.setCreatedAt(a.getCreatedAt()).setUserName(a.getUserName()).setId(a.getId()).setEmail(a.getEmail())
				.setBalance(new BigDecimal(balance)).build();
		return dto;
	}

	/** Converts List of Database Object to List of DTO */
	public static List<UserAccountDTO> doToDTOList(List<User> account) {
		return account.stream().map(UserAccountMapper::doToDTO).collect(Collectors.toList());
	}

}
