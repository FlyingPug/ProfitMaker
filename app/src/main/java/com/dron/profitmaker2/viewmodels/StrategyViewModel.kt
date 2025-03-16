package com.dron.profitmaker2.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dron.profitmaker2.R
import com.dron.profitmaker2.models.Strategy
import com.dron.profitmaker2.models.TimeStep
import com.dron.profitmaker2.models.StrategyType
import com.dron.profitmaker2.repository.BotRepository

import com.dron.profitmaker2.repository.StrategyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StrategyViewModel @Inject constructor(
    private val strategyRepository: StrategyRepository,
    private val botRepository: BotRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _strategies = MutableStateFlow<List<Strategy>>(emptyList())
    val strategies: StateFlow<List<Strategy>> = _strategies.asStateFlow()

    private val _strategyUsageCount = MutableStateFlow<Map<String, Int>>(emptyMap())
    val strategyUsageCount: StateFlow<Map<String, Int>> = _strategyUsageCount.asStateFlow()

    var strategyName by mutableStateOf(savedStateHandle.get<String>("strategyName") ?: "")
        private set

    var selectedTimeStep by mutableStateOf(
        savedStateHandle.get<TimeStep>("selectedTimeStep") ?: TimeStep.HOUR_1
    )
        private set

    var formula by mutableStateOf(savedStateHandle.get<String>("formula") ?: "")
        private set

    var selectedStrategy by mutableStateOf<Strategy?>(null)
        private set

    init {
        loadStrategies()
        loadStrategyUsageCount()
    }

    private fun loadStrategies() {
        viewModelScope.launch {
            _strategies.value = strategyRepository.getAllStrategies()
        }
    }

    fun createStrategy(name: String, type: StrategyType, timeStep: TimeStep, formula: String?) {
        viewModelScope.launch {
            val newStrategy = Strategy(
                id = System.currentTimeMillis().toString(),
                name = name,
                type = type,
                timeStep = timeStep,
                formula = formula
            )
            strategyRepository.createStrategy(newStrategy)
            resetState()
        }
    }

    fun updateStrategy(id: String, name: String, timeStep: TimeStep, formula: String?) {
        viewModelScope.launch {
            val updatedStrategy = Strategy(
                id = id,
                name = name,
                type = selectedStrategy?.type ?: StrategyType.MATH,
                timeStep = timeStep,
                formula = formula
            )
            strategyRepository.updateStrategy(updatedStrategy)
            loadStrategies()
            resetState()
        }
    }

    fun loadStrategyUsageCount() {
        viewModelScope.launch {
            val counts = botRepository.getAllBots()
                .groupingBy { it.strategyId }
                .eachCount()
            _strategyUsageCount.value = counts
        }
    }

    fun getStrategyById(id: String): Strategy? {
        return _strategies.value.find { it.id == id }
    }

    fun getStrategyIcon(strategyType: StrategyType): Int {
        return when (strategyType) {
            StrategyType.MATH -> R.drawable.math
            StrategyType.FISH -> R.drawable.random
        }
    }

    fun selectStrategy(strategy: Strategy) {
        selectedStrategy = strategy
        strategyName = strategy.name
        selectedTimeStep = strategy.timeStep
        formula = strategy.formula ?: ""
        savedStateHandle["strategyName"] = strategy.name
        savedStateHandle["selectedTimeStep"] = strategy.timeStep
        savedStateHandle["formula"] = strategy.formula ?: ""
    }

    fun resetState() {
        selectedStrategy = null
        strategyName = ""
        selectedTimeStep = TimeStep.HOUR_1
        formula = ""
        savedStateHandle["strategyName"] = ""
        savedStateHandle["selectedTimeStep"] = TimeStep.HOUR_1
        savedStateHandle["formula"] = ""
    }

    fun isFormulaValid(): Boolean {
        return formula.isNotBlank() && formula.length <= 100
    }
}