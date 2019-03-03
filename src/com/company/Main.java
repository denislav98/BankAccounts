package com.company;

import com.company.Exceptions.ExceptionsMessages;
import com.company.Exceptions.ValidationException;
import com.company.BankSystem.Account;
import com.company.BankSystem.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static final Path ACCOUNTS_FILE = Paths.get("accounts.txt");
    private static final Path TRANSACTIONS_FILE = Paths.get("transactions.txt");
    private static final String NEW_ACCOUNT_FILE = "new_accounts.txt";
    private static final String READ_PATTERN = "\\s";

    public static void main(String[] args) {
        try {
            Collection<Account> accounts = readAccounts();

            List<Transaction> transactions = readTransactions();

            Map<Account,  List<Transaction> > filledBankMap = fillMap(accounts, transactions);

            validateTransactions(filledBankMap);

            commitTransactions(filledBankMap);

            write(accounts);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Map<Account,List<Transaction>> fillMap(Collection<Account> accounts, List<Transaction> transactions) {

        Map< Account , List<Transaction> > filledBankMap = new HashMap<>();

        for (Account account : accounts) {

            filledBankMap.putIfAbsent(account, transactions);
        }
        return filledBankMap;
    }

    private static void validateTransactions(Map<Account, List<Transaction> > bankMap) throws ValidationException {
        for (Map.Entry<Account, List<Transaction> > entry : bankMap.entrySet()) {

            for (int i = 0; i < bankMap.values().size(); i++) {

                int accountMoney = entry.getKey().getMoney();
                int transactionsMoney =  entry.getValue().get(i).getMoney();
                int firstAccountFromTransactions = entry.getValue().get(i).getFirstAccountId();
                int secondAccountFromTransactions = entry.getValue().get(i).getSecondAccountId();
                int accountId = entry.getKey().getAccountId();

                if(transactionsMoney < 0 || accountMoney < 0){
                    throw new ValidationException(ExceptionsMessages.WRONG_MONEY_EXCEPTION);
                }

                if(firstAccountFromTransactions != accountId && secondAccountFromTransactions != accountId) {
                    throw new ValidationException(ExceptionsMessages.INVALID_ACCOUNT_ECXEPTION);
                }
            }
        }
    }

    private static void commitTransactions(Map<Account,List<Transaction>> bankMap) {

        for (Map.Entry<Account, List<Transaction> > entry : bankMap.entrySet()) {
            for (int i = 0; i < bankMap.values().size(); i++) {  //  for za elementite na lista

                int firstAccountFromTransactions = entry.getValue().get(i).getFirstAccountId();
                int secondAccountFromTransactions = entry.getValue().get(i).getSecondAccountId();
                int accountId = entry.getKey().getAccountId();
                int accountMoney = entry.getKey().getMoney();
                int transactionsMoney =  entry.getValue().get(i).getMoney();

                if(firstAccountFromTransactions == accountId){
                    entry.getKey().setMoney(accountMoney - transactionsMoney);

                }else if(secondAccountFromTransactions == accountId){
                    entry.getKey().setMoney(accountMoney + transactionsMoney);
                }
            }
        }
    }

    private static List<Transaction> readTransactions() {

        List<Transaction> transactions = new ArrayList<>();
        try {
            List<String> transactionsLines = Files.readAllLines(TRANSACTIONS_FILE);

            for (String line : transactionsLines) {
                String[] tokens = line.split(READ_PATTERN);
                int accountToGiveMoney = Integer.parseInt(tokens[0]);
                int accountToReceiveMoney = Integer.parseInt(tokens[1]);
                int money = Integer.parseInt(tokens[2]);

                Transaction transaction = new Transaction(accountToGiveMoney, accountToReceiveMoney, money);
                transactions.add(transaction);
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
        return transactions;
    }


    private static Collection<Account> readAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            List<String> accountsLines = Files.readAllLines(ACCOUNTS_FILE);

            for (String line : accountsLines) {
                String[] lineTokens = line.split(READ_PATTERN);
                int accountId = Integer.parseInt(lineTokens[0]);
                int money = Integer.parseInt(lineTokens[1]);

                Account account = new Account(accountId, money);
                accounts.add(account);
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
        return accounts;
    }

    private static void write(Collection<Account> accounts) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(NEW_ACCOUNT_FILE))) {

            for (Account account : accounts) {
                String accountId = String.valueOf(account.getAccountId());
                String accountMoney = String.valueOf(account.getMoney());

                fileWriter.write(accountId);
                fileWriter.write(" ");
                fileWriter.write(accountMoney);

                fileWriter.newLine();
                fileWriter.flush();
            }
        }
    }
}

