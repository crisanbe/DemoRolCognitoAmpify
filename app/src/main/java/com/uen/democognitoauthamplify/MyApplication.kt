package com.uen.democognitoauthamplify

import android.app.Application
import android.graphics.ColorSpace.Model
import android.net.http.NetworkException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.DataStoreConfiguration
import com.amplifyframework.datastore.DataStoreConflictHandler
import com.amplifyframework.datastore.events.NetworkStatusEvent
import com.amplifyframework.datastore.generated.model.AmplifyModelProvider
import com.amplifyframework.datastore.generated.model.Bus
import com.amplifyframework.datastore.generated.model.Company
import com.amplifyframework.datastore.generated.model.Device
import com.amplifyframework.datastore.generated.model.Use
import com.amplifyframework.datastore.syncengine.RetryHandler
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.SubscriptionToken
import com.amplifyframework.logging.AndroidLoggingPlugin
import com.amplifyframework.logging.LogLevel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate() {
        super.onCreate()
        configureAmplify()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun configureAmplify() {
        try {
            // Configuración de logging para desarrollo
            Amplify.addPlugin(AndroidLoggingPlugin(LogLevel.INFO))

            // Configuración de API y Auth
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())

            // Configuración optimizada de DataStore
            val dataStorePlugin = AWSDataStorePlugin.builder()
                .modelProvider(AmplifyModelProvider.getInstance())
                .dataStoreConfiguration(
                    DataStoreConfiguration.builder()
                        .syncInterval(15, TimeUnit.MINUTES) // Sincronización cada 15 minutos
                        .syncMaxRecords(10_000) // Máximo de registros por sincronización
                        .syncPageSize(1_000) // Tamaño de página optimizado
                        .errorHandler { error ->
                            Log.e("DataStore", "Error: ${error.message}")
                            // Implementar lógica de reintentos si es necesario
                            if (error.cause is NetworkException) {
                                // Manejar errores de red
                            }
                        }
                        .conflictHandler { conflictData, resolution ->
                            Log.d("DataStore", "Conflicto detectado: ${conflictData.local} vs ${conflictData.remote}")
                            resolution.accept(DataStoreConflictHandler.ConflictResolutionDecision.applyRemote())
                        }
                        .build()
                )
                .build()

            Amplify.addPlugin(dataStorePlugin)
            Amplify.configure(applicationContext)

            // Iniciar DataStore después de la autenticación
            setupAuthListener()

        } catch (error: AmplifyException) {
            Log.e("MyApplication", "Error inicializando Amplify", error)
        }
    }

    private fun setupAuthListener() {
        Amplify.Hub.subscribe(HubChannel.AUTH) { hubEvent ->
            when (hubEvent.name) {
                "signedIn" -> {
                    // Iniciar DataStore cuando el usuario inicia sesión
                    startDataStore()
                }
                "signedOut" -> {
                    // Limpiar DataStore cuando el usuario cierra sesión
                    clearDataStore()
                }
            }
        }
    }

    private fun startDataStore() {
        Amplify.DataStore.start(
            { Log.i("DataStore", "DataStore iniciado exitosamente") },
            { error -> Log.e("DataStore", "Error iniciando DataStore", error) }
        )
    }

    private fun clearDataStore() {
        Amplify.DataStore.clear(
            { Log.i("DataStore", "DataStore limpiado exitosamente") },
            { error -> Log.e("DataStore", "Error limpiando DataStore", error) }
        )
    }
}
