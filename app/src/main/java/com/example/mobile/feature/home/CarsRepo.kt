package com.example.mobile.feature.home

import kotlinx.coroutines.delay

object CarsRepository {
    private val items = listOf(
        Car("1", "S 500 Sedan", "Mercedes-Benz", 2500, "А/Т", "Бензин"),
        Car("2", "E 200",      "Mercedes-Benz", 1800, "А/Т", "Бензин"),
        Car("3", "A6",         "Audi",          2100, "А/Т", "Бензин"),
        Car("4", "5 Series",   "BMW",           2300, "А/Т", "Бензин"),
    )

    suspend fun getAll(): List<Car> {
        delay(800) // имитация сети
        return items
    }

    suspend fun searchByBrand(query: String): List<Car> {
        delay(500)
        val q = query.trim().lowercase()
        return if (q.isEmpty()) items else items.filter { it.brand.lowercase().contains(q) }
    }
}
