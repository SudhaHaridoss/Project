package com.project.BankApplication.Utils;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class CreateAccountInput {

    @NotBlank(message = "Bank name is mandatory")
    private String bankName;

    @NotBlank(message = "Owner name is mandatory")
    private String ownerName;
    
    @NotBlank(message = "card type credit/debit is mandatory")
    private String cardType;
    
	public CreateAccountInput() {}

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

    @Override
    public String toString() {
        return "CreateAccountInput{" +
                "bankName='" + bankName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAccountInput that = (CreateAccountInput) o;
        return Objects.equals(bankName, that.bankName) &&
                Objects.equals(ownerName, that.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankName, ownerName);
    }
}
