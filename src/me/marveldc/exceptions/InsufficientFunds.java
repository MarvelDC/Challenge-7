package me.marveldc.exceptions;

public class InsufficientFunds extends Exception {
    public InsufficientFunds() {
        super("Insufficient funds for this action");
    }
}
