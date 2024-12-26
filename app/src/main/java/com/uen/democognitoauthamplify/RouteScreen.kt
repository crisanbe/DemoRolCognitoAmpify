@file:OptIn(InternalApi::class)

package com.uen.democognitoauthamplify

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
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
import androidx.navigation.NavHostController
import aws.smithy.kotlin.runtime.InternalApi
import aws.smithy.kotlin.runtime.util.type
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.DataStoreItemChange
import com.uen.democognitoauthamplify.component.actomic.plantilla.BaseScreenWithMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteScreen(
    username: String,
    navHostController: NavHostController,
    drawerState : DrawerState = rememberDrawerState(DrawerValue.Closed)
) {
   /* var routeList by remember { mutableStateOf(emptyList<Route>()) }

    // Consulta para obtener las rutas
    fun queryRoutes() {
        val filter = QueryField.field("routeOwner").eq(username)
        Amplify.DataStore.query(
            Route::class.java,
            filter,
            { items ->
                val tempList = mutableListOf<Route>()
                while (items.hasNext()) {
                    tempList.add(items.next())
                }
                routeList = tempList
            },
            { error -> Log.e("MyAmplifyApp", "Error fetching routes", error) }
        )
    }
    // Observando los cambios en la DataStore en tiempo real
    DisposableEffect(Unit) {
        val observeOperation = Amplify.DataStore.observe(
            Route::class.java,
            { Log.i("MyAmplifyApp", "Observation started") },
            { change ->
                when (change.type()) {
                    DataStoreItemChange.Type.CREATE, DataStoreItemChange.Type.UPDATE -> {
                        queryRoutes()

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
        queryRoutes()
    }*/

    BaseScreenWithMenu(
        title = "Rutas",
        username = username,
        navHostController = navHostController,
        drawerState = drawerState,
        content = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp)
                    ) {
                       /* itemsIndexed(routeList) { index, route ->
                            RouteItem(route = route, index = index)
                        }*/
                    }

        }
    )
}

class Route(
    val id: String,
    val routeName: String,
    val fare: Double
)

@Composable
fun RouteItem(route: Route, index: Int) {
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
                text = "${index + 1}. Ruta ID: ${route.id}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Nombre: ${route.routeName}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tarifa: ${route.fare}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
        }
    }
}
