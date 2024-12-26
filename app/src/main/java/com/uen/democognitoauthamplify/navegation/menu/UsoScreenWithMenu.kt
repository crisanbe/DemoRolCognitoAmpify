package com.uen.democognitoauthamplify.navegation.menu

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.uen.democognitoauthamplify.UsoScreen
import com.uen.democognitoauthamplify.network.SnackbarViewModel
import com.uen.democognitoauthamplify.util.SyncProgressViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsoScreenWithMenu(
    username: String,
    navHostController: NavHostController,
    syncProgressViewModel: SyncProgressViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Contenedor con esquinas curvas
            Box(
                modifier = Modifier
                    .width(280.dp) // Ajusta el ancho del menú
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)) // Esquinas curvas
                    .background(Color.White)
                    .padding(8.dp) // Agrega un poco de espacio alrededor para destacar las curvas
            ) {
                DrawerContent(
                    username = username,
                    navController = navHostController,
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        },
        content = {
            UsoScreen(
                username = username,
                snackbarHostState = remember { SnackbarHostState() },
                syncProgressViewModel = syncProgressViewModel,
                drawerState = drawerState
            )
        }
    )
}
