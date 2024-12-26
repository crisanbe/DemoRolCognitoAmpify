package com.uen.democognitoauthamplify.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uen.democognitoauthamplify.data.database.entity.SyncState

@Dao
interface SyncStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncState: SyncState)

    @Query("SELECT * FROM sync_state WHERE id = :id")
    suspend fun getSyncState(id: String): SyncState?

    @Query("SELECT * FROM sync_state WHERE isSynchronized = 1")
    suspend fun getAllSynchronizedItems(): List<SyncState>

    @Query("DELETE FROM sync_state")
    suspend fun clearAll()

    @Delete
    suspend fun delete(syncState: SyncState)

    @Query("DELETE FROM sync_state WHERE id IN (:ids)")
    suspend fun deleteAll(ids: List<String>)
}