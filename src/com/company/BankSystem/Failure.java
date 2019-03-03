package com.company.BankSystem;

public class Failure extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    public Failure(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }


}