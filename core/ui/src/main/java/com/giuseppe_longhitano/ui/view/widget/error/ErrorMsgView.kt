package com.giuseppe_longhitano.ui.view.widget.error

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.giuseppe_longhitano.arch.event.CommonEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.R

@Composable
fun ErrorMsgView(
    modifier: Modifier = Modifier,
    throwable: Throwable? = null,
    handleEvent: (UIEvent) -> Unit
) {
    val msg = throwable?.message ?: stringResource(R.string.default_msg_error)
    Row(
        modifier
            .background(Color.Red)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.weight(0.2f),
            imageVector = Icons.Filled.Warning,
            contentDescription = msg,
            tint = Color.White
        )
        Text(
            modifier = Modifier.weight(0.6f),
            text = msg, color = Color.White
        )
        Icon(
            modifier = Modifier
                .weight(0.2f)
                .clickable {
                    handleEvent.invoke(CommonEvent.Retry)
                },
            imageVector = Icons.Filled.Refresh,
            contentDescription = msg,
            tint = Color.White
        )

    }

}