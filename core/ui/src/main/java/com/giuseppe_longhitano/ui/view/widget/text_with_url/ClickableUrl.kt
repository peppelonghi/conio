package com.giuseppe_longhitano.ui.view.widget.text_with_url


import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.giuseppe_longhitano.arch.event.NavigationEvent
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.arch.routing.ExternalRoute
import com.giuseppe_longhitano.domain.model.Url

@Composable
fun ClickableUrl(
    modifier: Modifier = Modifier,
    textBeforeLink: String = "",
    textAfterLink: String = "",
    url: Url,
    handleEvent: (UIEvent) -> Unit = {}
) {
    when (url) {
        is Url.HttpUrl -> {
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append(textBeforeLink)
                }
                pushStringAnnotation(tag = "URL", annotation = url.value)
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(" ${url.value}")
                }
                pop()
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append(textAfterLink)
                }
            }
            BasicText(
                text = annotatedText,
                modifier = modifier.pointerInput(Unit) {
                    detectTapGestures { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "URL",
                            start = offset.x.toInt(),
                            end = offset.y.toInt()
                        )
                            .firstOrNull()?.let { _ ->
                                handleEvent.invoke(NavigationEvent(ExternalRoute(url)))
                            }
                    }
                }
            )
        }

        is Url.NotValidUrl -> Unit
    }

}
