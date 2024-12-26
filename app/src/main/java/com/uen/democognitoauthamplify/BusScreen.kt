@file:OptIn(ExperimentalMaterial3Api::class, InternalApi::class)

package com.uen.democognitoauthamplify

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import aws.smithy.kotlin.runtime.InternalApi
import aws.smithy.kotlin.runtime.util.type
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreItemChange
import com.amplifyframework.datastore.generated.model.Bus

@Composable
fun BusScreen(username: String) {
    var busList by remember { mutableStateOf(emptyList<Bus>()) }

    fun queryBuses() {
        val filter = QueryField.field("busOwner").eq(username)
        Amplify.DataStore.query(
            Bus::class.java,
            filter,
            { items ->
                val tempList = mutableListOf<Bus>()
                while (items.hasNext()) {
                    tempList.add(items.next())
                }
                busList = tempList
            },
            { error -> Log.e("MyAmplifyApp", "Query failed", error) }
        )
    }
    // Observando los cambios en la DataStore en tiempo real
    DisposableEffect(Unit) {
        val observeOperation = Amplify.DataStore.observe(
            Bus::class.java,
            { Log.i("MyAmplifyApp", "Observation started") },
            { change ->
                when (change.type()) {
                    DataStoreItemChange.Type.CREATE, DataStoreItemChange.Type.UPDATE -> {
                        queryBuses()

                    }
                    DataStoreItemChange.Type.DELETE -> {

                    }
                    else -> Log.w("MyAmplifyApp", "Unhandled change type: ${change.type()}")
                }
            },
            { error -> Log.e("MyAmplifyApp", "Observation failed", error) },
            { Log.i("MyAmplifyApp", "Observation completed") }
        )

        onDispose {
            observeOperation.type()
        }
    }
    LaunchedEffect(Unit) {
        queryBuses()
    }

    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        topBar = {
            TopAppBar(
                title = { Text("Buses") },
                actions = {
                    IconButton(onClick = { queryBuses() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                itemsIndexed(busList) { index, bus ->
                    BusItem(bus = bus, index = index)
                }
            }
        }
    }
}

@Composable
fun BusItem(bus: Bus, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${index + 1}. Placa: ${bus.plate}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Compañia ID: ${bus.company?.name ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
