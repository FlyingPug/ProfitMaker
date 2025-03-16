package com.dron.profitmaker2

import android.app.Application
import com.dron.profitmaker2.repository.BotRepository
import com.dron.profitmaker2.repository.StrategyRepository
import com.dron.profitmaker2.repository.AssetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun provideBotRepository(database: AppDatabase): BotRepository {
        return BotRepository(database.botDao())
    }

    @Provides
    @Singleton
    fun provideAssetRepository(): AssetRepository {
        return AssetRepository()
    }

    @Provides
    @Singleton
    fun provideStrategyRepository(database: AppDatabase): StrategyRepository {
        return StrategyRepository(database.strategyDao())
    }
}