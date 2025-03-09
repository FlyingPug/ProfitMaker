package com.dron.profitmaker2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository

class BotViewModelFactory(
    private val botRepository: BotRepository,
    private val assetRepository: AssetRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BotViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BotViewModel(botRepository, assetRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}