package com.dron.profitmaker2.viewmodels

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.repository.StrategyRepository

class StrategyViewModelFactory(
    private val strategyRepository: StrategyRepository,
    private val botRepository: BotRepository,
) : AbstractSavedStateViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(StrategyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StrategyViewModel(strategyRepository, botRepository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}