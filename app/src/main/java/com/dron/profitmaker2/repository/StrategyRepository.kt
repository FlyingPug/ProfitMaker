package com.dron.profitmaker2.repository

import com.dron.profitmaker2.models.Strategy
import com.dron.profitmaker2.models.StrategyType
import com.dron.profitmaker2.models.TimeStep

// mock repository
class StrategyRepository {
    private val strategies = mutableListOf<Strategy>()

    fun getAllStrategies(): List<Strategy> = strategies

    fun createStrategy(strategy: Strategy) {
        strategies.add(strategy)
    }

    fun getStrategyById(id: String): Strategy? {
        return strategies.find { it.id == id }
    }

    fun updateStrategy(updatedStrategy: Strategy) {
        val index = strategies.indexOfFirst { it.id == updatedStrategy.id }
        if (index != -1) {
            strategies[index] = updatedStrategy
        }
    }

    fun deleteStrategy(strategyId: String) {
        strategies.removeIf { it.id == strategyId }
    }

    fun initializeMockData() {
        strategies.addAll(
            listOf(
                Strategy(
                    id = "0",
                    name = "Random Strategy",
                    type = StrategyType.FISH,
                    timeStep = TimeStep.HOUR_1,
                    formula = null,
                ),
                Strategy(
                    id = "1",
                    name = "Math Strategy",
                    type = StrategyType.MATH,
                    timeStep = TimeStep.MIN_30,
                    formula = "Y = x * 2",
                ),
                Strategy(
                    id = "2",
                    name = "Sin Strategy",
                    type = StrategyType.MATH,
                    timeStep = TimeStep.DAY_1,
                    formula = "Y = sin(x^2) - 2",
                )
            )
        )
    }
}