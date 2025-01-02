@file:OptIn(ExperimentalMaterial3Api::class)

package com.uen.democognitoauthamplify.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.generated.model.Bus
import com.amplifyframework.datastore.generated.model.Company
import com.amplifyframework.datastore.generated.model.Device
import kotlinx.coroutines.launch

@Composable
fun AppBar(
    username: String,
    totalItems: String,
    onCreateTodo: () -> Unit,
    onCreateVarious: suspend (Int) -> Unit,
    onDeleteAllTodos: () -> Unit,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
) {
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    val companyName = remember { mutableStateOf("Cargando...") }

    LaunchedEffect(username) {
        Log.d("MyAmplifyApp", "Iniciando consulta para el usuario: $username")

        queryCompany(username) { plate ->
            companyName.value = plate
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF619B22)
            ),
            title = {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "IMEI:$username", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Todo Uso: $totalItems", style = MaterialTheme.typography.bodyMedium)
                }
            },
            actions = {
                Spacer(modifier = Modifier.height(120.dp))
                Row {
                    IconButton(onClick = onCreateTodo) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Crear Todo",
                            tint = Color.Green
                        )
                    }
                    IconButton(onClick = {
                        if (!isLoading) {
                            isLoading = true
                            scope.launch {
                                try {
                                    onCreateVarious(10) // Crear 10 elementos
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    }) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Yellow,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                Icons.Default.Send,
                                contentDescription = "Crear datos de prueba",
                                tint = Color.Yellow
                            )
                        }
                    }
                    IconButton(onClick = onDeleteAllTodos) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar Todos",
                            tint = Color.Red
                        )
                    }
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = companyName.value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Cívica",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        // Borde blanco curvado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 80.dp)
                )
        )
    }
}

fun queryCompany(imei: String, onResult: (String) -> Unit) {
    // Primero buscar el Device por IMEI
    val deviceQuery = QueryField.field("imei").eq(imei)
    Amplify.DataStore.query(
        Device::class.java,
        deviceQuery,
        { devices ->
            if (devices.hasNext()) {
                val device = devices.next()
                // Obtener la empresa asociada al device
                Amplify.DataStore.query(
                    Company::class.java,
                    QueryField.field("id").eq(device.company.id),
                    { companies ->
                        if (companies.hasNext()) {
                            val company = companies.next()
                            onResult(company.name)
                        } else {
                            onResult("Empresa no encontrada")
                        }
                    },
                    { error ->
                        Log.e("AppBar", "Error consultando empresa", error)
                        onResult("Error")
                    }
                )
            } else {
                onResult("Dispositivo no encontrado")
            }
        },
        { error ->
            Log.e("AppBar", "Error consultando dispositivo", error)
            onResult("Error")
        }
    )
}