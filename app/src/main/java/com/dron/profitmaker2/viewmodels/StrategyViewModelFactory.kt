package com.dron.profitmaker2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dron.profitmaker2.repository.StrategyRepository

class StrategyViewModelFactory(
    private val strategyRepository: StrategyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StrategyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StrategyViewModel(strategyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}