package com.company.Exceptions;

import com.company.BankSystem.Failure;
import com.company.BankSystem.Transaction;
import com.company.BankSystem.TransactionReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadingMoreFiles {
    public static void main(String[] args) {

        File dir = new File("C:\\Users\\User\\IdeaProjects\\BankAccounts\\transactions");
        List<Transaction> data = new ArrayList<>();
        int idx = 0;

        if(!dir.exists()) {
            throw new Failure( "Directory :  " + dir + " not exist ");
        }else{

            for(File file : dir.listFiles()) {
                if(file.isFile()) {
                    for (String line : TransactionReader.readFile(file.toPath())) {

                        data.add(TransactionReader.parseTransaction(line, idx));

                        idx++;
                    }
                    System.out.println(file.getName());
                }
            }
        }
    }
}
