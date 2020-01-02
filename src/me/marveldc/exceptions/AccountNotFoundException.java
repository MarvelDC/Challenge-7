package me.marveldc.exceptions;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException() {
        super("Unable to find account");
    }
}
