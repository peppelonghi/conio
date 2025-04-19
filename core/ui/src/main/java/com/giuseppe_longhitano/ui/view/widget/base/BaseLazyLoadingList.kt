package com.giuseppe_longhitano.ui.view.widget.base

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.R
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.ListModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.error.ErrorMsgView
import com.giuseppe_longhitano.ui.view.widget.extra.LoadingItem
import com.giuseppe_longhitano.ui.view.widget.extra.LoadingLazyList
import kotlin.collections.orEmpty


@Composable
fun <T> BaseLazyLoadingList(
    uiState: UIState<ListModel<T>>, modifier: Modifier = Modifier,
    handleEvent: (UIEvent) -> Unit, contentItem: @Composable (T) -> Unit,
) {
    val data = uiState.data
    BaseScreen(
        uiState = uiState,
        loadingView = {
            LoadingLazyList()
        },
        handleEvent = handleEvent, modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data?.items.orEmpty()) { item ->
                contentItem.invoke(item)
            }
            item {
                if (data?.isLoading == true)
                    LoadingItem()
            }
            item {
                if (data?.error != null) {
                    ErrorMsgView(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(), throwable = data.error
                    ) {
                        handleEvent.invoke(CommonEvent.Next)
                    }
                }
            }
            item {
                if (data?.endReached == false && data.items.isEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No data available")
                    }
                }
            }
            item {
                if (data?.endReached == false && !data.isLoading && data.error == null) {
                    handleEvent.invoke(CommonEvent.Next)
                }
            }
        }
    }

}



