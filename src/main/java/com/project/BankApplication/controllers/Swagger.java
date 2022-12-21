package com.project.BankApplication.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.BankApplication.Utils.AccountInput;
import com.project.BankApplication.Utils.CreateAccountInput;
import com.project.BankApplication.Utils.InputValidator;
import com.project.BankApplication.Utils.TransactionInput;
import com.project.BankApplication.Utils.WithdrawInput;
import com.project.BankApplication.models.Account;
import com.project.BankApplication.repo.AccountRepository;
import com.project.BankApplication.service.AccountService;
import com.project.BankApplication.service.Action;
import com.project.BankApplication.service.BankConstants;
import com.project.BankApplication.service.TransactionService;

/**
 * REST Controller.
 * Endpoint: /getallaccounts
 * Endpoint: /getallaccountsbalance
 * Endpoint: /checkaccountbalance
 * Endpoint: /accounts
 * Endpoint: /transfer
 * Endpoint: /withdraw 
 * */
@RestController
@RequestMapping("api/v1")
public class Swagger {

    private static final Logger LOGGER = LoggerFactory.getLogger(Swagger.class);

	@Autowired
	AccountRepository accRepo;

    private final AccountService accountService;
    private final TransactionService transactionService;
	
    @Autowired
    public Swagger(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/getallaccounts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAccounts() {
        LOGGER.debug("Triggered AccoAccountInputuntRestController.accountInput");

            // Attempt to retrieve the account information
           List<Account> accounts= (List<Account>)accRepo.findAll();
            // Return the account details, or warn that no account was found for given input
            if (accounts == null) {
                return new ResponseEntity<>(BankConstants.NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(accounts, HttpStatus.OK);
            }
    }
    
    @PostMapping(value = "/getallaccountsbalance",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAccountsBalance() {
        LOGGER.debug("Triggered AccoAccountInputuntRestController.accountInput");

            // Attempt to retrieve the account information
           List<String> accounts= (List<String>)accRepo.getAccountsBalance();
            // Return the account details, or warn that no account was found for given input
            if (accounts == null) {
                return new ResponseEntity<>(BankConstants.NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(accounts, HttpStatus.OK);
            }
    }

    @PostMapping(value = "/checkaccountbalance",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAccountBalance(
            // TODO In the future support searching by card number in addition to sort code and account number
            @Valid @RequestBody AccountInput accountInput) {
        LOGGER.debug("Triggered AccountRestController.accountInput");

        // Validate input
        if (InputValidator.isSearchCriteriaValid(accountInput)) {
            // Attempt to retrieve the account information
            Account account = accountService.getAccount(accountInput.getAccountNumber());

            // Return the account details, or warn that no account was found for given input
            if (account == null) {
                return new ResponseEntity<>(BankConstants.NO_ACCOUNT_FOUND, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(BankConstants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(value = "/createAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(
            @Valid @RequestBody CreateAccountInput createAccountInput) {
        LOGGER.debug("Triggered AccountRestController.createAccountInput");

        // Validate input
        if (InputValidator.isCreateAccountCriteriaValid(createAccountInput)) {
            // Attempt to retrieve the account information
            Account account = accountService.createAccount(
                    createAccountInput.getBankName(), createAccountInput.getOwnerName(), createAccountInput.getCardType());

            // Return the account details, or warn that no account was found for given input
            if (account == null) {
                return new ResponseEntity<>(BankConstants.CREATE_ACCOUNT_FAILED, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(BankConstants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/transfer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makeTransfer(
            @Valid @RequestBody TransactionInput transactionInput) {
        if (InputValidator.isSearchTransactionValid(transactionInput)) {
//            new Thread(() -> transactionService.makeTransfer(transactionInput));
            boolean isComplete = transactionService.makeTransfer(transactionInput);
            return new ResponseEntity<>(isComplete, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(BankConstants.INVALID_TRANSACTION, HttpStatus.BAD_REQUEST);
        }
    }

	
	  @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<?> withdraw(@Valid @RequestBody WithdrawInput withdrawInput) {
	  LOGGER.debug("Triggered AccountRestController.withdrawInput");

      // Validate input
      if (InputValidator.isSearchCriteriaValid(withdrawInput)) {
          // Attempt to retrieve the account information
          Account account = accountService.getAccount(withdrawInput.getAccountNumber());

          // Return the account details, or warn that no account was found for given input
          if (account == null) {
              return new ResponseEntity<>(BankConstants.NO_ACCOUNT_FOUND, HttpStatus.OK);
          } else {
              if (transactionService.isAmountAvailable(withdrawInput.getAmount(), account.getCurrentBalance())) {
                  transactionService.updateAccountBalance(account, withdrawInput.getAmount(), Action.WITHDRAW);
                  return new ResponseEntity<>(BankConstants.SUCCESS, HttpStatus.OK);
              }
              return new ResponseEntity<>(BankConstants.INSUFFICIENT_ACCOUNT_BALANCE, HttpStatus.OK);
          }
      } else {
          return new ResponseEntity<>(BankConstants.INVALID_SEARCH_CRITERIA, HttpStatus.BAD_REQUEST);
      }
	  }
	 

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
