package com.project.BankApplication.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.BankApplication.models.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(int accountNumber);
    List<Account> findAll();
    
    @Query(nativeQuery=true,value="SELECT acc.account_Number , acc.owner_name, acc.current_Balance as CurrentBalance from bankaccountdb.account acc")
    List<String> getAccountsBalance();
}
