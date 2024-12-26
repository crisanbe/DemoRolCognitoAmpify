/*
package com.uen.democognitoauthamplify.util

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.core.Amplify
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.datastore.events.ModelSyncedEvent
import com.amplifyframework.datastore.events.NetworkStatusEvent
import com.amplifyframework.datastore.DataStoreChannelEventName
import com.amplifyframework.hub.SubscriptionToken


private var hubSubscriptionToken: SubscriptionToken? = null

*/
/**
 * Observa el progreso de sincronización de DataStore.
 *//*

fun observeDataStoreSyncProgress(viewModel: SyncProgressViewModel) {
    hubSubscriptionToken?.let {
        Amplify.Hub.unsubscribe(it)
        Log.w("MyAmplifyApp", "Suscripción anterior cancelada.")
    }
    hubSubscriptionToken = Amplify.Hub.subscribe(HubChannel.DATASTORE) { hubEvent ->
        Log.d("MyAmplifyApp", "Evento recibido: ${hubEvent.name}, Data: ${hubEvent.data}")

        when (hubEvent.name) {
            DataStoreChannelEventName.SYNC_QUERIES_STARTED.toString() -> {
                Log.i("MyAmplifyApp", "Sincronización inicial iniciada.")
                viewModel.updateProgress(10)
            }
            DataStoreChannelEventName.MODEL_SYNCED.toString() -> {
                val modelSyncedEvent = hubEvent.data as? ModelSyncedEvent
                modelSyncedEvent?.let {
                    Log.i("MyAmplifyApp", "Modelo sincronizado: ${it.model}")
                    Log.i("MyAmplifyApp", "Nuevos: ${it.added}, Actualizados: ${it.updated}, Eliminados: ${it.deleted}")
                }
                viewModel.updateProgress(50)
            }
            DataStoreChannelEventName.SYNC_QUERIES_READY.toString() -> {
                Log.i("MyAmplifyApp", "Sincronización de consultas completada.")
                viewModel.updateProgress(80)
            }
            DataStoreChannelEventName.READY.toString() -> {
                Log.i("MyAmplifyApp", "DataStore listo. Sincronización completa.")
                viewModel.updateProgress(100)
            }
            DataStoreChannelEventName.OUTBOX_MUTATION_ENQUEUED.toString() -> {
                Log.i("MyAmplifyApp", "Mutación encolada para sincronización.")
            }
            DataStoreChannelEventName.OUTBOX_MUTATION_PROCESSED.toString() -> {
                Log.i("MyAmplifyApp", "Mutación procesada correctamente.")
            }
            DataStoreChannelEventName.OUTBOX_MUTATION_FAILED.toString() -> {
                Log.e("MyAmplifyApp", "Error al procesar una mutación.")
            }
            DataStoreChannelEventName.SUBSCRIPTIONS_ESTABLISHED.toString() -> {
                Log.i("MyAmplifyApp", "Suscripciones establecidas correctamente.")
            }
            "SUCCEEDED" -> {
                Log.i("MyAmplifyApp", "Inicialización de categoría exitosa: ${hubEvent.data}")
            }
            "networkStatus" -> {
                val networkStatus = hubEvent.data as? NetworkStatusEvent
                val isOnline = networkStatus?.active ?: false
                if (isOnline) {
                    Log.i("MyAmplifyApp", "La aplicación está conectada a la red.")
                } else {
                    Log.w("MyAmplifyApp", "La aplicación está desconectada de la red.")
                }
            }
            else -> {
                Log.w("MyAmplifyApp", "Evento desconocido: ${hubEvent.name}")
            }
        }
    }

    Log.i("MyAmplifyApp", "Suscripción a eventos de DataStore iniciada.")
}
    */
/**
 * Cancela la suscripción al canal de eventos de DataStore.
 *//*

fun cancelDataStoreSyncSubscription() {
    hubSubscriptionToken?.let {
        Amplify.Hub.unsubscribe(it)
        Log.i("MyAmplifyApp", "Suscripción de Hub cancelada.")
        hubSubscriptionToken = null
    }
}
*/
