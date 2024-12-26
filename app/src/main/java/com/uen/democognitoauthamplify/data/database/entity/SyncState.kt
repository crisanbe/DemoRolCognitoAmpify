package com.uen.democognitoauthamplify.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_state")
data class SyncState(
    @PrimaryKey val id: String,
    val isSynchronized: Boolean
)