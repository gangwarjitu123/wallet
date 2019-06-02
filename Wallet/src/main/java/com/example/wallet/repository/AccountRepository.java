package com.example.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wallet.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
