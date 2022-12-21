package com.project.BankApplication.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * Entity class
 * Account information stored in the table account. Schema name: bankaccountdb.
 * */
@Entity
@Table(name = "account", schema = "bankaccountdb")
public class Account {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="id_seq")
    private long id;


    @NotNull(message = "Please enter account number")
    @Column(name = "accountNumber", unique = true)
    private int accountNumber;

    @Column(name ="currentBalance")
    private double currentBalance;

    @Column(name ="bankName")
    private String bankName;

    @Column(name ="ownerName")
    private String ownerName;
    
    @Column(name ="cardType")
    private String cardType;
    
    @Column(name ="cardNo")
    private long cardNo;

    private transient List<Transaction> transactions;

    protected Account() {}
    public Account(String bankName, String ownerName, int generatedAccountNumber, double currentBalance, String cardType, long cardNo) {
        this.accountNumber = generatedAccountNumber;
        this.currentBalance = currentBalance;
        this.bankName = bankName;
        this.ownerName = ownerName;
        this.cardType = cardType;
        this.cardNo = cardNo;
    }
     public Account(long id, int accountNumber, double currentBalance, String bankName, String ownerName, List<Transaction> transactions) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.bankName = bankName;
        this.ownerName = ownerName;
        this.transactions = transactions;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public double getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", currentBalance=" + currentBalance +
                ", bankName='" + bankName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public long getCardNo() {
		return cardNo;
	}
	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}
}
