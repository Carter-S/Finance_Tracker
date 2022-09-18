package dev.carter.financetracker;

import java.sql.Date;

public class Transaction {
    private int transactionId;
    private double transactionAmount;
    private String category;
    private Date date;

    public Transaction(int transactionId, double transactionAmount, String category, Date date) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.category = category;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
