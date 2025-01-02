package com.uen.democognitoauthamplify

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uen.democognitoauthamplify.util.SyncProgressViewModel

@Composable
fun AnimatedProgressBar(viewModel: SyncProgressViewModel) {
    val progress by viewModel.syncProgress.collectAsState(initial = 0)
    val message by viewModel.syncMessage.collectAsState(initial = "Sincronizando datos...")

    // Animación de deslizamiento continuo
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 200f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF67A225), Color(0xFFB9E4A5))
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mensaje de sincronización
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                ),
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(10.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(Color(0xFFE8F5E9)) // Fondo de la barra
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp) // Ancho de la "luz" animada
                        .offset(x = animatedOffset.dp) // Offset animado
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xFF0D730F),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar porcentaje
            Text(
                text = "$progress%",
                style = MaterialTheme.typography.bodyLarge.copy(),
                color = Color.White
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun AnimatedProgressBarPreview() {
    val fakeViewModel = object : SyncProgressViewModel() {
        override val syncProgress: LiveData<Int> = MutableLiveData(65)
        override val syncMessage: LiveData<String> = MutableLiveData("Sincronizando datos...")
    }
    AnimatedProgressBar(viewModel = fakeViewModel)
}
*/
