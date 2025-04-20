package com.giuseppe_longhitano.ui.view.widget.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(data?.items.orEmpty()) { item ->
                contentItem.invoke(item)
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



