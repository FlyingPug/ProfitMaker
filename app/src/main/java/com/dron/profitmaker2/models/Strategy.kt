package com.dron.profitmaker2.models

import java.util.UUID

data class Strategy(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: StrategyType,
    val timeStep: TimeStep,
    val formula: String? = null,
)
