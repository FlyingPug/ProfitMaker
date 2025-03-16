package com.dron.profitmaker2.repository

import com.dron.profitmaker2.dao.BotDao
import com.dron.profitmaker2.models.Bot

class BotRepository(private val botDao: BotDao) {

    suspend  fun getAllBots(): List<Bot> = botDao.getAllBots()

    suspend fun createBot(bot: Bot) {
        botDao.insertBot(bot)
    }

    suspend fun deleteBot(botId: String) {
        botDao.deleteBot(botId)
    }

    suspend fun countStrategyUsage(strategyId: String): Int {
        return botDao.countStrategyUsage(strategyId)
    }
}

