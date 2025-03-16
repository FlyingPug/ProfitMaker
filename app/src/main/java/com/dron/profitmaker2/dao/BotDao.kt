package com.dron.profitmaker2.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dron.profitmaker2.models.Bot

@Dao
interface BotDao {
    @Query("SELECT * FROM bots")
    suspend fun getAllBots(): List<Bot>

    @Insert
    suspend fun insertBot(bot: Bot)

    @Query("DELETE FROM bots WHERE id = :botId")
    suspend fun deleteBot(botId: String)

    @Query("SELECT COUNT(*) FROM bots WHERE strategyId = :strategyId")
    suspend fun countStrategyUsage(strategyId: String): Int
}