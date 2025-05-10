package com.dron.profitmaker2.models
import java.time.LocalDate
import java.util.UUID

data class Bot(
    val id: String  = UUID.randomUUID().toString(),
    val name: String,
    val assets: List<String>,
    val strategyId: String,
    val creationDate: LocalDate = LocalDate.now(),
    val profitUSD: Double = 0.00,
    val profitHistory: List<Double> = emptyList()
)