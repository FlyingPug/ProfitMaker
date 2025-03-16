package com.dron.profitmaker2.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "strategies")
data class Strategy(
    @PrimaryKey val id: String,
    val name: String,
    val type: StrategyType,
    val timeStep: TimeStep,
    val formula: String?
)
