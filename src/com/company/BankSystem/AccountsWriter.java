package com.company.BankSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class AccountsWriter {

    public static void write ( Collection<Account> commitedAccounts , String file ) throws IOException {

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {

            for (Account account : commitedAccounts) {
                String accountId = String.valueOf(account.getAccountId());
                String accountMoney = String.valueOf(account.getMoney());

                fileWriter.write(accountId);
                fileWriter.write(" ");
                fileWriter.write(accountMoney);

                fileWriter.newLine();
                fileWriter.flush();
            }
        }catch(Failure failure){
            System.err.println(failure.getError());
        }
    }
}
