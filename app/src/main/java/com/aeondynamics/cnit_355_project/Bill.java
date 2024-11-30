package com.aeondynamics.cnit_355_project;

public class Bill {
    private String title;
    private String description;
    private String amount;
    private String date;


    public Bill(String title, String description) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;


    }    public String getDate() {
        return date;
    }
}
