package com.giuseppe_longhitano.ui.view.widget.base

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.giuseppe_longhitano.arch.routing.Route
import com.giuseppe_longhitano.ui.BaseViewModel
import com.giuseppe_longhitano.ui.view.widget.base.ui_model.ListModel
import com.giuseppe_longhitano.ui.view.widget.error.ErrorMsgView
import com.giuseppe_longhitano.ui.view.widget.extra.LoadingLazyList






@Composable
fun <T> PageLazyLoadingList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    viewModel: BaseViewModel<T>,
    handleEvent: (UIEvent) -> Unit,
    contentItem: @Composable (Any) -> Unit,
    onChangeRoute: (Route) -> Unit,
) {

    PageScreen(
        onChangeRoute = onChangeRoute,
        viewModel = viewModel,
        loadingView = { LoadingLazyList() },
        handleEvent = handleEvent,
        modifier = modifier,
        contentScreen = { uiState ->
            val listModel = (uiState.data as ListModel<Any>)
            LazyColumn(
                horizontalAlignment = horizontalAlignment,
                flingBehavior = flingBehavior,
                userScrollEnabled = userScrollEnabled,
                state = state,
                contentPadding = contentPadding,
                reverseLayout = reverseLayout,
                verticalArrangement = verticalArrangement,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(listModel.items) { item ->
                    contentItem.invoke(item)
                }
                item {
                    if (listModel.isItemsLoading)
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                }
                item {
                    if (listModel.error !=null) {
                        Box {
                            ErrorMsgView(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth(), throwable = listModel.error
                            ) {
                                handleEvent.invoke(CommonEvent.Next)
                            }
                        }
                    }
                }
                item {
                    if (!listModel.endReached && listModel.items.isEmpty()) {
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
                    if (!listModel.endReached && !listModel.isItemsLoading && listModel.error == null) {
                        handleEvent.invoke(CommonEvent.Next)
                    }
                }
            }
        }
    )

}



