package me.marveldc;

import me.marveldc.exceptions.InsufficientFunds;

import java.util.UUID;

public class Account {
    UUID accountNumber;
    String owner;
    double balance;

    public Account(String owner, Double balance) {
        this.accountNumber = UUID.randomUUID();
        this.owner = owner;
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public UUID getAccountNumber() {
        return this.accountNumber;
    }

    public String getOwner() {
        return this.owner;
    }

    public double withdrawAmount(double withdrawAmount) throws InsufficientFunds {
        if (this.balance < withdrawAmount) throw new InsufficientFunds();
        this.balance -= withdrawAmount;
        return this.balance;
    }

    public double depositAmount(double depositAmount) {
        this.balance += depositAmount;
        return this.balance;
    }

    @Override
    public String toString() {
        return String.format("Account number: %s\nOwner: %s\nBalance: $%,.2f", this.accountNumber.toString(), this.owner, this.balance);
    }

    @Override
    public boolean equals(Object otherAccountIdentifier) {
        if (otherAccountIdentifier instanceof UUID) return otherAccountIdentifier.equals(this.accountNumber);
        return otherAccountIdentifier.equals(this.owner);
    }

    /*
    public boolean equals(UUID otherAccountNumber) {
        return otherAccountNumber.equals(this.accountNumber);
    }

    public boolean equals(String otherAccountName) {
        return otherAccountName.equals(this.owner);
    }
     */
}
