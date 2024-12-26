package com.uen.democognitoauthamplify.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.amplifyframework.datastore.generated.model.Use

@Composable
fun UpdateItemDialog(todo: Use, onDismiss: () -> Unit, onUpdate: (Use) -> Unit, onDelete: (Use) -> Unit) {
    var cardNumber by remember { mutableStateOf(todo.cardNumber ?: "") }
    var balance by remember { mutableStateOf(todo.balance ?: 0.0) }
    var sequenceNumber by remember { mutableStateOf(todo.sequenceNumber ?: 0) }
    var status by remember { mutableStateOf(todo.status ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Card Number") }
                )
                OutlinedTextField(
                    value = balance.toString(),
                    onValueChange = { balance = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Balance") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = sequenceNumber.toString(),
                    onValueChange = { sequenceNumber = it.toIntOrNull() ?: 0 },
                    label = { Text("Sequence Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = status.toString(),
                    onValueChange = { status = it },
                    label = { Text("Status") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedItem = todo.copyOfBuilder()
                    .cardNumber(cardNumber)
                    .balance(balance)
                    .sequenceNumber(sequenceNumber)
                    .status(status as Boolean?)
                    .build()
                onUpdate(updatedItem)
            }) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Bus Icon",
                tint = Color(0xFFE53935),
                modifier = Modifier.clickable {
                    onDelete(todo)
                    onDismiss()
                }
            )
        }
    )
}