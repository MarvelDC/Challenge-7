package me.marveldc;

import me.marveldc.exceptions.AccountNotFoundException;
import me.marveldc.exceptions.DuplicateAccount;
import me.marveldc.exceptions.InsufficientFunds;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    static ArrayList<Bank> banks = new ArrayList<>();

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        printMenu();

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {
            case 1:
                mainRequirements();
                break;
            case 2:
                createBank();
                break;
            case 3:
                createAccount();
                break;
            case 4:
                checkAccount();
                break;
            case 5:
                depositAmount();
                break;
            case 6:
                withdrawAmount();
                break;
            case 7:
                setBalance();
                break;
            case 8:
                deleteAccount();
                break;
            default:
                start();
                break;
        }
    }

    private static void deleteAccount() {
        System.out.println("What is the bank's name?");
        Scanner scanner = new Scanner(System.in);
        String bankName = scanner.nextLine();

        Bank bank = findBank(bankName);
        if (bank == null) {
            System.out.println("There is no bank with that name.");
            start();
            return;
        }

        System.out.println("What is the account number or who is the owner?");
        String accountIdentifier = scanner.nextLine();

        System.out.println("Are you sure you want to delete this account? Choose either true or false.");
        boolean confirmation = scanner.nextBoolean();

        if (!confirmation) {
            System.out.println("You chose to not delete the account.");
            start();
            return;
        }

        try {
            bank.removeAccount(accountIdentifier);
        } catch (AccountNotFoundException e) {
            System.out.println("There was no account found.");
            start();
            return;
        }

        System.out.println("The account was removed successfully!");
        start();
    }

    private static void setBalance() {
        System.out.println("What is the bank's name?");
        Scanner scanner = new Scanner(System.in);
        String bankName = scanner.nextLine();

        Bank bank = findBank(bankName);
        if (bank == null) {
            System.out.println("There is no bank with that name.");
            start();
            return;
        }

        System.out.println("What is the account number or who is the owner?");
        String accountIdentifier = scanner.nextLine();

        Account account;
        try {
            account = bank.findAccount(accountIdentifier);
        } catch (AccountNotFoundException e) {
            System.out.println("There was no account found.");
            start();
            return;
        }

        System.out.println(String.format("What do you want to set the money to? The current balance is $%,.2f.", account.getBalance()));
        double newBalance = scanner.nextDouble();

        account.setBalance(newBalance);
        System.out.println(String.format("The account's balance is now $%,.2f!", account.getBalance()));
        start();
    }

    private static void withdrawAmount() {
        System.out.println("What is the bank's name?");
        Scanner scanner = new Scanner(System.in);
        String bankName = scanner.nextLine();

        Bank bank = findBank(bankName);
        if (bank == null) {
            System.out.println("There is no bank with that name.");
            start();
            return;
        }

        System.out.println("What is the account number or who is the owner?");
        String accountIdentifier = scanner.nextLine();

        Account account;
        try {
            account = bank.findAccount(accountIdentifier);
        } catch (AccountNotFoundException e) {
            System.out.println("There was no account found.");
            start();
            return;
        }

        System.out.println(String.format("How much money do you want to withdraw? It has to be less than the current balance, which is $%,.2f.", account.getBalance()));
        double withdrawAmount = scanner.nextDouble();

        try {
            account.withdrawAmount(withdrawAmount);
        } catch (InsufficientFunds insufficientFunds) {
            System.out.println("You tried to withdraw more money than the account has. Try again with less.");
            start();
            return;
        }

        System.out.println(String.format("Withdrew $%,.2f! The account's new balance is $%,.2f.", withdrawAmount, account.getBalance()));
        start();
    }

    private static void depositAmount() {
        System.out.println("What is the bank's name?");
        Scanner scanner = new Scanner(System.in);
        String bankName = scanner.nextLine();

        Bank bank = findBank(bankName);
        if (bank == null) {
            System.out.println("There is no bank with that name.");
            start();
            return;
        }

        System.out.println("What is the account number or who is the owner?");
        String accountIdentifier = scanner.nextLine();

        Account account;
        try {
            account = bank.findAccount(accountIdentifier);
        } catch (AccountNotFoundException e) {
            System.out.println("There was no account found.");
            start();
            return;
        }

        System.out.println("How much money do you want to deposit?");
        double depositAmount = scanner.nextDouble();

        account.depositAmount(depositAmount);
        System.out.println(String.format("Deposited $%,.2f! The account's new balance is $%,.2f.", depositAmount, account.getBalance()));
        start();
    }

    private static void checkAccount() {
        System.out.println("What is the bank's name?");
        Scanner scanner = new Scanner(System.in);
        String bankName = scanner.nextLine();

        Bank bank = findBank(bankName);
        if (bank == null) {
            System.out.println("There is no bank with that name.");
            start();
            return;
        }

        System.out.println("What is the account number or who is the owner?");
        String accountIdentifier = scanner.nextLine();

        Account account;
        try {
            account = bank.findAccount(accountIdentifier);
        } catch (AccountNotFoundException e) {
            System.out.println("There was no account found.");
            start();
            return;
        }

        System.out.println("Found an account! Details:\n" + account.toString());
        start();
    }

    private static void createAccount() {
        System.out.println("What is the bank's name?");
        Scanner scanner = new Scanner(System.in);
        String bankName = scanner.nextLine();

        Bank bank = findBank(bankName);
        if (bank == null) {
            System.out.println("There is no bank with that name.");
            start();
            return;
        }

        System.out.println("Who's the owner of this new account?");
        String accountOwner = scanner.nextLine();
        System.out.println("What will be the initial balance? Use 0.0 if not wanted.");
        double accountBalance = scanner.nextDouble();

        UUID accountUUID;
        try {
            accountUUID = bank.createNewAccount(accountOwner, accountBalance);
        } catch (DuplicateAccount duplicateAccount) {
            System.out.println("Uh oh! There is already an account with that owner. Try again with another name.");
            start();
            return;
        }

        Account account;
        try {
            account = bank.retrieveAccount(accountUUID);
        } catch (AccountNotFoundException e) {
            System.out.println("I couldn't create the account, something went wrong...");
            e.printStackTrace();
            start();
            return;
        }

        System.out.println(String.format("Created a new account with the owner being: %s. The initial balance is $%,.2f and the account number is %s.", account.getOwner(), account.getBalance(), account.getAccountNumber().toString()));
        start();
    }

    private static void createBank() {
        System.out.println("What will this bank be called?");
        Scanner scanner = new Scanner(System.in);
        String bankName = scanner.nextLine();

        if (findBank(bankName) != null) {
            System.out.println("A bank with that name already exists.");
            start();
            return;
        }

        Bank newBank = new Bank(bankName);
        banks.add(newBank);
        System.out.println("Created the bank " + bankName + " successfully!");
        start();
    }

    private static void mainRequirements() {
        Bank UK = new Bank("United Kingdom");
        Bank US = new Bank("United States");
        Bank RU = new Bank("Russia");

        try {
            UK.createNewAccount("John Smith");
        } catch (DuplicateAccount duplicateAccount) {
            duplicateAccount.printStackTrace();
        }

        try {
            Account account = UK.retrieveAccount("John Smith");
            account.depositAmount(1000000);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Account account = UK.retrieveAccount("John Smith");
            System.out.println(account.toString());
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }

        UUID accountUUID = null;
        try {
            accountUUID = US.createNewAccount("Mary Jane", 1000);
        } catch (DuplicateAccount duplicateAccount) {
            duplicateAccount.printStackTrace();
        }

        try {
            Account account = US.retrieveAccount(accountUUID);
            account.withdrawAmount(500);
            System.out.println("\n" + account.toString());
        } catch (AccountNotFoundException | InsufficientFunds e) {
            e.printStackTrace();
        }
        start();
    }

    private static void printMenu() {
        System.out.println("\nType the number to do actions.\n" +
                "1. Do main requirements\n" +
                "2. Create a new bank\n" +
                "3. Create a new account in a bank\n" +
                "4. Check an account of a bank\n" +
                "5. Deposit amount to an account\n" +
                "6. Withdraw amount to an account\n" +
                "7. Set balance of an account\n" +
                "8. Delete an account from a bank\n");
    }

    public static Bank findBank(String bankName) {
        for (Bank bank : banks) {
            if (bank.equals(bankName)) return bank;
        }
        return null;
    }
}
