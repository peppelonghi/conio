package com.giuseppe_longhitano.ui.view.atomic_view

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.giuseppe_longhitano.ui.view.widget.error.ErrorImageView


@Composable
fun LoadingImageView(modifier: Modifier = Modifier, url: String, errorView: @Composable ()->Unit = { ErrorImageView(modifier = Modifier)}, contentDescription: String? = null) {
    SubcomposeAsyncImage(
        contentScale = ContentScale.Crop,
        modifier = modifier,
        model = url,
        loading = { CircularProgressIndicator() },
        error = { errorView() },
        contentDescription =  contentDescription
    )
}


