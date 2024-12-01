package com.aeondynamics.cnit_355_project;

import java.util.Date;

public class Bill {
    private String currentDate;
    private String title;
    private String description;
    private String amount;
    private String date;


    public Bill(String title, String description, String amount, String date) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;

    }


    public Bill(String newBill, String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;

    }
    public String getDueDate() {
        return date;
    }
}
