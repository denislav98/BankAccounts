package com.company.BankSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class TransactionReader {

    public static List<Transaction> read(Path filePath) {

        File dir = new File("transactions");

        List<Transaction> transactions = new ArrayList<>();
        int idx = 1;

        if (!dir.exists()) {
            throw new Failure("Directory : " + dir + " not exist ");
        }

        for (File file : dir.listFiles()) {
                for (String line : readFile(file.toPath())) {

                    transactions.add(parseTransaction(line, idx));

                    idx++;
                }
            }

        return transactions;
    }

    public static Transaction parseTransaction(String data, int idx) {
        String[] elements = data.split(" ");

        if (elements.length != 3) {
            throw new Failure("Invalid record at transaction file at row: " + idx + ". Expected <number> <number> <number>");
        }

        try {
            int firstAccount = Integer.parseInt(elements[0]);
            int secondAccount = Integer.parseInt(elements[1]);
            int money = Integer.parseInt(elements[2]);

            return new Transaction(firstAccount, secondAccount, money);
        } catch (NumberFormatException e) {
            throw new Failure("Invalid record at transaction file at row: " + idx + ". Expected <number> <number> <number>");
        }
    }

    public static List<String> readFile(Path file) {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new Failure("Error while reading file: " + file + ". Error: " + e.getMessage());
        }
    }
}
