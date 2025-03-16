package com.dron.profitmaker2.repository

import com.dron.profitmaker2.dao.StrategyDao
import com.dron.profitmaker2.models.Strategy

class StrategyRepository(
    private val strategyDao: StrategyDao
) {

    suspend fun getAllStrategies(): List<Strategy> = strategyDao.getAllStrategies()

    suspend fun createStrategy(strategy: Strategy) {
        strategyDao.insertStrategy(strategy)
    }

    suspend fun updateStrategy(updatedStrategy: Strategy) {
        strategyDao.updateStrategy(updatedStrategy)
    }

    suspend fun deleteStrategy(strategyId: String) {
        strategyDao.deleteStrategy(strategyId)
    }
}