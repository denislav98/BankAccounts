package com.company.BankSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AccountReader {

    public static Collection<Account> read( Path filepath ) {
        File dir = new File("accounts");

        List<Account> accounts = new ArrayList<>();
        int idx = 1;

        if (!dir.exists()) {
            throw new Failure("Directory : " + dir + " not exist ");
        }

        for (File file : dir.listFiles()) {
            for (String line : readFile(file.toPath())) {

                accounts.add(parseAccount(line, idx));

                idx++;
            }
        }

        return accounts;
    }

    private static Account parseAccount( String data, int idx ) {
        String[] elements = data.split( " " );

        if ( elements.length != 2 ) {
            throw new Failure( "Invalid record at accounts file at row: " + idx + ". Expected <number> <number>" );
        }

        try {
            int accountId = Integer.parseInt( elements[0] );
            int money = Integer.parseInt( elements[1] );

            return new Account( accountId, money );
        }
        catch ( NumberFormatException e ) {
            throw new Failure( "Invalid record at accounts file at row: " + idx + ". Expected <number> <number>" );
        }
    }

    private static List<String> readFile( Path file ) {
        try {
            return Files.readAllLines( file );
        }
        catch ( IOException e ) {
            throw new Failure( "Error while reading file: " + file + ". Error: " + e.getMessage() );
        }
    }

}