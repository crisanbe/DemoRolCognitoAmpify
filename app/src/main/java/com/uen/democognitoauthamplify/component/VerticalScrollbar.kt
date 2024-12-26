package com.uen.democognitoauthamplify.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("FrequentlyChangedStateReadInComposition")
@Composable
fun VerticalScrollbar(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    itemCount: Int
) {
    val thumbHeight = 100.dp
    val totalItemsCount = listState.layoutInfo.totalItemsCount
    val firstVisibleItemIndex = listState.firstVisibleItemIndex
    val visibleItems = listState.layoutInfo.visibleItemsInfo.size

    val density = LocalDensity.current
    val proportion = if (totalItemsCount > visibleItems) {
        firstVisibleItemIndex.toFloat() / (totalItemsCount - visibleItems)
    } else {
        0f
    }
    var isDragging by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .width(8.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDragCancel = { isDragging = false },
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        with(density) {
                            val availableHeight =
                                (size.height - thumbHeight.toPx()).coerceAtLeast(0f)
                            if (availableHeight > 0) {
                                val newOffset = (proportion * availableHeight + dragAmount)
                                    .coerceIn(0f, availableHeight)
                                val newProportion = newOffset / availableHeight
                                val maxIndex = (itemCount - 1).coerceAtLeast(0)
                                val newIndex = (newProportion * (totalItemsCount - visibleItems))
                                    .toInt()
                                    .coerceIn(0, maxIndex)

                                scope.launch {
                                    listState.scrollToItem(newIndex)
                                }
                            }
                        }
                    }
                )
            }
    ) {
        with(density) {
            val availableHeight = (size.height - thumbHeight.toPx()).coerceAtLeast(0f)
            if (availableHeight > 0) {
                val scrollbarPosition = proportion * availableHeight
                drawRoundRect(
                    color = if (isDragging) Color.Yellow else Color(0xFF67A225),
                    topLeft = Offset(0f, scrollbarPosition),
                    size = Size(8.dp.toPx(), thumbHeight.toPx())
                )
            }
        }
    }
}