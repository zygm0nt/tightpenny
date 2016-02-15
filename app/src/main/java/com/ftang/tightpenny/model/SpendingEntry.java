package com.ftang.tightpenny.model;

import com.orm.SugarRecord;

import java.math.BigDecimal;
import java.util.UUID;

public class SpendingEntry extends SugarRecord<SpendingEntry> {

    String uuid;
    String category;
    Integer amount;
    long timestamp;

    int year;
    int month;
    int day;

    public SpendingEntry() {}

    public SpendingEntry(Category category, BigDecimal amount, long timestamp, int year, int month, int day) {
        this.uuid = UUID.randomUUID().toString();
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

    public String getUuid() {
        return uuid;
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
                "uuid='" + uuid + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
