package com.ftang.tightpenny.model

import java.math.BigDecimal

enum class Category(val id: Int, val title: String, val limit: BigDecimal = BigDecimal.ZERO) {
    Leisure(1, "Leisure"),
    Food(2, "Food and groceries"),
    EatingOut(3, "Eating out", BigDecimal(700)),
    Fuel(4, "Fuel");

    companion object {
        @JvmStatic fun valueOf(id: Int): Category {
            return Category.values().filter { it.id == id }.first()
        }
    }
}