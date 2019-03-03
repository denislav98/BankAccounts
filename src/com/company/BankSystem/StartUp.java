package com.company.BankSystem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StartUp {

    private static final Path ACCOUNTS_FILE = Paths.get("accounts.txt");
    private static final Path TRANSACTIONS_FILE = Paths.get("transactions.txt");
    private static final String NEW_ACCOUNT_FILE = "new_accounts.txt";

    public static void main(String[] args) throws IOException {
        try {

            Collection<Account> accounts = AccountReader.read(ACCOUNTS_FILE);

            if (accounts.isEmpty()) {
                System.out.println("Empty accounts file");
                return;
            }

            validateAccounts( accounts );

            List<Transaction> transactions = TransactionReader.read(TRANSACTIONS_FILE);

            if (transactions.isEmpty()) {
                System.out.println("Empty transactions file");
                return;
            }

            validateTransactions(transactions, accounts);

            commitTransactions( transactions, accounts );

            for (Account commitedAccount : accounts) {
                System.out.println(commitedAccount);
            }

            AccountsWriter.write( accounts, NEW_ACCOUNT_FILE);

        } catch (Failure e) {
            System.err.println(e.getError());
        }
    }

    private static void commitTransactions( List<Transaction> transactions, Collection<Account> accounts ) {

        Map<Integer, Account> accountsById = new HashMap<>();

        for ( Account a : accounts ) {
            accountsById.put( a.getAccountId(), a );
        }

        for ( Transaction t : transactions ) {

            Account first = accountsById.get( t.getFirstAccountId() );
            Account second = accountsById.get( t.getSecondAccountId() );

            int newBalance = first.getMoney() - t.getMoney();

            if ( newBalance < 0 ) {
                throw new Failure( "Invalid transaction sequence. Account: " + first.getAccountId() + " left with negative balance" );
            }

            first.setMoney( newBalance );
            second.setMoney( second.getMoney() + t.getMoney() );
        }
    }

    private static void validateAccounts(Collection<Account> data) {
        int counter = 0;

        for (Account a : data) {

            counter++;

            if (a.getAccountId() < 0) {
                throw new Failure(" Invalid Account: " + a.getAccountId() + ". Account id must be positive number");
            }

            if (a.getMoney() < 0) {
                throw new Failure(" Invalid Account: " + a.getAccountId() + ". Account balance must be positive number");
            }

        }

        Set<Account> accounts = new HashSet<>();

        for (Account a : data) {

            if (!accounts.add(a)) {
                throw new Failure("Invalid Account: " + a.getAccountId() + ". Duplicate account id");
            }
        }
    }

    private static void validateTransactions(List<Transaction> transactions, Collection<Account> accounts) {

        Set<Integer> accountIds = new HashSet<>();
        for (Account a : accounts) {
            accountIds.add(a.getAccountId());
        }

        int tran = 0;
        for (Transaction t : transactions) {

            tran++;
            if (t.getFirstAccountId() < 0) {
                throw new Failure("Invalid Transaction:" +" at row " + tran + "  First account id must be positive number");
            }

            if (!accountIds.contains(t.getFirstAccountId())) {
                throw new Failure("Invalid Transaction: " + " at row  " + tran + "  First account id is not a valid account number");
            }

            if (t.getSecondAccountId() < 0) {
                throw new Failure( "Invalid Transaction: " + " at row  " + tran + " Second account id must be positive number");
            }

            if (!accountIds.contains(t.getSecondAccountId())) {
                throw new Failure("Invalid Transaction: " + " at row  " + tran + "  Second account id is not a valid account number");
            }

            if (t.getMoney() < 0) {
                throw new Failure("Invalid Transaction: " + " at row  " + tran + "  Second account id must be positive number");
            }
        }
    }
}