package com.ftang.tightpenny.model

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.math.minus
import kotlin.math.plus

data class SimpleSpendingEntry(val amount: BigDecimal, val date: DateTime, val uuid: String) {

    companion object {
        fun fromSpendingEntry(entry: SpendingEntry): SimpleSpendingEntry {
            return SimpleSpendingEntry(entry.getAmount(), DateTime(entry.timestamp), entry.uuid)
        }
    }
}

class AggregateSpendingEntry(val category: Category, val entries: ArrayList<SimpleSpendingEntry>) {
    fun amount(): BigDecimal {
        if (entries.size == 0) {
            return BigDecimal.ZERO
        } else {
            return entries.fold(BigDecimal.ZERO, { acc, entry -> acc.plus(entry.amount)})
        }
    }

    fun limit(): BigDecimal {
        return category.limit
    }

    fun currentDailyLimit(): BigDecimal {
        if (limit().equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO
        }
        val moneyLeft = category.limit.minus(amount())

        val now = DateTime.now()
        val sundays = getSundaysOfMonth(now.toLocalDate()).filter { it.isAfter(now.toLocalDate()) }
        val lastDayInMonth = now.dayOfMonth().withMaximumValue()

        val daysLeftInAMonth = lastDayInMonth.dayOfMonth - now.dayOfMonth - sundays.size

        return moneyLeft.divide(BigDecimal(daysLeftInAMonth), 2, RoundingMode.HALF_UP)
    }

    private fun getSundaysOfMonth(date: LocalDate): List<LocalDate> {
        val firstSunday = LocalDate(date.year, date.monthOfYear, 1).withDayOfWeek(DateTimeConstants.SUNDAY)
        val lastDayInMonth = firstSunday.dayOfMonth().withMaximumValue()
        return (1..6).map {firstSunday.plusWeeks(it - 1)}.dropLastWhile { it.isAfter(lastDayInMonth) }
    }

    fun addEntry(entry: SpendingEntry) {
        entries.add(SimpleSpendingEntry.fromSpendingEntry(entry))
    }
}
