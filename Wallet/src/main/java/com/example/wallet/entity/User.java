package com.example.wallet.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String userName;
	private String email;
	@NotNull
	private Long phone;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	@OneToMany(mappedBy = "userAccount", fetch = FetchType.EAGER)
	private Set<Transaction> transactions = new HashSet<>();
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="account_id")
	private Account account;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String userName, String email, LocalDateTime dateCreated, Long phone) {
		this.userName = userName;
		this.email = email;
		this.createdAt = dateCreated;
		this.phone = phone;
	}

	public User(Long id, String userName, String email, LocalDateTime dateCreated, Long phone) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.createdAt = dateCreated;
		this.phone = phone;
	}

	public User(UserAccountBuilder builder) {
		id = builder.id;
		userName = builder.userName;
		email = builder.email;
		createdAt = builder.createdAt;
		phone = builder.phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
	

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.id);
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
		final User other = (User) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	public static class UserAccountBuilder {

		private Long id;
		private String userName;
		private String email;
		private Long phone;
		private LocalDateTime createdAt;

		public Long getId() {
			return id;
		}

		public UserAccountBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public UserAccountBuilder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public UserAccountBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserAccountBuilder setPhone(Long phone) {
			this.phone = phone;
			return this;
		}

		public UserAccountBuilder setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public User build() {
			return new User(this);
		}

	}

}