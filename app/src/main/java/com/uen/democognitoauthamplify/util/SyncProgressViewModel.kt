package com.uen.democognitoauthamplify.util

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreChannelEventName
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

    private val _syncProgress = MutableStateFlow(0)
    val syncProgress = _syncProgress.asStateFlow()

    private val _syncMessage = MutableStateFlow("Sincronizando datos...")
    val syncMessage = _syncMessage.asStateFlow()

    private val _synchronizedItems = MutableStateFlow<Set<String>>(emptySet())
    val synchronizedItems = _synchronizedItems.asStateFlow()

    private val _isSyncComplete = MutableStateFlow(false)
    val isSyncComplete = _isSyncComplete.asStateFlow()

    private var hubSubscriptionToken: SubscriptionToken? = null

    init {
        viewModelScope.launch {
            _synchronizedItems.value = syncStateManager.getAllSynchronizedItems().map { it.id }.toSet()
        }
        observeDataStoreSyncProgress()
        observeNetworkStatus()
        observeOutboxEvents()
    }

    private fun updateProgress(progress: Int, message: String? = null) {
        _syncProgress.value = progress
        _syncMessage.value = message ?: "Sincronizando datos..."
        if (progress == 100) {
            _isSyncComplete.value = true
        }
    }

    private fun observeOutboxEvents() {
        hubSubscriptionToken = Amplify.Hub.subscribe(HubChannel.DATASTORE) { hubEvent ->
            when (hubEvent.name) {
                DataStoreChannelEventName.OUTBOX_MUTATION_PROCESSED.toString() -> {
                    val mutationEvent = hubEvent.data as? OutboxMutationEvent<*>
                    val uso = mutationEvent?.element?.model as? Use

                    uso?.let {
                        if (mutationEvent.element.model.modelName.contains("Uso", true)) {
                            viewModelScope.launch {
                                syncStateManager.saveSynchronizedItem(uso.id)
                                _synchronizedItems.value += uso.id
                            }
                        }
                    }
                }
                DataStoreChannelEventName.OUTBOX_STATUS.toString() -> {
                    val outboxStatus = hubEvent.data as? OutboxStatusEvent
                    _isSyncComplete.value = outboxStatus?.isEmpty == true
                }
            }
        }
    }

    private fun observeDataStoreSyncProgress() {
        hubSubscriptionToken = Amplify.Hub.subscribe(HubChannel.DATASTORE) { hubEvent ->
            when (hubEvent.name) {
                DataStoreChannelEventName.SYNC_QUERIES_STARTED.toString() -> updateProgress(10, "Sincronización inicial iniciada...")
                DataStoreChannelEventName.MODEL_SYNCED.toString() -> {
                    val modelSyncedEvent = hubEvent.data as? ModelSyncedEvent
                    modelSyncedEvent?.let {
                        Log.i("SyncProgressViewModel", "Modelo sincronizado: ${it.model}")
                    }
                    updateProgress(50, "Sincronización de modelo en progreso...")
                }
                DataStoreChannelEventName.SYNC_QUERIES_READY.toString() -> updateProgress(80, "Sincronización de consultas completada.")
                DataStoreChannelEventName.READY.toString() -> {
                    updateProgress(100, "Sincronización completa.")
                    checkAndSyncPendingMutations()
                }
                else -> Log.w("SyncProgressViewModel", "Evento desconocido: ${hubEvent.name}")
            }
        }
    }

    private fun observeNetworkStatus() {
        hubSubscriptionToken = Amplify.Hub.subscribe(HubChannel.DATASTORE,
            { it.name == DataStoreChannelEventName.NETWORK_STATUS.toString() },
            {
                val networkStatus = it.data as NetworkStatusEvent
                Log.i("MyAmplifyApp", "User has a network connection? ${networkStatus.active}")
                if (networkStatus.active) {
                    Log.i("SyncProgressViewModel", "Conexión activa detectada. Revisando mutaciones pendientes.")
                    checkAndSyncPendingMutations()
                } else {
                    Log.w("SyncProgressViewModel", "Conexión perdida.")
                }
            }
        )
    }

    private fun checkAndSyncPendingMutations() {
        viewModelScope.launch(Dispatchers.IO) {
            Amplify.DataStore.observe(
                Use::class.java,
                { Log.i("SyncProgressViewModel", "Iniciando observación de mutaciones pendientes.") },
                { observeOutboxEvents() },
                { error -> Log.e("SyncProgressViewModel", "Error observando mutaciones pendientes", error) },
                { Log.i("SyncProgressViewModel", "Observación de mutaciones completada.") }
            )
        }
    }

    suspend fun deleteSynchronizedItem(id: String) {
        // Eliminar de Amplify DataStore
        Amplify.DataStore.query(Use::class.java, QueryField.field("id").eq(id),
            { items ->
                if (items.hasNext()) {
                    val uso = items.next()
                    Amplify.DataStore.delete(uso,
                        { Log.i("SyncProgressViewModel", "Deleted item from DataStore: $id") },
                        { error -> Log.e("SyncProgressViewModel", "Could not delete item from DataStore", error) }
                    )
                }
            },
            { error -> Log.e("SyncProgressViewModel", "Query failed", error) }
        )

        // Eliminar de Room
        syncStateManager.deleteSynchronizedItem(id)
        _synchronizedItems.value -= id
    }

    suspend fun deleteSynchronizedItems(ids: List<String>) {
        syncStateManager.deleteSynchronizedItems(ids)
        _synchronizedItems.value -= ids.toSet()
    }

    override fun onCleared() {
        super.onCleared()
        hubSubscriptionToken?.let { Amplify.Hub.unsubscribe(it) }
    }
}