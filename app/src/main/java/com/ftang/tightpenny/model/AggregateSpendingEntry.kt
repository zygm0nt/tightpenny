package com.ftang.tightpenny.model

import java.math.BigDecimal
import kotlin.math.plus

// entries with dates ?? TODO
class AggregateSpendingEntry(val category: String, val entries: List<BigDecimal>) {
    fun amount(): BigDecimal {
        if (entries.size == 0) {
            return BigDecimal.ZERO
        } else {
            return entries.reduce { acc, entry -> acc.plus(entry) }
        }
    }
}
