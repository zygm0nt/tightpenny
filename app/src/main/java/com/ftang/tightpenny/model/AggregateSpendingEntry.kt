package com.ftang.tightpenny.model

import android.util.Log
import java.math.BigDecimal
import java.util.*
import kotlin.math.plus

// entries with dates ?? TODO
class AggregateSpendingEntry(val category: Category, val entries: ArrayList<BigDecimal>) {
    fun amount(): BigDecimal {
        Log.i("Model", "Entries in $category: $entries")
        if (entries.size == 0) {
            return BigDecimal.ZERO
        } else {
            return entries.reduce { acc, entry -> acc.plus(entry) }
        }
    }

    fun addEntry(entry: SpendingEntry) {
        entries.add(entry.getAmount())
    }
}
