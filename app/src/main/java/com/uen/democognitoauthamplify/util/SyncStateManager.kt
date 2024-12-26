package com.uen.democognitoauthamplify.util

import com.uen.democognitoauthamplify.data.database.dao.SyncStateDao
import com.uen.democognitoauthamplify.data.database.entity.SyncState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncStateManager @Inject constructor(private val syncStateDao: SyncStateDao) {

    suspend fun saveSynchronizedItem(id: String) {
        syncStateDao.insert(SyncState(id, true))
    }

    suspend fun isItemSynchronized(id: String): Boolean {
        return syncStateDao.getSyncState(id)?.isSynchronized ?: false
    }

    suspend fun getAllSynchronizedItems(): List<SyncState> {
        return syncStateDao.getAllSynchronizedItems()
    }

    suspend fun clearAllSynchronizedItems() {
        syncStateDao.clearAll()
    }

    suspend fun deleteSynchronizedItem(id: String) {
        val syncState = syncStateDao.getSyncState(id)
        if (syncState != null) {
            syncStateDao.delete(syncState)
        }
    }

    suspend fun deleteSynchronizedItems(ids: List<String>) {
        syncStateDao.deleteAll(ids)
    }
}