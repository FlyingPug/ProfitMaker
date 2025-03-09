package com.dron.profitmaker2.repository

import com.dron.profitmaker2.models.Asset
import com.dron.profitmaker2.models.AssetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// mock repository
class AssetRepository {

    // mock data (мичела не будет)
    private val mockAssets = listOf(
        Asset("AAPL", "Apple Inc.", AssetType.STOCK),
        Asset("GOOGL", "Alphabet Inc.", AssetType.STOCK),
        Asset("TSLA", "Tesla Inc.", AssetType.STOCK),
        Asset("NVDA", "Nvidia Corporation", AssetType.STOCK),
        Asset("BTC-USD", "Bitcoin", AssetType.CRYPTO)
    )

    fun getAllAssets(): Flow<List<Asset>> = flow {
        emit(mockAssets)
    }

    fun searchAssets(query: String): Flow<List<Asset>> = flow {
        val filtered = mockAssets.filter {
            it.symbol.contains(query, ignoreCase = true) ||
                    it.name.contains(query, ignoreCase = true)
        }
        emit(filtered)
    }
}