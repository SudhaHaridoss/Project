package com.project.BankApplication.models;

import javax.persistence.*;
import java.time.LocalDateTime;

// Bank transaction
@Entity
@Table(name = "transaction", schema = "bankaccountdb")

@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_seq", schema = "bankaccountdb", initialValue = 5)
public class Transaction {

    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    private long id;

    private long sourceAccountId;

    private long targetAccountId;

    private String targetOwnerName;

    private double amount;

    private LocalDateTime initiationDate;

    private LocalDateTime completionDate;

    private String reference;

    public Transaction() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getSourceAccountId() {
        return sourceAccountId;
    }
    public void setSourceAccountId(long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }
    public long getTargetAccountId() {
        return targetAccountId;
    }
    public void setTargetAccountId(long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }
    public String getTargetOwnerName() {
        return targetOwnerName;
    }
    public void setTargetOwnerName(String targetOwnerName) {
        this.targetOwnerName = targetOwnerName;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public LocalDateTime getInitiationDate() {
        return initiationDate;
    }
    public void setInitiationDate(LocalDateTime initiationDate) {
        this.initiationDate = initiationDate;
    }
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }
    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", targetOwnerName='" + targetOwnerName + '\'' +
                ", amount=" + amount +
                ", initiationDate=" + initiationDate +
                ", completionDate=" + completionDate +
                ", reference='" + reference + '\'' +
                '}';
    }
}
