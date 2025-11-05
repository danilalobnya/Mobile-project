package com.example.mobile.feature.home

data class Car(
    val id: String,
    val title: String,        // "S 500 Sedan"
    val brand: String,        // "Mercedes-Benz"
    val pricePerDay: Int,     // 2500
    val transmission: String, // "A/Т"
    val fuel: String          // "Бензин"
)
