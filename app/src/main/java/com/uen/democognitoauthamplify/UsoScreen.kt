@file:OptIn(InternalApi::class, FlowPreview::class)

package com.uen.democognitoauthamplify

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import aws.smithy.kotlin.runtime.InternalApi
import aws.smithy.kotlin.runtime.io.closeIfCloseable
import com.amplifyframework.core.Action
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.Consumer
import com.amplifyframework.core.async.Cancelable
import com.amplifyframework.core.model.query.ObserveQueryOptions
import com.amplifyframework.core.model.query.QuerySortBy
import com.amplifyframework.core.model.query.QuerySortOrder
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.core.model.query.predicate.QueryPredicate
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.DataStoreException
import com.amplifyframework.datastore.DataStoreItemChange
import com.amplifyframework.datastore.DataStoreQuerySnapshot
import com.amplifyframework.datastore.generated.model.Device
import com.amplifyframework.datastore.generated.model.Use
import com.uen.democognitoauthamplify.component.AppBar
import com.uen.democognitoauthamplify.component.CustomStatusBar
import com.uen.democognitoauthamplify.component.TodoItem
import com.uen.democognitoauthamplify.component.UpdateItemDialog
import com.uen.democognitoauthamplify.component.VerticalScrollbar
import com.uen.democognitoauthamplify.util.SyncProgressViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UsoScreen(
    username: String,
    snackbarHostState: SnackbarHostState,
    syncProgressViewModel: SyncProgressViewModel,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
) {
    val scope = rememberCoroutineScope()
    var todoList by remember { mutableStateOf<List<Use>>(emptyList()) }
    var totalItems by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    var selectedTodo by remember { mutableStateOf<Use?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    val synchronizedItems by syncProgressViewModel.synchronizedItems.collectAsState()
    val syncState by syncProgressViewModel.syncState.collectAsState()

    // Función para obtener el deviceID usando el IMEI (username)
    fun queryDeviceId(onDeviceFound: (String) -> Unit) {
        val deviceQuery = QueryField.field("imei").eq(username)
        Amplify.DataStore.query(
            Device::class.java,
            deviceQuery,
            { devices ->
                if (devices.hasNext()) {
                    onDeviceFound(devices.next().id)
                } else {
                    Log.e("UsoScreen", "No device found for IMEI: $username")
                }
            },
            { error -> Log.e("UsoScreen", "Error querying device", error) }
        )
    }

    // Configurar observación de datos
    LaunchedEffect(Unit) {
        queryDeviceId { deviceId ->
            val useQuery = QueryField.field("deviceID").eq(deviceId)
            val options = ObserveQueryOptions(
                useQuery,
                listOf(QuerySortBy("createdAt", QuerySortOrder.DESCENDING))
            )

            Amplify.DataStore.observeQuery(
                Use::class.java,
                options,
                { Log.i("UsoScreen", "Query observation started") },
                { snapshot ->
                    todoList = snapshot.items
                    totalItems = snapshot.items.size
                    Log.i("UsoScreen", "Data updated, synced: ${snapshot.isSynced}")
                },
                { error -> Log.e("UsoScreen", "Query observation failed", error) },
                { Log.i("UsoScreen", "Query observation completed") }
            )
        }
    }

    LaunchedEffect(Unit) {
        val deviceQuery = QueryField.field("imei").eq(username)

        Amplify.DataStore.query(
            Device::class.java,
            deviceQuery,
            { devices ->
                if (devices.hasNext()) {
                    val device = devices.next()
                    val useQuery = QueryField.field("deviceID").eq(device.id)

                    val options = ObserveQueryOptions(
                        useQuery,
                        listOf(QuerySortBy("createdAt", QuerySortOrder.DESCENDING))
                    )

                    val cancelable = Amplify.DataStore.observeQuery(
                        Use::class.java,
                        options,
                        { Log.i("UsoScreen", "Observación iniciada") },
                        { snapshot ->
                            todoList = snapshot.items
                            totalItems = snapshot.items.size
                            isLoading = false
                        },
                        { error ->
                            Log.e("UsoScreen", "Error en observación", error)
                            isLoading = false
                        },
                        { Log.i("UsoScreen", "Observación finalizada") }
                    )


                }
            },
            { error -> Log.e("UsoScreen", "Error consultando device", error) }
        )
    }

    // UI Principal
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.White,
            topBar = {
                AppBar(
                    username = username,
                    totalItems = "($totalItems)",
                    onCreateTodo = { createNewUso(username, scope) },
                    onCreateVarious = { count ->
                        scope.launch {
                            isSaving = true
                            createMultipleUsos(username, count) {
                                isSaving = false
                            }
                        }
                    },
                    onDeleteAllTodos = {
                        scope.launch {
                            deleteAllUsos(username)
                        }
                    },
                    drawerState = drawerState
                )
            },
            bottomBar = {
                CustomStatusBar(username = username)
            }
        ) { paddingValues ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                itemsIndexed(todoList) { index, task ->
                    TodoItem(
                        todo = task,
                        index = index,
                        synchronizedItems = synchronizedItems,
                        onSelect = { selectedTodo = it }
                    )
                }
                if (isLoading || isSaving) {
                    item { LoadingIndicator() }
                }
            }

            // ScrollBar
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd),
                listState = listState,
                itemCount = todoList.size
            )
        }

        // Dialog de actualización
        selectedTodo?.let { todo ->
            UpdateItemDialog(
                todo = todo,
                onDismiss = { selectedTodo = null },
                onUpdate = { updatedItem -> updateUso(updatedItem) },
                onDelete = { deleteUso(it, scope) }
            )
        }

        // Overlay de guardado
        if (isSaving) {
            SavingOverlay()
        }
    }
}

