package com.uen.democognitoauthamplify.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uen.democognitoauthamplify.data.database.dao.SyncStateDao
import com.uen.democognitoauthamplify.data.database.entity.SyncState

@Database(entities = [SyncState::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun syncStateDao(): SyncStateDao
}