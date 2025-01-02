package com.uen.democognitoauthamplify

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.uen.democognitoauthamplify.component.authenticateUser
import com.uen.democognitoauthamplify.navegation.AppNavigation
import com.uen.democognitoauthamplify.network.NetworkMonitor
import com.uen.democognitoauthamplify.network.SnackbarViewModel
import com.uen.democognitoauthamplify.ui.theme.DemoCognitoAuthAmplifyTheme
import com.uen.democognitoauthamplify.util.SyncProgressViewModel
import com.uen.democognitoauthamplify.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var monitor: NetworkMonitor
    private var isAuthenticated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*authenticateUser(this) { success ->
            isAuthenticated = success
            if (isAuthenticated) {
                runOnUiThread {*/

        // Habilitar diseño edge-to-edge
        enableEdgeToEdge()

        window.statusBarColor = android.graphics.Color.parseColor("#619B22") // Verde
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        monitor.startMonitoring()
        setContent {
            val syncProgressViewModel: SyncProgressViewModel = hiltViewModel() // Inyectar ViewModel con Hilt
            val snackbarViewModel: SnackbarViewModel = viewModel() // Instancia global del ViewModel
            DemoCognitoAuthAmplifyTheme {
                AppWithGlobalSnackbar(
                    snackbarViewModel = snackbarViewModel,
                    syncProgressViewModel = syncProgressViewModel
                )
            }
        }
    }
    // }
    //   }
    //}
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppWithGlobalSnackbar(
    snackbarViewModel: SnackbarViewModel,
    syncProgressViewModel: SyncProgressViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Observa los mensajes de Snackbar
    LaunchedEffect(snackbarViewModel.snackbarMessage.collectAsState().value) {
        snackbarViewModel.snackbarMessage.value?.let { message ->
            scope.launch {
                snackbarHostState.showSnackbar(message)
            }
            snackbarViewModel.hideSnackbar()
        }
    }

    val progress by syncProgressViewModel.syncProgress.collectAsState(initial = 0)
    // Observa los cambios de conectividad con LiveData
    val isConnected by snackbarViewModel.isConnected.collectAsState(initial = true)

    LaunchedEffect(isConnected) {
        val message = if (isConnected) "Conexión Online 🛜." else "Modo Offline 🌐."
        snackbarViewModel.showSnackbar(message)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    action = {
                        Button(onClick = { data.dismiss() }) {
                            Text("Cerrar")
                        }
                    }
                ) { Text(data.visuals.message) }
            }
        }
    ) {
        Surface {
            Authenticator(
            ) { signedInState ->
                AppNavigation(signedInState.user.username, snackbarViewModel, syncProgressViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthenticator() {
    DemoCognitoAuthAmplifyTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF619B22)) // Color verde para el header
                .padding(vertical = 16.dp), // Espaciado interno
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_civica), // Reemplaza con tu recurso de logo
                contentDescription = "Logotipo Cívica",
                modifier = Modifier.height(50.dp), // Tamaño del logo
                contentScale = ContentScale.Fit
            )
        }
    }
}