package me.marveldc;

import me.marveldc.exceptions.AccountNotFoundException;
import me.marveldc.exceptions.DuplicateAccount;

import java.util.ArrayList;
import java.util.UUID;

public class Bank {
    ArrayList<Account> accounts = new ArrayList<>();
    String name;

    public Bank(String name) {
        this.name = name;
    }

    public UUID createNewAccount(String owner) throws DuplicateAccount {
        try {
            findAccount(owner);
        } catch (AccountNotFoundException e) {
            Account newAccount = new Account(owner, 0.0);
            accounts.add(newAccount);
            return newAccount.getAccountNumber();
        }

        throw new DuplicateAccount();
    }

    public UUID createNewAccount(String owner, double initialBalance) throws DuplicateAccount {
        try {
            findAccount(owner);
        } catch (AccountNotFoundException e) {
            Account newAccount = new Account(owner, initialBalance);
            accounts.add(newAccount);
            return newAccount.getAccountNumber();
        }

        throw new DuplicateAccount();
    }

    public <T> Account retrieveAccount(T retrieveAccountIdentifier) throws AccountNotFoundException {
        Account account = findAccount(retrieveAccountIdentifier);
        if (account == null) throw new AccountNotFoundException();
        return account;
    }

    /*
    public Account retrieveAccount(UUID retrieveAccountNumber) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.equals(retrieveAccountNumber)) return account;
        }
        throw new AccountNotFoundException();
    }

    public Account retrieveAccount(String retrieveAccountName) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.equals(retrieveAccountName)) return account;
        }
        throw new AccountNotFoundException();
    }
     */

    public <T> void removeAccount(T removeAccountIdentifier) throws AccountNotFoundException {
        Account account = findAccount(removeAccountIdentifier);
        if (account == null) throw new AccountNotFoundException();
        accounts.remove(account);
    }

    /*
    public void removeAccount(UUID removeAccountNumber) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.equals(removeAccountNumber)) {
                accounts.remove(account);
                return;
            }
        }
        throw new AccountNotFoundException();
    }

    public void removeAccount(String removeAccountName) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.equals(removeAccountName)) {
                accounts.remove(account);
                return;
            }
        }
        throw new AccountNotFoundException();
    }
     */

    public <T> Account findAccount(T accountIdentifier) throws AccountNotFoundException {
        for (Account account : accounts) if (account.equals(accountIdentifier)) return account;
        throw new AccountNotFoundException();
    }

    public boolean equals(String bankName) {
        return bankName.equals(this.name);
    }
}
