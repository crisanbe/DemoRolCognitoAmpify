package com.uen.democognitoauthamplify.di

import android.content.Context
import androidx.room.Room
import com.uen.democognitoauthamplify.data.database.AppDatabase
import com.uen.democognitoauthamplify.data.database.dao.SyncStateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sync-database"
        ).build()
    }

    @Provides
    fun provideSyncStateDao(database: AppDatabase): SyncStateDao {
        return database.syncStateDao()
    }
}