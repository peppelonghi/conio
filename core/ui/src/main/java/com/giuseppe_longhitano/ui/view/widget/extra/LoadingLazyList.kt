package com.giuseppe_longhitano.ui.view.widget.extra

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.ui.R

@Composable
fun LoadingLazyList() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items((0..10).toList()) {
            LoadingItem()
        }
    }
}




@Composable
  fun LoadingItem(modifier: Modifier = Modifier) {

    val brushColor = brushColor()
    val color = brushColor().first()

    Column(modifier = modifier.padding(horizontal = 8.dp)) {
        Row {
            val transition = rememberInfiniteTransition(label = "")
            val translateAnimation by transition.animateFloat(
                initialValue = 0f,
                targetValue = 2000f, // Adjust the target value to control the animation length
                animationSpec = infiniteRepeatable(
                    animation = tween(1000) // Adjust the duration for speed
                ), label = ""
            )

            Canvas(modifier = Modifier.size(45.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
                val radius = (minOf(canvasWidth, canvasHeight) / 2) - 10
                val path = Path().apply {
                    addOval(
                        Rect(
                            center = center,
                            radius = radius
                        )
                    )
                }

                clipPath(path) {
                    // Draw the background of the shimmer
                    drawCircle(
                        color = color,
                        center = center,
                        radius = radius
                    )
                    val rotatedAngle = 15f
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = brushColor,
                            start = Offset(x = (translateAnimation - (canvasWidth*2)) + (canvasWidth * kotlin.math.sin(Math.toRadians(rotatedAngle.toDouble()).toFloat())), y = (canvasHeight * (1-kotlin.math.cos(Math.toRadians(rotatedAngle.toDouble()).toFloat()))) ),
                            end = Offset(x = translateAnimation + (canvasWidth * kotlin.math.sin(Math.toRadians(rotatedAngle.toDouble()).toFloat())), y = (canvasHeight)  )
                        ),
                        size = size,
                        blendMode = BlendMode.DstAtop
                    )

                }
            }
            Column {
                (0..3).map {
                    Box(
                        modifier = Modifier
                            .padding(end = (if (it % 2 != 0) 50 else 1).dp)
                            .padding(2.dp)
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.shimmer_item_height))
                            .clip(RoundedCornerShape(5.dp))
                            .shimmerEffect()
                    )
                }
            }
        }
    }

}

@Composable
fun brushColor() = listOf(
    MaterialTheme.colorScheme.primary,
    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
    MaterialTheme.colorScheme.primary,
)




private fun Modifier.shimmerEffect(showShimmer: Boolean = true): Modifier = composed {
    if (showShimmer) {
        var size by remember { mutableStateOf(IntSize.Zero) }
        val transition = rememberInfiniteTransition(label = "")
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(2000)
            ), label = ""
        )

        background(
            brush = Brush.linearGradient(
                colors = brushColor(),
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )
        )
            .onGloballyPositioned {
                size = it.size
            }
    } else {
        this
    }

}