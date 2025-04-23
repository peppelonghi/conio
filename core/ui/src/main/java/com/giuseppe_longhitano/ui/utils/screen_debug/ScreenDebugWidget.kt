package com.giuseppe_longhitano.ui.utils.screen_debug

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.text
 import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun CardStack(
    modifier: Modifier = Modifier,
    cards: List<String> = listOf("Card 1", "Card 2", "Card 3", "Card 4"),
    verticalOffset: Dp = 50.dp,
    elevation: Dp = 4.dp
) {
    Box(modifier = modifier) {
        cards.forEachIndexed { index, cardText ->
            Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .offset(y = (index * verticalOffset)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = elevation + (index * 2).dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cardText,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun InteractiveCardStack(
    modifier: Modifier = Modifier,
    cards: List<CardData> = defaultCards()
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    Box(modifier = modifier.fillMaxSize()) {
        cards.forEachIndexed { index, card ->
            val isVisible = index >= currentIndex
            val stackIndex = index - currentIndex

            AnimatedVisibility(
                visible = isVisible && stackIndex < 4, // Show max 4 cards
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ) + fadeIn(),
                exit = slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(300)
                ) + fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .offset(
                            x = (stackIndex * 12).dp,
                            y = (stackIndex * 8).dp
                        )
                        .clickable {
                            if (stackIndex == 0) {
                                currentIndex = (currentIndex + 1) % cards.size
                            }
                        },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp - (stackIndex * 2).dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = card.backgroundColor
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = card.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = card.textColor,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = card.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = card.textColor.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Instructions
        Text(
            text = "Tap the top card to cycle through",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

data class CardData(
    val title: String,
    val description: String,
    val backgroundColor: Color,
    val textColor: Color
)

fun defaultCards() = listOf(
    CardData(
        title = "First Card",
        description = "This is the first card in the stack",
        backgroundColor = Color(0xFF6200EE),
        textColor = Color.White
    ),
    CardData(
        title = "Second Card",
        description = "This is the second card with different styling",
        backgroundColor = Color(0xFF03DAC6),
        textColor = Color.Black
    ),
    CardData(
        title = "Third Card",
        description = "Another card with unique appearance",
        backgroundColor = Color(0xFFFF6200),
        textColor = Color.White
    ),
    CardData(
        title = "Fourth Card",
        description = "The fourth card in our collection",
        backgroundColor = Color(0xFF3700B3),
        textColor = Color.White
    ),
    CardData(
        title = "Fifth Card",
        description = "Last card that cycles back to the beginning",
        backgroundColor = Color(0xFF018786),
        textColor = Color.White
    )
)

@Preview(showBackground = true)
@Composable
fun CardStackPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {


            CardStack(
                modifier = Modifier.height(280.dp)
            )
        }
    }
}