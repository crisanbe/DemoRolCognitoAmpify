@file:OptIn(InternalApi::class)

package com.uen.democognitoauthamplify.util

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import aws.smithy.kotlin.runtime.InternalApi
import aws.smithy.kotlin.runtime.util.type
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreChannelEventName
import com.amplifyframework.datastore.DataStoreItemChange
import com.amplifyframework.datastore.events.ModelSyncedEvent
import com.amplifyframework.datastore.events.NetworkStatusEvent
import com.amplifyframework.datastore.events.OutboxStatusEvent
import com.amplifyframework.datastore.generated.model.Use
import com.amplifyframework.datastore.syncengine.OutboxMutationEvent
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.SubscriptionToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncProgressViewModel @Inject constructor(
    application: Application,
    private val syncStateManager: SyncStateManager
) : AndroidViewModel(application) {

    private val _syncState = MutableStateFlow<SyncState>(SyncState.NotStarted)
    val syncState = _syncState.asStateFlow()

    private val _synchronizedItems = MutableStateFlow<Set<String>>(emptySet())
    val synchronizedItems = _synchronizedItems.asStateFlow()

    private val _isSyncComplete = MutableStateFlow(false)
    val isSyncComplete = _isSyncComplete.asStateFlow()

    private val _networkStatus = MutableStateFlow(false)
    val networkStatus = _networkStatus.asStateFlow()

    private var hubSubscriptionTokens = mutableListOf<SubscriptionToken>()

    init {
        viewModelScope.launch {
            _synchronizedItems.value = syncStateManager.getAllSynchronizedItems().map { it.id }.toSet()
        }
        setupObservers()
    }

    private fun setupObservers() {
        observeDataStoreSync()
        observeNetworkStatus()
        observeOutboxEvents()
    }

    private fun observeOutboxEvents() {
        val token = Amplify.Hub.subscribe(HubChannel.DATASTORE) { hubEvent ->
            when (hubEvent.name) {
                DataStoreChannelEventName.OUTBOX_MUTATION_PROCESSED.toString() -> {
                    handleOutboxMutation(hubEvent.data)
                }
                DataStoreChannelEventName.OUTBOX_STATUS.toString() -> {
                    val outboxStatus = hubEvent.data as? OutboxStatusEvent
                    handleSyncCompletion(outboxStatus?.isEmpty == true)
                }
            }
        }
        hubSubscriptionTokens.add(token)
    }

    private fun handleOutboxMutation(data: Any?) {
        val mutationEvent = data as? OutboxMutationEvent<*>
        val uso = mutationEvent?.element?.model as? Use

        uso?.let { use ->
            viewModelScope.launch {
                when (mutationEvent.type()) {
                    DataStoreItemChange.Type.CREATE.toString(),
                    DataStoreItemChange.Type.UPDATE.toString() -> {
                        if (!use.status) {
                            updateUseStatus(use)
                        }
                        _synchronizedItems.value += use.id
                    }
                    DataStoreItemChange.Type.DELETE.toString() -> {
                        _synchronizedItems.value -= use.id
                    }
                }
            }
        }
    }

    private fun updateUseStatus(use: Use) {
        val updatedUse = use.copyOfBuilder()
            .status(true)
            .build()

        Amplify.DataStore.save(updatedUse,
            { Log.i("Sync", "Use actualizado: ${use.id}") },
            { error -> Log.e("Sync", "Error actualizando use", error) }
        )
    }

    private fun observeDataStoreSync() {
        val token = Amplify.Hub.subscribe(HubChannel.DATASTORE) { hubEvent ->
            when (hubEvent.name) {
                DataStoreChannelEventName.SYNC_QUERIES_STARTED.toString() ->
                    _syncState.value = SyncState.SyncingQueries
                DataStoreChannelEventName.MODEL_SYNCED.toString() -> {
                    val modelSyncedEvent = hubEvent.data as? ModelSyncedEvent
                    handleModelSync(modelSyncedEvent)
                }
                DataStoreChannelEventName.SYNC_QUERIES_READY.toString() ->
                    _syncState.value = SyncState.Ready
                DataStoreChannelEventName.READY.toString() -> {
                    handleSyncCompletion(true)
                    checkPendingMutations()
                }
            }
        }
        hubSubscriptionTokens.add(token)
    }

    private fun handleModelSync(event: ModelSyncedEvent?) {
        event?.let {
            Log.i("Sync", "Modelo sincronizado: ${it.model}")
            when (it.model) {
                "Use" -> {
                    // Lógica específica para sincronización de Uses
                    viewModelScope.launch {
                        queryAndUpdateSynchronizedItems()
                    }
                }

                else -> {}
            }
        }
    }

    private fun observeNetworkStatus() {
        val token = Amplify.Hub.subscribe(HubChannel.DATASTORE) { hubEvent ->
            if (hubEvent.name == DataStoreChannelEventName.NETWORK_STATUS.toString()) {
                val networkStatus = hubEvent.data as? NetworkStatusEvent
                _networkStatus.value = networkStatus?.active ?: false

                if (networkStatus?.active == true) {
                    Log.i("Sync", "Conexión recuperada - Iniciando sincronización")
                    startSync()
                } else {
                    _syncState.value = SyncState.Offline
                    Log.w("Sync", "Conexión perdida - Modo offline activo")
                }
            }
        }
        hubSubscriptionTokens.add(token)
    }

    private fun handleSyncCompletion(isComplete: Boolean) {
        _isSyncComplete.value = isComplete
        if (isComplete) {
            _syncState.value = SyncState.Ready
        }
    }

    fun startSync() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _syncState.value = SyncState.Starting
                Amplify.DataStore.start(
                    { Log.i("Sync", "Sincronización iniciada exitosamente") },
                    { error ->
                        Log.e("Sync", "Error en sincronización", error)
                        _syncState.value = SyncState.Error(error)
                    }
                )
            } catch (e: Exception) {
                _syncState.value = SyncState.Error(e)
            }
        }
    }

    private fun checkPendingMutations() {
        viewModelScope.launch(Dispatchers.IO) {
            Amplify.DataStore.start(
                { Log.i("Sync", "Verificación de mutaciones pendientes completada") },
                { error -> Log.e("Sync", "Error verificando mutaciones", error) }
            )
        }
    }

    private suspend fun queryAndUpdateSynchronizedItems() {
        // Actualizar lista de items sincronizados
        val items = syncStateManager.getAllSynchronizedItems()
        _synchronizedItems.value = items.map { it.id }.toSet()
    }

    suspend fun deleteSynchronizedItem(id: String) {
        try {
            Amplify.DataStore.query(
                Use::class.java,
                QueryField.field("id").eq(id),
                { items ->
                    if (items.hasNext()) {
                        val uso = items.next()
                        Amplify.DataStore.delete(uso,
                            { Log.i("Sync", "Item eliminado: $id") },
                            { error -> throw error }
                        )
                    }
                },
                { error -> throw error }
            )
            syncStateManager.deleteSynchronizedItem(id)
            _synchronizedItems.value -= id
        } catch (e: Exception) {
            Log.e("Sync", "Error eliminando item", e)
            _syncState.value = SyncState.Error(e)
        }
    }

    suspend fun deleteSynchronizedItems(ids: List<String>) {
        try {
            syncStateManager.deleteSynchronizedItems(ids)
            _synchronizedItems.value -= ids.toSet()
        } catch (e: Exception) {
            Log.e("Sync", "Error eliminando items", e)
            _syncState.value = SyncState.Error(e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        hubSubscriptionTokens.forEach { Amplify.Hub.unsubscribe(it) }
    }
}

sealed class SyncState {
    object NotStarted : SyncState()
    object Starting : SyncState()
    object SyncingQueries : SyncState()
    object Ready : SyncState()
    object Offline : SyncState()
    data class Error(val exception: Exception) : SyncState()
}