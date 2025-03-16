package com.dron.profitmaker2

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.dron.profitmaker2.dao.BotDao
import com.dron.profitmaker2.dao.StrategyDao
import com.dron.profitmaker2.models.Bot
import com.dron.profitmaker2.models.Strategy

@Database(entities = [Bot::class, Strategy::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun botDao(): BotDao
    abstract fun strategyDao(): StrategyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}