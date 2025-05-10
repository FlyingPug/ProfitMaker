package com.dron.profitmaker2.viewmodels

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dron.profitmaker2.repository.AssetRepository
import com.dron.profitmaker2.repository.BotRepository

class BotViewModelFactory(
    private val botRepository: BotRepository,
    private val assetRepository: AssetRepository
) : AbstractSavedStateViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(BotViewModel::class.java)) {
            return BotViewModel(botRepository, assetRepository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}