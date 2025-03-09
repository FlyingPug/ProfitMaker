package com.dron.profitmaker2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dron.profitmaker2.models.Asset
import com.dron.profitmaker2.models.Bot
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BotViewModel constructor(
    private val botRepository: BotRepository,
    private val assetRepository: AssetRepository
) : ViewModel() {

    private val _bots = MutableStateFlow<List<Bot>>(emptyList())
    val bots: StateFlow<List<Bot>> = _bots.asStateFlow()

    private val _selectedAssets = MutableStateFlow<List<String>>(emptyList())
    val selectedAssets: StateFlow<List<String>> = _selectedAssets.asStateFlow()

    private val _assets = MutableStateFlow<List<Asset>>(emptyList())
    val assets: StateFlow<List<Asset>> = _assets.asStateFlow()

    private val _selectedStrategyId = MutableStateFlow<String?>(null)
    val selectedStrategyId: StateFlow<String?> = _selectedStrategyId.asStateFlow()

    init {
        botRepository.initializeMockData();
        loadBots()
        loadAssets()
    }

    private fun loadBots() {
        viewModelScope.launch {
            _bots.value = botRepository.getAllBots()
        }
    }

    private fun loadAssets() {
        viewModelScope.launch {
            assetRepository.getAllAssets().collect { assets ->
                _assets.value = assets
            }
        }
    }

    fun calculateProfitData(bot: Bot): Map<String, Double> {
        // mock
        val profitSinceCreation = bot.profitHistory.sum()
        val profitInLast7Days = profitSinceCreation
        val profitInLast30Days = profitSinceCreation
        val profitInLastYear = profitSinceCreation

        return mapOf(
            "sinceCreation" to profitSinceCreation,
            "last7Days" to profitInLast7Days,
            "last30Days" to profitInLast30Days,
            "lastYear" to profitInLastYear
        )
    }

    fun selectAssets(assets: List<String>) {
        _selectedAssets.value = assets
    }

    fun selectStrategy(strategyId: String) {
        _selectedStrategyId.value = strategyId
    }

    fun countStrategyUsage(strategyId: String): Int {
        return botRepository.countStrategyUsage(strategyId);
    }

    fun deleteBot(botId: String) {
        viewModelScope.launch {
            botRepository.deleteBot(botId)
            loadBots()
        }
    }

    fun createBot(name: String) {
        viewModelScope.launch {
            if (selectedAssets.value.isEmpty()) {
                throw IllegalStateException("No assets selected")
            }
            if (selectedStrategyId.value == null) {
                throw IllegalStateException("No strategy selected")
            }

            val newBot = Bot(
                id = System.currentTimeMillis().toString(),
                name = name,
                assets = selectedAssets.value,
                strategyId = selectedStrategyId.value!!,
            )
            botRepository.createBot(newBot)
            loadBots()
            resetState()
        }
    }

    fun resetState() {
        _selectedAssets.value = emptyList()
        _selectedStrategyId.value = null
    }
}