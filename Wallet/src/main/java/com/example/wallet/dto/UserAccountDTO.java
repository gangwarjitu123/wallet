package com.example.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Manish Doodi
 *
 */

/** Client Facing Model of Transaction **/
public class UserAccountDTO {

	@ApiModelProperty(required = false, hidden = true)
	private Long id;
	private String userName;
	@NotNull
	private Long phone;
	private String email;
	private LocalDateTime createdAt;

	@ApiModelProperty(required = false, hidden = true)
	private BigDecimal balance;

	public UserAccountDTO() {
	}

	public UserAccountDTO(UserAccountDTOBuilder builder) {
		id = builder.id;
		userName = builder.userName;
		email = builder.email;
		createdAt = builder.createdAt;
		balance = builder.balance;
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


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}



	public static class UserAccountDTOBuilder {

		private Long id;
		private String userName;
		private String email;
		private Long phone;
		private LocalDateTime createdAt;
		private BigDecimal balance;

		public UserAccountDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public UserAccountDTOBuilder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public UserAccountDTOBuilder setEmail(String email) {
			this.email = email;
			return this;
		}


		public UserAccountDTOBuilder setBalance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}
		
		public UserAccountDTOBuilder setPhone(Long phone) {
			this.phone = phone;
			return this;
		}

		public UserAccountDTOBuilder setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public UserAccountDTO build() {
			return new UserAccountDTO(this);
		}

	}

}