private fun createNewUso(username: String, scope: CoroutineScope) {
    scope.launch(Dispatchers.IO) {
        val deviceQuery = QueryField.field("imei").eq(username)

        Amplify.DataStore.query(
            Device::class.java,
            deviceQuery,
            { devices ->
                if (devices.hasNext()) {
                    val device = devices.next()
                    val newUse = Use.builder()
                        .device(device)  // Esto internamente manejará el deviceID
                        .cardNumber("1234")
                        .balance(1000.0) // Usa Double como está definido en el modelo
                        .sequenceNumber(1)
                        .status(false)
                        .build()

                    Amplify.DataStore.save(
                        newUse,
                        { saved ->
                            Log.i("UsoScreen", "Use guardado con ID: ${saved.item()}")
                            Log.i("UsoScreen", "Device asociado: ${saved.item().id}")
                        },
                        { error -> Log.e("UsoScreen", "Error guardando use", error) }
                    )
                } else {
                    Log.e("UsoScreen", "No se encontró dispositivo para IMEI: $username")
                }
            },
            { error -> Log.e("UsoScreen", "Error consultando dispositivo", error) }
        )
    }
}

private suspend fun createMultipleUsos(
    username: String,
    count: Int,
    onComplete: () -> Unit
) {
    withContext(Dispatchers.IO) {
        val deviceQuery = QueryField.field("imei").eq(username)

        Amplify.DataStore.query(
            Device::class.java,
            deviceQuery,
            { devices ->
                if (devices.hasNext()) {
                    val device = devices.next()
                    repeat(count) { index ->
                        val use = Use.builder()
                            .device(device)
                            .balance((1000..2000).random().toDouble())
                            .sequenceNumber(index + 1)
                            .status(false)
                            .cardNumber((1000..9999).random().toString())
                            .build()

                        Amplify.DataStore.save(
                            use,
                            { Log.i("UsoScreen", "Use created: ${use.id}") },
                            { error -> Log.e("UsoScreen", "Error creating use", error) }
                        )
                    }
                }
                onComplete()
            },
            { error ->
                Log.e("UsoScreen", "Error querying device", error)
                onComplete()
            }
        )
    }
}

private fun deleteAllUsos(deviceId: String) {
    val useQuery = QueryField.field("deviceID").eq(deviceId)
    Amplify.DataStore.query(
        Use::class.java,
        useQuery,
        { uses ->
            while (uses.hasNext()) {
                val use = uses.next()
                Amplify.DataStore.delete(
                    use,
                    { Log.i("UsoScreen", "Deleted use: ${use.id}") },
                    { error -> Log.e("UsoScreen", "Error deleting use", error) }
                )
            }
        },
        { error -> Log.e("UsoScreen", "Error querying uses", error) }
    )
}

private fun updateUso(uso: Use) {
    Amplify.DataStore.save(
        uso,
        { Log.i("UsoScreen", "Updated use: ${uso.id}") },
        { error -> Log.e("UsoScreen", "Error updating use", error) }
    )
}

private fun deleteUso(uso: Use, scope: CoroutineScope) {
    scope.launch(Dispatchers.IO) {
        Amplify.DataStore.delete(
            uso,
            { Log.i("UsoScreen", "Deleted use: ${uso.id}") },
            { error -> Log.e("UsoScreen", "Error deleting use", error) }
        )
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFF619B22),
            strokeWidth = 2.dp
        )
    }
}

@Composable
private fun SavingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = Color.Yellow,
                strokeWidth = 4.dp
            )
            Text(
                text = "Guardando datos...",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}