package com.ftang.tightpenny.model

import android.util.Log
import java.math.BigDecimal
import kotlin.math.plus

// entries with dates ?? TODO
class AggregateSpendingEntry(val category: Category, val entries: List<Int>) {
    fun amount(): BigDecimal {
        Log.i("Model", "Entries in $category: $entries")
        if (entries.size == 0) {
            return BigDecimal.ZERO
        } else {
            //return entries.reduce { acc, entry -> acc.plus(entry) }
            return BigDecimal(entries.sum())
        }
    }
}
