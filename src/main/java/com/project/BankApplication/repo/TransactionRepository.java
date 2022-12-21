package com.project.BankApplication.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.BankApplication.models.Transaction;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccountIdOrderByInitiationDate(long id);
}
