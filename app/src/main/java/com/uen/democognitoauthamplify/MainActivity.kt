package com.uen.democognitoauthamplify

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.uen.democognitoauthamplify.component.authenticateUser
import com.uen.democognitoauthamplify.navegation.AppNavigation
import com.uen.democognitoauthamplify.network.NetworkMonitor
import com.uen.democognitoauthamplify.network.SnackbarViewModel
import com.uen.democognitoauthamplify.ui.theme.DemoCognitoAuthAmplifyTheme
import com.uen.democognitoauthamplify.util.SyncProgressViewModel
import com.uen.democognitoauthamplify.util.SyncState
import com.uen.democognitoauthamplify.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilitar diseño edge-to-edge
        enableEdgeToEdge()

        // Configurar barra de estado
        window.statusBarColor = android.graphics.Color.parseColor("#619B22")
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContent {
            val syncProgressViewModel: SyncProgressViewModel = hiltViewModel()
            val snackbarViewModel: SnackbarViewModel = viewModel()

            DemoCognitoAuthAmplifyTheme {
                AppWithGlobalSnackbar(
                    snackbarViewModel = snackbarViewModel,
                    syncProgressViewModel = syncProgressViewModel
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppWithGlobalSnackbar(
    snackbarViewModel: SnackbarViewModel,
    syncProgressViewModel: SyncProgressViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val isConnected by snackbarViewModel.isConnected.collectAsState(initial = false)
    val syncState by syncProgressViewModel.syncState.collectAsState()

    // Manejar cambios de conectividad
    LaunchedEffect(isConnected) {
        if (isConnected) {
            syncProgressViewModel.startSync()
            snackbarViewModel.showSnackbar("Conexión Online 🛜. Sincronización iniciada.")
        } else {
            snackbarViewModel.showSnackbar("Modo Offline 🌐.")
        }
    }

    // Observar estado de sincronización
    LaunchedEffect(syncState) {
        when (syncState) {
            is SyncState.Error -> {
                snackbarViewModel.showSnackbar(
                    "Error de sincronización: ${(syncState as SyncState.Error).exception.message}"
                )
            }
            is SyncState.Ready -> {
                snackbarViewModel.showSnackbar("Sincronización completada")
            }
            is SyncState.Offline -> {
                snackbarViewModel.showSnackbar("Trabajando en modo offline")
            }
            else -> Unit
        }
    }

    // Manejar mensajes de Snackbar
    LaunchedEffect(snackbarViewModel.snackbarMessage.collectAsState().value) {
        snackbarViewModel.snackbarMessage.value?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = "Cerrar",
                    duration = SnackbarDuration.Short
                )
            }
            snackbarViewModel.hideSnackbar()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        Button(
                            onClick = { data.dismiss() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text("Cerrar")
                        }
                    }
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    ) {
        Surface {
            Authenticator() { signedInState ->
                val username = signedInState.user.username

                // Iniciar sincronización después de autenticación
                LaunchedEffect(username) {
                    syncProgressViewModel.startSync()
                }

                AppNavigation(
                    username = username,
                    snackbarViewModel = snackbarViewModel,
                    syncProgressViewModel = syncProgressViewModel
                )
            }
        }
    }
}