package com.uen.democognitoauthamplify.component

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DirectionsBusFilled
import androidx.compose.material.icons.filled.SyncLock
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amplifyframework.datastore.generated.model.Use

@Composable
fun TodoItem(todo: Use, index: Int, synchronizedItems: Set<String>, onSelect: (Use) -> Unit) {
    val isSynchronized = synchronizedItems.contains(todo.id)
    val syncStatus = if (isSynchronized) 1 else 0
    val syncMessage = if (isSynchronized) "Sincronizado" else "Pendiente!"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onSelect(todo) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono principal (ej. Bus)
            Icon(
                imageVector = Icons.Default.DirectionsBusFilled,
                contentDescription = "Route Icon",
                tint = Color(0xFF68B684), // Verde
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Información del item
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${index + 1}. ${todo.cardNumber ?: "Sin número"}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Balance: ${todo.balance}",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = syncMessage,
                    style = MaterialTheme.typography.bodySmall.copy(color = if (isSynchronized) Color.Green else Color.LightGray)
                )
            }

            // Indicador de sincronización
            SyncStatusIcon(status = syncStatus)
        }
    }
}

@Composable
fun SyncStatusIcon(status: Int) {
    val (syncColor, syncIcon) = when (status) {
        1 -> Pair(Color(0xFF4CAF50), Icons.Default.SyncLock) // Verde: Sincronizado
        0 -> Pair(Color(0xFFFFEB3B), Icons.Default.SyncProblem) // Amarillo: Pendiente
        else -> Pair(Color(0xFFF44336), Icons.Default.SyncProblem) // Rojo: Error
    }

    Icon(
        imageVector = syncIcon,
        contentDescription = "Sync Status",
        tint = syncColor,
        modifier = Modifier.size(24.dp)
    )
}