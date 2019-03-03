package com.company.BankSystem;

public class Account
{
    private int accountId;
    private int money;

    public Account(int accountId, int money) {
        this.accountId = accountId;
        this.money = money;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId;
    }

    @Override
    public int hashCode() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", money=" + money + "]";
    }
}