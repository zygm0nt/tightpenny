package com.ftang.tightpenny.model;

import com.orm.SugarRecord;

import java.math.BigDecimal;

public class SpendingEntry extends SugarRecord<SpendingEntry> {

    String category;
    BigDecimal amount;
    long timestamp;

    int year;
    int month;
    int day;

    public SpendingEntry() {}

    public SpendingEntry(String category, BigDecimal amount, long timestamp, int year, int month, int day) {
        this.category = category;
        this.amount = amount;
        this.timestamp = timestamp;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
