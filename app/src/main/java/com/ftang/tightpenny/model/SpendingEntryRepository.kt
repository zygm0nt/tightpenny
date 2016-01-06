package com.ftang.tightpenny.model

import android.util.Log
import org.joda.time.DateTime

import java.math.BigDecimal
import java.util.*

class SpendingEntryRepository {

    val TAG = "TightPenny-DAO"

    fun clearDb(): Unit {
        SpendingEntry.deleteAll(SpendingEntry::class.java)
    }

    fun findSpendingsForDate(year: Int, month: Int, day: Int): Map<Category, List<SpendingEntry>> {

        val entries = SpendingEntry.find(SpendingEntry::class.java,
                "year = ? and month = ? and day = ?", "" + year, "" + month, "" + day)

        Log.i(TAG, "Fetch for ($year, $month, $day) got ${entries.size}")
        return entries.groupBy { it.category }.mapKeys { Category.valueOf(it.component1().toInt()) }
    }

    fun getMonthSpendings(year: Int, month: Int): List<AggregateSpendingEntry> {
        val monthsEntries = (1..31).map { day ->
            findSpendingsForDate(year, month, day)
        }

        val results = monthsEntries.flatMap {it.toList() }.groupBy { it.first }.mapValues {
            it.component2().flatMap { it.second }
        }.toList().map { AggregateSpendingEntry(it.first, it.second.map { it.getAmount() }.toArrayList())}

        return results
    }

    fun addNewSpending(category: Category, amount: BigDecimal): SpendingEntry {
        val now = DateTime.now()

        val entry = SpendingEntry(category, amount, now.toDate().time,
                now.year, now.monthOfYear, now.dayOfMonth)
        Log.i(TAG, "Persisting $entry")
        entry.save()
        return entry
    }
}
