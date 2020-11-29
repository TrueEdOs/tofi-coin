package org.tofi.coin.node.models;

public class Balances {
    private int length;
    private String[] address;
    private long[] balance;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public long[] getBalance() {
        return balance;
    }

    public void setBalance(long[] balance) {
        this.balance = balance;
    }
}
