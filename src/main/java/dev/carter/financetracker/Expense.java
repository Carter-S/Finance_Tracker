package dev.carter.financetracker;

import java.sql.Date;

public class Expense {
    private int expenseId;
    private double expenseAmount;
    private String category;
    private Date date;

    public Expense(int expenseId, double expenseAmount, String category, Date date) {
        this.expenseId = expenseId;
        this.expenseAmount = expenseAmount;
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

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }
}
