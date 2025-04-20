package com.giuseppe_longhitano.ui.view.widget.error

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.giuseppe_longhitano.ui.R


@Composable
fun ErrorImageView(modifier: Modifier) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Filled.Warning,
        contentDescription = stringResource(R.string.error_loading_img),
        tint = Color.White
    )
}