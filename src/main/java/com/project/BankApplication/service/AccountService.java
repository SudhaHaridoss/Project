package com.project.BankApplication.service;
/**
 * Account Service deals with all the manipulations.*/
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

import com.project.BankApplication.models.Account;
import com.project.BankApplication.repo.AccountRepository;
import com.project.BankApplication.repo.TransactionRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    
    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // Retreives all the Accounts information
    public List<Account> getAllAccount() {
    	 List<Account> account = accountRepository.findAll();
         return account;
    }

    //Retrieve the account information 
    public Account getAccount(int accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountNumber(accountNumber);

        account.ifPresent(value ->
                value.setTransactions(transactionRepository
                        .findBySourceAccountIdOrderByInitiationDate(value.getId())));

        return account.orElse(null);
    }
    
    
    // Creates new account for the requested user.
    public Account createAccount(String bankName, String ownerName, String cardType) {
    	Logger logger = Logger.getLogger(AccountService.class.getName());
    	Random rand = new Random(); 
    	int Accnum = rand.nextInt(9000000) + 1000000; 
    	long cardNo = rand.nextInt(900000000) + 100000000;
    	logger.info("creating account for " + bankName +" " +ownerName+ " "+Accnum + " "+ 0.00 +" " + cardType + " "+ cardNo);
        Account newAccount = new Account(bankName, ownerName, Accnum, 0.00, cardType, cardNo);
        return accountRepository.save(newAccount);
    }
}
