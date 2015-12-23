package com.ftang.tightpenny.model;

import com.orm.SugarRecord;

import java.math.BigDecimal;

public class SpendingEntry extends SugarRecord<SpendingEntry> {

    String category;
    Integer amount;
    long timestamp;

    int year;
    int month;
    int day;

    public SpendingEntry() {}

    public SpendingEntry(String category, Integer amount, long timestamp, int year, int month, int day) {
        this.category = category;
        this.amount = amount;
        this.timestamp = timestamp;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return "SpendingEntry{" +
                "category='" + category + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
