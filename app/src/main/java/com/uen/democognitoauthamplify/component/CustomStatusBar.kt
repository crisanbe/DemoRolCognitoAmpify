package com.uen.democognitoauthamplify.component

import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.predicate.QueryField
import com.amplifyframework.datastore.generated.model.Bus
import com.amplifyframework.datastore.generated.model.Device
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CustomStatusBar(username: String) {
    val context = LocalContext.current
    val currentDate = remember { mutableStateOf(getCurrentDate()) }
    val gpsStatus = remember { mutableStateOf(checkGpsStatus(context)) }
    val busPlate = remember { mutableStateOf("Cargando...") }

    // Animación para el ícono
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Actualizar la fecha, GPS y placa del bus en tiempo real
    LaunchedEffect(Unit) {
        while (true) {
            currentDate.value = getCurrentDate()
            gpsStatus.value = checkGpsStatus(context)
            queryBusPlate(username) { plate ->
                busPlate.value = plate
            }
            delay(1000) // Actualización cada segundo
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)) // Esquinas curvas superiores
            .background(Color(0xFF619B22)), // Fondo verde para la barra
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Mostrar el estado del GPS con un ícono animado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)) // Esquinas curvas superiores
                .background(Color(0xFF619B22)), // Fondo verde para la barra
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Mostrar la fecha actual
            Text(
                text = currentDate.value,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFB0BEC5))
                    .border(
                        width = 2.dp,
                        color = Color(0xFF37474F),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = busPlate.value,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // Mostrar el estado del GPS con un ícono animado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (gpsStatus.value) Color(0xFFA8E6CF) else Color(0xFFFF8A80)
                        )
                        .graphicsLayer(
                            scaleX = if (gpsStatus.value) scale else 1f,
                            scaleY = if (gpsStatus.value) scale else 1f,
                            shadowElevation = 8f,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (gpsStatus.value) Icons.Filled.LocationOn else Icons.Filled.LocationOff,
                        contentDescription = if (gpsStatus.value) "GPS Activo" else "GPS Inactivo",
                        tint = Color(0xFF619B22),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (gpsStatus.value) "GPS Activo" else "GPS Inactivo",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

    }
}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}

fun checkGpsStatus(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun queryBusPlate(imei: String, onResult: (String) -> Unit) {
    val deviceQuery = QueryField.field("imei").eq(imei)

    Amplify.DataStore.query(
        Device::class.java,
        deviceQuery,
        { devices ->
            if (devices.hasNext()) {
                val device = devices.next()
                // Query the bus using busID from device
                Amplify.DataStore.query(
                    Bus::class.java,
                    QueryField.field("id").eq(device.bus.id),
                    { buses ->
                        if (buses.hasNext()) {
                            val bus = buses.next()
                            onResult(bus.plate)
                        } else {
                            onResult("No Bus")
                        }
                    },
                    { error ->
                        Log.e("StatusBar", "Error querying bus", error)
                        onResult("Error")
                    }
                )
            } else {
                onResult("No Device")
            }
        },
        { error ->
            Log.e("StatusBar", "Error querying device", error)
            onResult("Error")
        }
    )
}
