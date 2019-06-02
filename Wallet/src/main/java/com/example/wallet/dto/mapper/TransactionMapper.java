package com.example.wallet.dto.mapper;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.wallet.dto.TransactionDTO;
import com.example.wallet.entity.Transaction;

/**
 * @author Deepak Garg
 *
 */

/** The Class to map Database Objects and Data Transfer Objects */
public class TransactionMapper {

	/** converts DTO to Database Object */
	public static Transaction dtoToDO(TransactionDTO w) {
		Transaction transaction = new Transaction.TransactionBuilder().setUserAccount(w.getUserAccountId())
				.setAmount(w.getAmount()).setId(w.getId()).setDetails(w.getDetails())
				.setTransactionDate(new Date())
				.setTransactionReference(UUID.randomUUID().toString())
				.build();
		return transaction;
	}

	/** Converts Database Object to DTO */
	public static TransactionDTO doToDTO(Transaction w) {
		TransactionDTO transactionDTO = new TransactionDTO.TransactionDTOBuilder()
				.setUserAccountId(w.getUserAccount().getId()).setAmount(w.getAmount()).setId(w.getId())
				.setDetails(w.getDetails())
				.setTransactionReference(UUID.randomUUID().toString()).build();
		return transactionDTO;
	}

	/** Converts List of Database Object to List of DTO */
	public static List<TransactionDTO> doToDTOList(List<Transaction> txns) {
		return txns.stream().map(TransactionMapper::doToDTO).collect(Collectors.toList());
	}
}