package com.uen.democognitoauthamplify.navegation

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uen.democognitoauthamplify.RouteScreen
import com.uen.democognitoauthamplify.BusScreen
import com.uen.democognitoauthamplify.navegation.menu.UsoScreenWithMenu
import com.uen.democognitoauthamplify.start.StartLoadingScreen
import com.uen.democognitoauthamplify.network.SnackbarViewModel
import com.uen.democognitoauthamplify.util.SyncProgressViewModel

@Composable
fun AppNavigation(
    username: String,
    snackbarViewModel: SnackbarViewModel = hiltViewModel(),
    syncProgressViewModel: SyncProgressViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
    val isSyncComplete = sharedPreferences.getBoolean("is_sync_complete", false)

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = if (isSyncComplete) "uso" else "start" // Determina destino inicial
            ) {
                composable("start") {
                    StartLoadingScreen(
                        username = username,
                        viewModel = syncProgressViewModel,
                        navController = navController
                    )
                    // Actualiza estado de sincronización en SharedPreferences
                    syncProgressViewModel.isSyncComplete.collectAsState().value.let {
                        sharedPreferences.edit().putBoolean("is_sync_complete", it).apply()
                    }
                }
                composable("uso") {
                    UsoScreenWithMenu(
                        username = username,
                        navHostController = navController,
                        syncProgressViewModel = syncProgressViewModel
                    )
                }
                composable("routes") {
                    RouteScreen(username = username, navHostController = navController)
                }
                composable("buses") {
                    BusScreen(username = username)
                }
            }
        }
    }
}
