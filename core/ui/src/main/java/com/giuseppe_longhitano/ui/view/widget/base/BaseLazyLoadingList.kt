package com.giuseppe_longhitano.ui.view.widget.base

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.ListModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.UIState
import com.giuseppe_longhitano.ui.view.widget.error.ErrorMsgView
import com.giuseppe_longhitano.ui.view.widget.extra.LoadingLazyList


@Composable
fun <T> BaseLazyLoadingList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    uiState: UIState<ListModel<T>>,
    handleEvent: (UIEvent) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable  (T) -> Unit,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,

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
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled =userScrollEnabled,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(data?.items.orEmpty()) { item ->
                content.invoke(item)
            }
            item {
                if (data?.isLoading == true)
                    CircularProgressIndicator()
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



