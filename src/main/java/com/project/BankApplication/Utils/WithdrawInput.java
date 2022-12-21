package com.project.BankApplication.Utils;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class WithdrawInput extends AccountInput{
	    
    	@NotNull(message = "Account number is mandatory")
		int accountNumber;
	   
	    @Positive(message = "Transfer amount must be positive")
	    private double amount;

	    public WithdrawInput() {
	        this.accountNumber = super.getAccountNumber();
	    }

	    public double getAmount() {
	        return amount;
	    }

	    public void setAmount(double amount) {
	        this.amount = amount;
	    }

	    @Override
	    public String toString() {
	        return "AccountInput{" +
	                "accountNumber='" + accountNumber + '\'' +
	                ", amount='" + amount + '\'' +
	                '}';
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        WithdrawInput that = (WithdrawInput) o;
	        return Objects.equals(accountNumber, that.accountNumber) &&
	                Objects.equals(amount, that.amount);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(accountNumber, amount);
	    }
}
