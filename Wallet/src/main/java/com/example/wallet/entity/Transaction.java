package com.example.wallet.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
public class Transaction {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User userAccount;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="account_id")
	private Account account;
	@NotNull
	private double amount;

	/** Purpose of Transaction */
	private String details;

	@Temporal(javax.persistence.TemporalType.DATE)
	private Date transactionDate;
	@NotNull
	private String transactionReference;
	
	@JoinColumn(name="transaction_type")
	private int transactionType;
	

	public Transaction() {
	}

	public Transaction(User userAccount, double amount, String details, Date transactionDate,
			String transactionReference) {
		this.userAccount = userAccount;
		this.amount = amount;
		this.details = details;
		this.transactionDate = transactionDate;
		this.transactionReference = transactionReference;
	}

	public Transaction(TransactionBuilder builder) {
		id = builder.id;
		userAccount = new User(builder.userAccountId);
		amount = builder.amount;
		details = builder.details;
		transactionDate = builder.transactionDate;
		transactionReference = builder.transactionReference;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(User userAccount) {
		this.userAccount = userAccount;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionReference() {
		return transactionReference;
	} 

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	
	
	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 37 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Transaction other = (Transaction) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	public static class TransactionBuilder {

		private Long id;
		private Long userAccountId;
		private double amount;
		private String details;
		private Date transactionDate;
		private String transactionReference;

		public TransactionBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public TransactionBuilder setUserAccount(Long userAccountId) {
			this.userAccountId = userAccountId;
			return this;
		}

		public TransactionBuilder setAmount(double amount) {
			this.amount = amount;
			return this;
		}

		public TransactionBuilder setDetails(String details) {
			this.details = details;
			return this;
		}

		public TransactionBuilder setTransactionDate(Date transactionDate) {
			this.transactionDate = transactionDate;
			return this;
		}

		public TransactionBuilder setTransactionReference(String transactionReference) {
			this.transactionReference = transactionReference;
			return this;
		}

		public Transaction build() {
			return new Transaction(this);
		}

	}

}