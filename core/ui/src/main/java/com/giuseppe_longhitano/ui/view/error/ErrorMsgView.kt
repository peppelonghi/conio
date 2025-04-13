package com.giuseppe_longhitano.ui.view.error

 import androidx.compose.foundation.background
 import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.Row
 import androidx.compose.foundation.layout.padding
 import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
 import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
 import androidx.compose.ui.unit.dp
 import com.giuseppe_longhitano.ui.R

@Composable
fun ErrorMsgView(modifier: Modifier = Modifier, throwable: Throwable? = null) {
    val msg = throwable?.message ?: stringResource(R.string.default_msg_error)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .background(Color.Red)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Warning, // The error icon
            contentDescription = msg, // For accessibility
            tint = Color.White // Customize the color
        )
        Text(text = msg, color = Color.White)
    }

}