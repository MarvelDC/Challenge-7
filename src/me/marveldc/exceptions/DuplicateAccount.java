package me.marveldc.exceptions;

public class DuplicateAccount extends Exception {
    public DuplicateAccount() {
        super("An account of this name already exists in this bank");
    }
}
