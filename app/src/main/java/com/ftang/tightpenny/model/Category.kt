package com.ftang.tightpenny.model

enum class Category(val id: Int, val title: String) {
    Leisure(1, "Leisure"),
    Food(2, "Food and groceries"),
    EatingOut(3, "Eating out"),
    Fuel(4, "Fuel");

    companion object {
        @JvmStatic fun valueOf(id: Int): Category {
            return Category.values().filter { it.id == id }.first()
        }
    }
}