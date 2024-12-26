package com.uen.democognitoauthamplify.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.ObserveQueryOptions
import com.amplifyframework.core.model.query.QuerySortBy
import com.amplifyframework.core.model.query.QuerySortOrder
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreQuerySnapshot
import com.amplifyframework.datastore.generated.model.Device
import com.amplifyframework.datastore.generated.model.Use
import com.uen.democognitoauthamplify.data.database.AppDatabase
import com.uen.democognitoauthamplify.data.database.entity.SyncState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Utils {
    /*fun checkIfRegisteredInMongo(uso: Uso, onStatusChecked: (Boolean) -> Unit) {
        val isRegistered = uso.status == "registered"
        onStatusChecked(isRegistered)
    }*/

    @SuppressLint("HardwareIds", "MissingPermission", "NewApi")
    fun getDeviceIdentifier(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.imei // Para Android 8.0 y superior
            } else {
                telephonyManager.deviceId // Deprecado pero útil para versiones más antiguas
            }
        } else ({
            Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            null
        }).toString()
    }

    fun keepLastFiveUsos(context: Context, username: String) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val userFilter = QueryField.field("usoOwner").eq(username)
            Amplify.DataStore.query(
                Use::class.java,
                userFilter,
                { todos ->
                    val usosList = todos.asSequence().sortedByDescending { it.createdAt }.toList()
                    val usosToDelete = usosList.drop(5)

                    // Inicializa la base de datos de Room
                    val db = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "app-database"
                    ).build()
                    val usoDao = db.syncStateDao()

                    usosToDelete.forEach { uso ->
                        Amplify.DataStore.delete(
                            uso,
                            { Log.i("keepLastFiveUsos", "Deleted item: ${uso.id}") },
                            { error -> Log.e("keepLastFiveUsos", "Could not delete item", error) }
                        )

                        // Elimina de la base de datos de Room
                        val syncState = SyncState(
                            id = uso.id,
                            isSynchronized = false
                        )
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                usoDao.delete(syncState)
                            }
                        }
                    }
                },
                { failure -> Log.e("keepLastFiveUsos", "Query failed.", failure) }
            )
        }
    }

    fun scheduleKeepLastFiveUsos(context: Context,username: String) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                keepLastFiveUsos(context, username)
                handler.postDelayed(this, 300000) // 60000 milliseconds = 1 minute
            }
        }
        handler.post(runnable)
    }

    private fun fetchDeviceByImei(
        imei: String,
        onResult: (Device?) -> Unit
    ) {
        val deviceFilter = QueryField.field("imei").eq(imei)
        Amplify.DataStore.query(Device::class.java, deviceFilter,
            { devices ->
                if (devices.hasNext()) {
                    val device = devices.next()
                    Log.i("FetchDeviceByImei", "Device found: ${device.id}")
                    onResult(device)
                } else {
                    Log.e("FetchDeviceByImei", "No device found for IMEI: $imei")
                    onResult(null)
                }
            },
            { error ->
                Log.e("FetchDeviceByImei", "Failed to fetch device by IMEI", error)
                onResult(null)
            }
        )
    }


}