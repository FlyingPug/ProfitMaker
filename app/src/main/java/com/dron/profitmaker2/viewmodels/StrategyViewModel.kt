package com.dron.profitmaker2.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dron.profitmaker2.R
import com.dron.profitmaker2.models.Strategy
import com.dron.profitmaker2.models.TimeStep
import com.dron.profitmaker2.models.StrategyType

import com.dron.profitmaker2.repository.StrategyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StrategyViewModel constructor(
    private val strategyRepository: StrategyRepository
) : ViewModel() {
    private val _strategies = MutableStateFlow<List<Strategy>>(emptyList())
    val strategies: StateFlow<List<Strategy>> = _strategies.asStateFlow()

    var strategyName by mutableStateOf("")
        private set

    var selectedTimeStep by mutableStateOf(TimeStep.HOUR_1)
        private set

    var formula by mutableStateOf("")
        private set

    var selectedStrategy by mutableStateOf<Strategy?>(null)
        private set

    init {
        // mock data
        strategyRepository.initializeMockData()
        loadStrategies()
    }

    private fun loadStrategies() {
        viewModelScope.launch {
            _strategies.value = strategyRepository.getAllStrategies()
        }
    }

    fun createStrategy(
        name: String,
        type: StrategyType,
        timeStep: TimeStep,
        formula: String?
    ) {
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

    fun updateStrategy() {
        viewModelScope.launch {
            selectedStrategy?.let { strategy ->
                val updatedStrategy = strategy.copy(
                    name = strategyName,
                    timeStep = selectedTimeStep,
                    formula = if (strategy.type == StrategyType.MATH) formula else null
                )
                strategyRepository.updateStrategy(updatedStrategy)
                loadStrategies()
                resetState()
            }
        }
    }

    fun deleteStrategy(strategyId: String) {
        viewModelScope.launch {
            strategyRepository.deleteStrategy(strategyId)
            loadStrategies()
        }
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
    }

    fun getStrategyById(id: String): Strategy? {
        return strategyRepository.getStrategyById(id)
    }

    fun resetState() {
        selectedStrategy = null
        strategyName = ""
        selectedTimeStep = TimeStep.HOUR_1
        formula = ""
    }

    fun isFormulaValid(): Boolean {
        return formula.isNotBlank() && formula.length <= 100
    }
}