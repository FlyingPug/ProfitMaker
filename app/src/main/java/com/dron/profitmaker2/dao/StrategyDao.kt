package com.dron.profitmaker2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dron.profitmaker2.models.Strategy

@Dao
interface StrategyDao {
    @Query("SELECT * FROM strategies")
    suspend fun getAllStrategies(): List<Strategy>

    @Insert
    suspend fun insertStrategy(strategy: Strategy)

    @Update
    suspend fun updateStrategy(strategy: Strategy)

    @Query("DELETE FROM strategies WHERE id = :strategyId")
    suspend fun deleteStrategy(strategyId: String)
}