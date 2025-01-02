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
import com.uen.democognitoauthamplify.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
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
    var todoList by remember { mutableStateOf(emptyList<Use>()) }
    var totalItems by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    var selectedTodo by remember { mutableStateOf<Use?>(null) }
    val pageSize = 10
    var currentPage by remember { mutableIntStateOf(1) }
    var isLoading by remember { mutableStateOf(false) }
    val isSyncComplete by syncProgressViewModel.isSyncComplete.collectAsState()
    val synchronizedItems by syncProgressViewModel.synchronizedItems.collectAsState()
    var isSaving by remember { mutableStateOf(false) }
    val context = LocalContext.current

    fun queryTodoListPaginated(page: Int) {
        if (isLoading) return
        isLoading = true

        // Consultar el dispositivo asociado al usuario
        val deviceFilter = QueryField.field("deviceOwner").eq(username)
        Amplify.DataStore.query(
            Device::class.java,
            deviceFilter,
            { devices ->
                if (devices.hasNext()) {
                    val device = devices.next()
                    val deviceID = device.id

                    // Consultar los usos asociados al dispositivo
                    val useFilter = QueryField.field("deviceID").eq(deviceID)
                    Amplify.DataStore.query(
                        Use::class.java,
                        useFilter,
                        { uses ->
                            val tempList = mutableListOf<Use>()
                            var skipCount = (page - 1) * pageSize
                            var count = 0

                            while (uses.hasNext() && count < pageSize) {
                                val item = uses.next()
                                if (skipCount > 0) {
                                    skipCount--
                                } else {
                                    tempList.add(item)
                                    count++
                                }
                            }

                            todoList = (todoList + tempList).distinctBy { it.id }
                            isLoading = false
                        },
                        { failure ->
                            Log.e("UsoScreen", "Query failed for Use.", failure)
                            isLoading = false
                        }
                    )
                } else {
                    Log.e("UsoScreen", "No device found for the provided username.")
                    isLoading = false
                }
            },
            { failure ->
                Log.e("UsoScreen", "Query failed for Device.", failure)
                isLoading = false
            }
        )
    }

    fun saveUsosWithDelay(username: String, count: Int, onComplete: () -> Unit) {
        val currentTime = Temporal.DateTime(Date(), -5 * 60)
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            repeat(count) { index ->
                Amplify.DataStore.save(
                    Use.builder()
                        .cardNumber((1000..9999).random().toString())
                        .balance((1000..2000).random().toDouble())
                        .sequenceNumber(index + 1)
                        .status(false) // Pendiente
                        .deviceOwner(username)
                        .build(),
                    {
                        Log.i("UsoScreen", "Saved item: ${it.item()}")
                    },
                    { error -> Log.e("UsoScreen", "Error saving item", error) }
                )
                kotlinx.coroutines.delay(200) // Simula un tiempo de procesamiento por elemento
            }
            onComplete()
        }
    }


    fun queryTotalItems() {
        // Consultar el Device usando el IMEI
        val deviceFilter = QueryField.field("deviceOwner").eq(username)
        Amplify.DataStore.query(Device::class.java, deviceFilter, { devices ->
            if (devices.hasNext()) {
                val device = devices.next()
                val deviceID = device.id

                // Consultar Use usando el deviceID
                val useFilter = QueryField.field("deviceID").eq(deviceID)
                Amplify.DataStore.query(Use::class.java, useFilter, { uses ->
                    var count = 0
                    while (uses.hasNext()) {
                        uses.next()
                        count++
                    }
                    totalItems = count
                    Log.i("UsoScreen", "Total items recalculated: $totalItems")
                }, { failure -> Log.e("UsoScreen", "Query failed for Use.", failure) })
            } else {
                Log.e("UsoScreen", "No device found for the provided IMEI.")
                totalItems = 0
            }
        }, { failure -> Log.e("UsoScreen", "Query failed for Device.", failure) })
    }

    LaunchedEffect(isSyncComplete) {
        if (isSyncComplete) {
            Amplify.DataStore.start(
                { Log.i("UsoScreen", "Sincronización completa asegurada.") },
                { error -> Log.e("UsoScreen", "Error sincronizando DataStore.", error) }
            )
            queryTodoListPaginated(1)
            queryTotalItems()
        }
    }


    LaunchedEffect(Unit) {
        val tag = "ObserveQuery"
        val onQuerySnapshot: Consumer<DataStoreQuerySnapshot<Use>> =
            Consumer<DataStoreQuerySnapshot<Use>> { value: DataStoreQuerySnapshot<Use> ->
                Log.d(tag, "success on snapshot")
                Log.d(tag, "number of records: " + value.items.size)
                Log.d(tag, "sync status: " + value.isSynced)
            }

        val observationStarted =
            Consumer { _: Cancelable ->
                Log.d(tag, "success on cancelable")
            }
        val onObservationError =
            Consumer { value: DataStoreException ->
                Log.d(tag, "error on snapshot $value")
            }
        val onObservationComplete = Action {
            Log.d(tag, "complete")
        }
        val predicate: QueryPredicate = QueryField.field("imei").eq(username)
        val querySortBy = QuerySortBy(Use.SEQUENCE_NUMBER.toString(), QuerySortOrder.DESCENDING)

        val options = ObserveQueryOptions(predicate, listOf(querySortBy))
        Amplify.DataStore.observeQuery(
            Use::class.java,
            options,
            observationStarted,
            onQuerySnapshot,
            onObservationError,
            onObservationComplete
        )
    }

    LaunchedEffect(isSyncComplete) {
        if (isSyncComplete) {
            Amplify.DataStore.start(
                { Log.i("UsoScreen", "Sincronización completa asegurada.") },
                { error -> Log.e("UsoScreen", "Error sincronizando DataStore.", error) }
            )
            queryTodoListPaginated(1)
            queryTotalItems()
        }
    }

    DisposableEffect(Unit) {
        val observeOperation = Amplify.DataStore.observe(
            Use::class.java,
            { Log.i("MyAmplifyApp", "Observation started") },
            { change ->
                when (change.type()) {
                    DataStoreItemChange.Type.CREATE -> {
                        val newItem = change.item() as Use
                        if (!todoList.any { it.id == newItem.id }) {
                            todoList = listOf(newItem) + todoList
                        }
                    }

                    DataStoreItemChange.Type.UPDATE -> {
                        val updatedItem = change.item() as Use
                        todoList = todoList.map { if (it.id == updatedItem.id) updatedItem else it }
                    }

                    DataStoreItemChange.Type.DELETE -> {
                        val deletedItem = change.item() as Use
                        todoList = todoList.filter { it.id != deletedItem.id }
                        /*scope.launch {
                            syncProgressViewModel.deleteSynchronizedItem(deletedItem.id)
                        }*/
                    }
                }
                queryTotalItems()
            },
            { error -> Log.e("MyAmplifyApp", "Observation failed", error) },
            { Log.i("MyAmplifyApp", "Observation completed") }
        )

        onDispose {
            observeOperation.closeIfCloseable()
            Log.i("MyAmplifyApp", "Observation stopped")
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .debounce(300) // Evitar demasiadas llamadas en intervalos cortos
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                if (lastVisibleItem != null &&
                    lastVisibleItem.index == todoList.size - 1 && // Último visible
                    todoList.size < totalItems && // Aún hay más elementos para cargar
                    !isLoading // Evitar llamadas duplicadas mientras se carga
                ) {
                    currentPage++
                    queryTodoListPaginated(currentPage)
                }
            }
    }

    /*LaunchedEffect(Unit) {
        Utils.scheduleKeepLastFiveUsos(context ,username)
    }
*/
    // Raíz de la pantalla
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.White,
            topBar = {
                AppBar(
                    username = username,
                    totalItems = "($totalItems)",
                    onCreateTodo = {
                        scope.launch(Dispatchers.IO) {
                            // Buscar el dispositivo asociado al IMEI
                            val deviceFilter = QueryField.field("imei").eq(username)
                            Amplify.DataStore.query(Device::class.java, deviceFilter,
                                { devices ->
                                    if (devices.hasNext()) {
                                        val device = devices.next()

                                        // Crear y guardar un nuevo 'Use' asociado al dispositivo
                                        val currentTime = Temporal.DateTime(Date(), -5 * 60) // Ajustar según zona horaria
                                        val newUse = Use.builder()
                                            .cardNumber("12345") // Ajustar según lógica de negocio
                                            .balance(1000.0) // Ajustar según lógica de negocio
                                            .sequenceNumber(1) // Ajustar según lógica de negocio
                                            .status(true) // Pendiente
                                            .deviceOwner(username) // El IMEI
                                            .device(device) // Dispositivo asociado
                                            .build()

                                        Amplify.DataStore.save(newUse,
                                            {
                                                Log.i("UsoScreen", "Saved Use: ${newUse.id}")
                                            },
                                            { error ->
                                                Log.e("UsoScreen", "Failed to save Use", error)
                                            }
                                        )
                                        queryTodoListPaginated(currentPage)
                                        queryTotalItems()
                                    } else {
                                        Log.e("UsoScreen", "No device found for the provided username.")
                                    }
                                }, { error ->
                                    Log.e("UsoScreen", "Failed to query Device.", error)
                                })
                        }
                    },
                    onCreateVarious = { count ->
                        scope.launch {
                            isSaving = true
                            saveUsosWithDelay(username, count) {
                                isSaving = false
                                queryTodoListPaginated(currentPage)
                                queryTotalItems()
                            }
                        }
                    },
                    onDeleteAllTodos = {
                        scope.launch(Dispatchers.IO) {
                            val userFilter = QueryField.field("usoOwner").eq(username)
                            Amplify.DataStore.query(Use::class.java, userFilter, { items ->
                                val idsToDelete = mutableListOf<String>()
                                while (items.hasNext()) {
                                    val item = items.next()
                                    idsToDelete.add(item.id)
                                    Amplify.DataStore.delete(
                                        item,
                                        { Log.i("MyAmplifyApp", "Deleted item: ${item.id}") },
                                        { Log.e("MyAmplifyApp", "Could not delete item", it) })
                                }
                                todoList = emptyList()
                                queryTotalItems()
                                /*  scope.launch {
                                      syncProgressViewModel.deleteSynchronizedItems(idsToDelete)
                                  }*/
                            }, { Log.e("MyAmplifyApp", "Query failed", it) })
                        }
                    },
                    //onSignOut = { Amplify.Auth.signOut { Log.i("Amplify", "Signed out") } },
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
                        onSelect = { selectedTodo = it })
                }
                if (isLoading || isSaving) {
                    item {
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
                }
            }
        }
        selectedTodo?.let { todo ->
            UpdateItemDialog(
                todo = todo,
                onDismiss = { selectedTodo = null },
                onUpdate = { updatedItem ->
                    Amplify.DataStore.save(
                        updatedItem,
                        { Log.i("MyAmplifyApp", "Updated item: ${updatedItem.id}") },
                        { Log.e("MyAmplifyApp", "Could not update item", it) })
                    selectedTodo = null
                },
                onDelete = {
                    scope.launch(Dispatchers.IO) {
                        Amplify.DataStore.delete(
                            it,
                            { Log.i("MyAmplifyApp", "Deleted item: ${it.item()}") },
                            { Log.e("MyAmplifyApp", "Could not delete item", it) })
                        queryTodoListPaginated(currentPage)
                        queryTotalItems()
                        //syncProgressViewModel.deleteSynchronizedItem(it.id)
                    }
                }
            )
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd),
            listState = listState,
            itemCount = todoList.size
        )
    }

    // Superposición del loader
    if (isSaving) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)), // Fondo semitransparente
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
}
