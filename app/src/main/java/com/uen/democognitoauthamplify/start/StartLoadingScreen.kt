@file:OptIn(InternalApi::class)

package com.uen.democognitoauthamplify.start

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
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
import com.amplifyframework.core.model.query.ObserveQueryOptions
import com.amplifyframework.core.model.query.QuerySortBy
import com.amplifyframework.core.model.query.QuerySortOrder
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreItemChange
import com.amplifyframework.datastore.generated.model.Device
import com.amplifyframework.datastore.generated.model.Use
import com.uen.democognitoauthamplify.util.SyncProgressViewModel
import com.uen.democognitoauthamplify.util.SyncState

@Composable
fun StartLoadingScreen(
    username: String,
    navController: NavHostController,
    viewModel: SyncProgressViewModel = hiltViewModel()
) {
    val syncState by viewModel.syncState.collectAsState()
    val networkStatus by viewModel.networkStatus.collectAsState()
    val isSyncComplete by viewModel.isSyncComplete.collectAsState()
    var todoList by remember { mutableStateOf<List<Use>>(emptyList()) }

    // Efectos
    LaunchedEffect(Unit) {
        observeDataWithPredicate(
            username,
            { items ->
                todoList = items
                // Si hay datos, permite la navegación
                if (isSyncComplete) {
                    navController.navigate("uso")
                }
            },
            { error -> Log.e("StartScreen", "Error en observación", error) }
        )
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
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            when (syncState) {
                is SyncState.NotStarted -> {
                    LoadingIndicator("Iniciando sincronización...")
                }
                is SyncState.SyncingQueries -> {
                    LoadingIndicator("Sincronizando datos...")
                }
                is SyncState.Ready -> {
                    SyncCompletedIndicator(todoList.size)
                }
                is SyncState.Offline -> {
                    OfflineIndicator(todoList.isNotEmpty())
                }
                is SyncState.Error -> {
                    ErrorIndicator((syncState as SyncState.Error).exception.message)
                }
                else -> Unit
            }

            // Indicador de conexión
            if (!networkStatus) {
                NetworkStatusIndicator()
            }
        }
    }
}
private fun observeDataWithPredicate(
    username: String,
    onDataReceived: (List<Use>) -> Unit,
    onError: (Exception) -> Unit
) {
    // Buscar por IMEI en la tabla Device
    val deviceQuery = QueryField.field("imei").eq(username)

    Amplify.DataStore.query(
        Device::class.java,
        deviceQuery,
        { devices ->
            if (devices.hasNext()) {
                val device = devices.next()
                // Ahora buscar los Uses asociados a este device
                val useQuery = QueryField.field("deviceID").eq(device.id)
                val options = ObserveQueryOptions(
                    useQuery,
                    listOf(QuerySortBy("createdAt", QuerySortOrder.DESCENDING))
                )

                Amplify.DataStore.observeQuery(
                    Use::class.java,
                    options,
                    { Log.i("UsoScreen", "Observación iniciada") },
                    { snapshot ->
                        onDataReceived(snapshot.items)
                        Log.i("UsoScreen", "Datos actualizados, sincronizado: ${snapshot.isSynced}")
                    },
                    { error -> onError(error) },
                    { Log.i("UsoScreen", "Observación finalizada") }
                )
            } else {
                Log.e("UsoScreen", "No device found for IMEI: $username")
                onDataReceived(emptyList())
            }
        },
        { error ->
            Log.e("UsoScreen", "Error querying device", error)
            onDataReceived(emptyList())
        }
    )
}

@Composable
private fun LoadingIndicator(message: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
private fun SyncCompletedIndicator(itemCount: Int) {
    Text(
        text = "Sincronización completada ($itemCount items)",
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black
    )
}

@Composable
private fun OfflineIndicator(hasData: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Modo sin conexión activo",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        if (hasData) {
            Text(
                text = "Trabajando con datos locales",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun ErrorIndicator(errorMessage: String?) {
    Text(
        text = "Error en sincronización: $errorMessage",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Red
    )
}

@Composable
private fun NetworkStatusIndicator() {
    Text(
        text = "Sin conexión - Los cambios se sincronizarán cuando vuelva la conexión",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray,
        modifier = Modifier.padding(top = 8.dp)
    )
}