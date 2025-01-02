@file:OptIn(InternalApi::class)

package com.uen.democognitoauthamplify.start

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import aws.smithy.kotlin.runtime.InternalApi
import aws.smithy.kotlin.runtime.util.type
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreItemChange
import com.amplifyframework.datastore.generated.model.Device
import com.amplifyframework.datastore.generated.model.Use
import com.uen.democognitoauthamplify.AnimatedProgressBar
import com.uen.democognitoauthamplify.util.SyncProgressViewModel

@Composable
fun StartLoadingScreen(
    username: String,
    navController: NavHostController,
    viewModel: SyncProgressViewModel = hiltViewModel()
) {
    val progress by viewModel.syncProgress.collectAsState(0)
    val isSyncComplete by viewModel.isSyncComplete.collectAsState(false)
    var todoList by remember { mutableStateOf(emptyList<Use>()) }
    val pageSize = 100
    var isInitialSyncDone by remember { mutableStateOf(false) }

    // Sincronización inicial
    LaunchedEffect(Unit) {
        if (!isInitialSyncDone) {
            fetchPaginatedData(username, pageSize) { items ->
                todoList = items
                isInitialSyncDone = true
            }
        }
    }

    DisposableEffect(isInitialSyncDone) {
        if (isInitialSyncDone) {
            val observer = observeDataStoreChanges(username, pageSize) { updatedList ->
                todoList = updatedList
            }
            onDispose { observer() } // Cancelar observación cuando se complete
        } else {
            onDispose { } // No hacer nada si no está sincronizado
        }
    }

    // Navegación cuando la sincronización esté completa
    LaunchedEffect(isSyncComplete) {
        if (isSyncComplete) {
            Log.i("StartLoadingScreen", "Sincronización completa. Navegando a 'uso'.")
            navController.navigate("uso") {
                popUpTo("start") { inclusive = true }
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                progress < 100 && !isSyncComplete -> AnimatedProgressBar(viewModel)
                todoList.isEmpty() -> Text(
                    text = "Sincronizando datos, por favor espera...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                else -> Text(
                    text = "Sincronización inicial completada.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}

private fun fetchPaginatedData(username: String, pageSize: Int, onResult: (List<Use>) -> Unit) {
    // Consulta la tabla Device con el imei como filtro
    val deviceQuery = QueryField.field("deviceOwner").eq(username)
    Amplify.DataStore.query(Device::class.java, deviceQuery,
        { devices ->
            if (devices.hasNext()) {
                val device = devices.next()
                val deviceID = device.id

                // Consulta la tabla Use utilizando el deviceID obtenido
                val useQuery = QueryField.field("deviceID").eq(deviceID)
                Amplify.DataStore.query(Use::class.java, useQuery,
                    { uses ->
                        val paginatedList = uses.asSequence().take(pageSize).toList()
                        onResult(paginatedList)
                    },
                    { failure -> Log.e("StartLoadingScreen", "Query for Use failed", failure) }
                )
            } else {
                Log.e("StartLoadingScreen", "No device found for the provided IMEI.")
                onResult(emptyList()) // Devuelve una lista vacía si no hay dispositivos
            }
        },
        { failure -> Log.e("StartLoadingScreen", "Query for Device failed", failure) }
    )
}

private fun observeDataStoreChanges(
    username: String,
    pageSize: Int,
    onUpdate: (List<Use>) -> Unit
): () -> Unit {
    val cancelable = Amplify.DataStore.observe(
        Use::class.java,
        { cancelable -> Log.i("StartLoadingScreen", "Observation started: $cancelable") },
        { change ->
            handleDataChange(change, username, pageSize, onUpdate)
        },
        { error -> Log.e("StartLoadingScreen", "Observation failed", error) },
        { Log.i("StartLoadingScreen", "Observation completed") }
    )
    return { cancelable.type() } // Asegurar que el observador se cancela correctamente
}

private fun handleDataChange(
    change: DataStoreItemChange<*>,
    username: String,
    pageSize: Int,
    onUpdate: (List<Use>) -> Unit
) {
    when (change.type()) {
        DataStoreItemChange.Type.CREATE, DataStoreItemChange.Type.UPDATE -> {
            val uso = change.item() as Use
            if (uso.status == false) {
                val updatedUso = uso.copyOfBuilder().status(true).build()
                Amplify.DataStore.save(updatedUso,
                    { Log.i("StartLoadingScreen", "Item marked as synced: ${updatedUso.id}") },
                    { error -> Log.e("StartLoadingScreen", "Failed to mark item as synced", error) }
                )
            }
        }
        DataStoreItemChange.Type.DELETE -> Unit
        else -> Log.w("StartLoadingScreen", "Unhandled change type: ${change.type()}")
    }

    // Refrescar datos tras un cambio
    fetchPaginatedData(username, pageSize, onUpdate)
}
