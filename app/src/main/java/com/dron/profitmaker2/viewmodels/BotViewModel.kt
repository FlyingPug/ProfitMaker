package com.dron.profitmaker2.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dron.profitmaker2.models.Asset
import com.dron.profitmaker2.models.Bot
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BotViewModel @Inject constructor(
    private val botRepository: BotRepository,
    private val assetRepository: AssetRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _bots = MutableStateFlow<List<Bot>>(emptyList())
    val bots: StateFlow<List<Bot>> = _bots.asStateFlow()

    private val _assets = MutableStateFlow<List<Asset>>(emptyList())
    val assets: StateFlow<List<Asset>> = _assets.asStateFlow()

    private val _selectedAssets = MutableStateFlow<List<String>>(emptyList())
    val selectedAssets: StateFlow<List<String>> = _selectedAssets.asStateFlow()

    private val _selectedStrategyId = MutableStateFlow<String?>(null)
    val selectedStrategyId: StateFlow<String?> = _selectedStrategyId.asStateFlow()

    init {
        loadBots()
        loadAssets()

        savedStateHandle.get<List<String>>("selectedAssets")?.let { assets ->
            _selectedAssets.value = assets
        }
        savedStateHandle.get<String?>("selectedStrategyId")?.let { strategyId ->
            _selectedStrategyId.value = strategyId
        }
    }

    fun calculateProfitData(bot: Bot): Map<String, Double> {
        val profitSinceCreation = bot.profitHistory.sum()
        return mapOf(
            "sinceCreation" to profitSinceCreation,
            "last7Days" to profitSinceCreation,
            "last30Days" to profitSinceCreation,
            "lastYear" to profitSinceCreation
        )
    }

    fun selectAssets(assets: List<String>) {
        _selectedAssets.value = assets
        savedStateHandle["selectedAssets"] = assets
    }

    fun selectStrategy(strategyId: String?) {
        _selectedStrategyId.value = strategyId
        savedStateHandle["selectedStrategyId"] = strategyId
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
                profitUSD = 0.00,
                creationDate = LocalDate.now(),
                profitHistory = emptyList()
            )
            botRepository.createBot(newBot)
            loadBots()
            resetState()
        }
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

    private fun resetState() {
        _selectedAssets.value = emptyList()
        _selectedStrategyId.value = null
        savedStateHandle["selectedAssets"] = null
        savedStateHandle["selectedStrategyId"] = null
    }
}