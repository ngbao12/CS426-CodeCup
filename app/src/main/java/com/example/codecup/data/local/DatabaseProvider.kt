package com.example.codecup.data.local

import android.app.Application
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(application: Application): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                "app-database"
            ).fallbackToDestructiveMigration().build()

            INSTANCE = instance
            instance
        }
    }
}
