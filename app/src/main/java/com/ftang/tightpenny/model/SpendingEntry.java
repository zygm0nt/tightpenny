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

    public SpendingEntry(Category category, BigDecimal amount, long timestamp, int year, int month, int day) {
        this.category = "" + category.getId();
        this.amount = amount.multiply(BigDecimal.valueOf(100)).intValue();
        this.timestamp = timestamp;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Category getCategory() {
        return Category.valueOf(Integer.parseInt(category));
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
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
