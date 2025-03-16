package com.dron.profitmaker2.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "bots")
data class Bot(
    @PrimaryKey val id: String,
    val name: String,
    val assets: List<String>,
    val strategyId: String,
    val profitUSD: Double,
    val creationDate: LocalDate = LocalDate.now(),
    val profitHistory: List<Double>
)