package com.project.BankApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.project.BankApplication.Utils.AccountInput;
import com.project.BankApplication.controllers.Swagger;
import com.project.BankApplication.models.Account;
import com.project.BankApplication.repo.AccountRepository;
import com.project.BankApplication.repo.TransactionRepository;
import com.project.BankApplication.service.TransactionService;
import com.project.BankApplication.Utils.TransactionInput;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CheckBalanceAndTransfer {

    @Autowired
    private Swagger repo;

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

   @Test
   public void forGivenAccountVerifyAccountbalance() {
    	// Check create account works fine
        Account newAccount = new Account("ING", "SUDHA H", 7308463, 0.00, "DEBIT", 34434475);
        accountRepository.save(newAccount);
		assertEquals(5,accountRepository.findAll().size());
		
		//check Account information
        // given
        var input = new AccountInput();
        input.setAccountNumber(7308463);
        // when
        var body = repo.checkAccountBalance(input).getBody();

        // then
        var account = (Account) body;
        assertThat(account).isNotNull();
        assertThat(account.getOwnerName()).isEqualTo("Rose");
        assertThat(account.getAccountNumber()).isEqualTo(7308463);
        assertThat(account.getCurrentBalance()).isEqualTo(0.00);
    }
    @Test
    public void checkTransaction() {
        // given
        var sourceAccount = new AccountInput();
        sourceAccount.setAccountNumber(73084635);

        var targetAccount = new AccountInput();
         targetAccount.setAccountNumber(21956204);

        var input = new TransactionInput();
        input.setSourceAccount(sourceAccount);
        input.setTargetAccount(targetAccount);
        input.setAmount(27.5);
        input.setReference("My reference");

        // when
        var body = transactionService.makeTransfer(input);

        // then
        var isComplete = (Boolean) body;
        assertThat(isComplete).isTrue();
    }
}
