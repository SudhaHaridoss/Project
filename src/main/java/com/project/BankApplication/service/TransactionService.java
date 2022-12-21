package com.project.BankApplication.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.BankApplication.Utils.TransactionInput;
import com.project.BankApplication.models.Account;
import com.project.BankApplication.models.Transaction;
import com.project.BankApplication.repo.AccountRepository;
import com.project.BankApplication.repo.TransactionRepository;
/**
 * Transaction service handles all the transaction related requests.
 * Updates in the Account information.*/
@Service
public class TransactionService {

    org.slf4j.Logger logger = LoggerFactory.getLogger(TransactionService.class);
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public boolean makeTransfer(TransactionInput transactionInput) {

        int sourceAccountNumber = transactionInput.getSourceAccount().getAccountNumber();
        Optional<Account> sourceAccount = accountRepository
                .findByAccountNumber(sourceAccountNumber);

        int targetAccountNumber = transactionInput.getTargetAccount().getAccountNumber();
        Optional<Account> targetAccount = accountRepository
                .findByAccountNumber(targetAccountNumber);
        
       if (sourceAccount.isPresent() && targetAccount.isPresent()) {
            if (isAmountAvailable(transactionInput.getAmount(), sourceAccount.get().getCurrentBalance())) {
                var transaction = new Transaction();

                transaction.setAmount(transactionInput.getAmount());
                transaction.setSourceAccountId(sourceAccount.get().getId());
                transaction.setTargetAccountId(targetAccount.get().getId());
                transaction.setTargetOwnerName(targetAccount.get().getOwnerName());
                transaction.setInitiationDate(LocalDateTime.now());
                transaction.setCompletionDate(LocalDateTime.now());
                transaction.setReference(transactionInput.getReference());

                updateAccountBalance(sourceAccount.get(), transactionInput.getAmount(), Action.TRANSFER);
                
                // update target account balance
                updateAccountBalance (targetAccount.get(),transactionInput.getAmount(),Action.DEPOSIT);
                transactionRepository.save(transaction);

                return true;
            }
        }
        return false;
    }

     public void updateAccountBalance(Account account, double amount, Action action) {
        if (action == Action.TRANSFER) {
        	logger.info("Transfer");
        	if (account.getCardType().toUpperCase().equals(CardType.DEBIT.name())) {
            	logger.info("debit transaction");
        		account.setCurrentBalance((account.getCurrentBalance() - amount));
        	} else {
            	logger.info("credit transaction");
        		double sumOfAmountandCreditCardCharge = amount + (amount / 100);
        		account.setCurrentBalance((account.getCurrentBalance() - sumOfAmountandCreditCardCharge));
        	}
        } else if (action == Action.DEPOSIT) {
            account.setCurrentBalance((account.getCurrentBalance() + amount));
        } else if (action == Action.WITHDRAW) {
            account.setCurrentBalance((account.getCurrentBalance() - amount));
        }
        accountRepository.save(account);
    }

    // Doesn't allow negative balance
    public boolean isAmountAvailable(double amount, double accountBalance) {
        return (accountBalance - amount) > 0;
    }
}
