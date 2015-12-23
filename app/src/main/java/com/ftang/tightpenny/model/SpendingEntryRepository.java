package com.ftang.tightpenny.model;

import org.apache.commons.lang.math.Range;
import org.joda.time.DateTime;
import org.roboguice.shaded.goole.common.collect.Lists;
import org.roboguice.shaded.goole.common.collect.Maps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class SpendingEntryRepository {

    public Map<Category, List<SpendingEntry>> findSpendingsForDate(int year, int month, int day) {

        Map<Category, List<SpendingEntry>> categorizedEntries = Maps.newHashMap();

        List<SpendingEntry> entries = SpendingEntry.find(SpendingEntry.class,
                "year = ? and month = ? and day = ?", "" + year, "" + month, "" + day);

        for (SpendingEntry entry : entries) {
            Category category = Category.valueOf(entry.category);
            List<SpendingEntry> catEntries = categorizedEntries.get(category);
            if (catEntries == null) {
                catEntries = Lists.newArrayList();
                categorizedEntries.put(category, catEntries);
            }
            catEntries.add(entry);
        }
        return categorizedEntries;
    }

    public List<AggregateSpendingEntry> getMonthSpendings(int year, int month) {
        List<Map<Category, List<SpendingEntry>>> monthsEntries = new ArrayList<>();
        for (int day = 1; day <= 31; day++) {
            monthsEntries.add(findSpendingsForDate(year, month, day));
        }
        List<AggregateSpendingEntry> l = new ArrayList<AggregateSpendingEntry>();
        l.add(new AggregateSpendingEntry("category", new ArrayList<BigDecimal>()));
        return l;
        //return monthsEntries.get(0); // FIXME
    }

    public void addNewSpending(Category category, BigDecimal amount) {
        DateTime now = DateTime.now();

        SpendingEntry entry = new SpendingEntry("" + category.getId(), amount, now.toDate().getTime(),
                now.getYear(), now.getMonthOfYear(), now.getDayOfMonth());
        entry.save();
    }
}
