package com.company.BankSystem;

public class Transaction {
    private int firstAccountId;
    private int secondAccountId;
    private int money;

    public Transaction(int firstAccountId, int secondAccountId, int money) {
        this.firstAccountId = firstAccountId;
        this.secondAccountId = secondAccountId;
        this.money = money;
    }

    public int getFirstAccountId() {
        return firstAccountId;
    }

    public int getSecondAccountId() {
        return secondAccountId;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + firstAccountId;
        result = prime * result + money;
        result = prime * result + secondAccountId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (firstAccountId != other.firstAccountId)
            return false;
        if (money != other.money)
            return false;
        if (secondAccountId != other.secondAccountId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transaction ["
                + "firstAccountId=" + firstAccountId
                + ", secondAccountId=" + secondAccountId
                + ", money=" + money
                + "]";
    }

}