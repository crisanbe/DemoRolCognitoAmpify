package com.uen.democognitoauthamplify

import android.app.Application
import android.util.Log
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
import com.amplifyframework.datastore.generated.model.Device
import com.amplifyframework.datastore.generated.model.Route
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
    private var networkSubscription: SubscriptionToken? = null

    override fun onCreate() {
        super.onCreate()
        configureAmplify()
    }

    private fun configureAmplify() {
        try {
            //Amplify.addPlugin(AndroidLoggingPlugin(LogLevel.VERBOSE))

            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(
                AWSDataStorePlugin.builder()
                    .modelProvider(AmplifyModelProvider.getInstance())
                    .dataStoreConfiguration(
                        DataStoreConfiguration.builder()
                            .syncInterval(30, TimeUnit.SECONDS) // Ajustar según la carga
                            .syncMaxRecords(500) // Ajustar para sincronizaciones más rápidas
                            .syncPageSize(50) // Tamaño de página más pequeño
                            .observeQueryMaxTime(60) // Tiempo máximo para observar cambios
                            .conflictHandler { conflictData, onResolve ->
                                Log.d("ConflictHandler", "Conflicto detectado: ${conflictData.local} vs ${conflictData.remote}")
                                onResolve.accept(DataStoreConflictHandler.ConflictResolutionDecision.applyRemote())
                            }.errorHandler { error ->
                                Log.e("ErrorHandler", "Error en DataStore: ${error.message}", error)
                            }.build()
                    )
                    .build()
            )

            Amplify.configure(applicationContext)
            Log.i("MyApplication", "Amplify inicializado correctamente")

            authenticateUserAndSetupDataStore()

        } catch (error: AmplifyException) {
            Log.e("MyApplication", "Error inicializando Amplify", error)
        }
    }

    private fun authenticateUserAndSetupDataStore() {
        runBlocking {
            Amplify.Auth.fetchAuthSession(
                { session ->
                    if (session.isSignedIn) {
                        Amplify.Auth.getCurrentUser(
                            { user ->
                                Log.i("MyApplication", "Usuario autenticado: ${user.username}")
                                setupDataStore(user.username)
                            },
                            { error ->
                                Log.e("MyApplication", "Error obteniendo usuario actual", error)
                            }
                        )
                    } else {
                        Log.i("MyApplication", "No hay sesión de usuario activa")
                    }
                },
                { error ->
                    Log.e("MyApplication", "Error al obtener sesión de autenticación", error)
                }
            )
        }
    }

    /**
     * Configura DataStore con la configuración necesaria para el usuario autenticado
     */
    private fun setupDataStore(username: String) {
        try {
            Amplify.addPlugin(
                AWSDataStorePlugin.builder()
                    .modelProvider(AmplifyModelProvider.getInstance())
                    .dataStoreConfiguration(
                        DataStoreConfiguration.builder()
                            .syncExpression(Use::class.java) { QueryField.field("deviceOwner").eq(username) }
                            .syncExpression(Bus::class.java) { QueryField.field("busOwner").eq(username) }
                            .syncExpression(Device::class.java) { QueryField.field("imei").eq(username) }
                            .syncExpression(Route::class.java) { QueryField.field("routeOwner").eq(username) }
                            .build()
                    )
                    .build()
            )
            Log.i("MyApplication", "DataStore configurado para el usuario: $username")
        } catch (error: AmplifyException) {
            Log.e("MyApplication", "Error configurando DataStore: ${error.message}", error)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        networkSubscription?.let {
            Amplify.Hub.unsubscribe(it)
        }
    }
}
