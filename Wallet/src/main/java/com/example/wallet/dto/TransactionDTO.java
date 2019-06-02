package com.example.wallet.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Manish Doodi
 *
 */

/** Client Facing Model of Transaction **/
public class TransactionDTO {

	@ApiModelProperty(required = false, hidden = true)
	private Long id;

	@ApiModelProperty(required = false, hidden = true)
	private Long userAccountId;

	private double amount;
	private String details;
	private LocalDateTime transactionDate;
	private String transactionReference;
	private String transcationType;

	public TransactionDTO() {
	}

	public TransactionDTO(TransactionDTOBuilder builder) {
		id = builder.id;
		userAccountId = builder.userAccountId;
		amount = builder.amount;
		details = builder.details;
		transactionDate = builder.transactionDate;
		transactionReference = builder.transactionReference;
		transcationType = builder.transactionType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}


	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	

	public String getTranscationType() {
		return transcationType;
	}

	public void setTranscationType(String transcationType) {
		this.transcationType = transcationType;
	}



	public static class TransactionDTOBuilder {

		private Long id;
		private Long userAccountId;
		private double amount;
		private String details;
		private LocalDateTime transactionDate;
		private String transactionReference;
		private String transactionType;

		public TransactionDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public TransactionDTOBuilder setUserAccountId(Long userAccountId) {
			this.userAccountId = userAccountId;
			return this;
		}

		public TransactionDTOBuilder setAmount(double amount) {
			this.amount = amount;
			return this;
		}

		public TransactionDTOBuilder setDetails(String details) {
			this.details = details;
			return this;
		}

		public void setTransactionDate(LocalDateTime transactionDate) {
			this.transactionDate = transactionDate;
		}

		public TransactionDTOBuilder setTransactionReference(String transactionReference) {
			this.transactionReference = transactionReference;
			return this;
		}
		
		public TransactionDTOBuilder setTransactionType(String transactionType) {
			this.transactionType = transactionType;
			return this;
		}

		public TransactionDTO build() {
			return new TransactionDTO(this);
		}

	}

}
