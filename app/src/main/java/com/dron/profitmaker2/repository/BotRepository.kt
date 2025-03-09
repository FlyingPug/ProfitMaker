package com.dron.profitmaker2.repository

import com.dron.profitmaker2.models.Bot
import com.dron.profitmaker2.models.Strategy

// mock repository
class BotRepository {
    private val bots = mutableListOf<Bot>()

    fun getAllBots(): List<Bot> = bots

    fun createBot(bot: Bot) {
        bots.add(bot)
    }

    fun countStrategyUsage(id: String): Int {
        return bots.count() { it.strategyId == id }
    }

    fun initializeMockData() {
        bots.addAll(
            listOf(
                Bot(
                    name = "Bot 1",
                    assets = listOf("BTC", "ETH"),
                    strategyId = "0",
                    profitUSD = -150.0,
                    profitHistory = listOf(50.0, 100.0, 150.0)
                ),
                Bot(
                    name = "Bot 2",
                    assets = listOf("XRP", "ADA"),
                    strategyId = "0",
                    profitUSD = -75.0,
                    profitHistory = listOf(25.0, 50.0, 75.0)
                ),
                Bot(
                    name = "Bot 3",
                    assets = listOf("SOL", "DOT"),
                    strategyId = "1",
                    profitUSD = 200.0,
                    profitHistory = listOf(100.0, 150.0, 200.0)
                ),
                Bot(
                    name = "Bot 3",
                    assets = listOf("SOL", "DOT"),
                    strategyId = "1",
                    profitUSD = 200.0,
                    profitHistory = listOf(100.0, 150.0, 200.0)
                ),
                Bot(
                    name = "Bot 3",
                    assets = listOf("SOL", "DOT"),
                    strategyId = "1",
                    profitUSD = 200.0,
                    profitHistory = listOf(100.0, 150.0, 200.0)
                ),
                Bot(
                    name = "Bot 3",
                    assets = listOf("SOL", "DOT"),
                    strategyId = "1",
                    profitUSD = 200.0,
                    profitHistory = listOf(100.0, 150.0, 200.0)
                )
            )
        )
    }
}