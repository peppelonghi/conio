package com.giuseppe_longhitano.ui.view.widget.containerWithContextualMenu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.giuseppe_longhitano.arch.event.UIEvent
import java.util.UUID

 @OptIn(ExperimentalFoundationApi::class)
 @Composable
fun <T> ContainerWithContextualMenu(
    modifier: Modifier = Modifier,
    headerMenu: String = "",
    anchorGravity: AnchorGravity = AnchorGravity.BOTTOM_START,
    content: @Composable (Modifier) -> Unit,
    menuItems: List<ContextMenu<T>>,
    handleEvent: (ContextMenuEvent) -> Unit,
) {
    var containerCoordinates: LayoutCoordinates? by remember { mutableStateOf(null) }
    var expanded by remember { mutableStateOf(false) }

    val iternalModifier = modifier
        .onGloballyPositioned { coordinates ->
            containerCoordinates = coordinates
        }
        .combinedClickable(onClick = {
            handleEvent.invoke(ContextMenuEvent.ContainerClicked)
        }, onLongClick = {
            expanded = expanded.not()
        })
    Box {
        content.invoke(iternalModifier)
    }

    if (expanded && containerCoordinates != null) {
        val density = LocalDensity.current
        val configuration = LocalConfiguration.current

        Popup(
            popupPositionProvider = ContextPopupPositionProvider(
                screenWidth = configuration.screenWidthDp.dp,
                density = density,
                anchorGravity = anchorGravity,
                containerCoordinates = containerCoordinates!!,
            ),
            onDismissRequest = { expanded = expanded.not() }
        ) {

            // Popup content
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    if (headerMenu.isNotEmpty()) {
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = headerMenu,
                            modifier = Modifier.padding(16.dp)
                        )

                        menuItems.forEach {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (it.leadingIcon != null)
                                    Icon(
                                        imageVector = it.leadingIcon,
                                        contentDescription = "",
                                        modifier = Modifier

                                    )
                                Text(
                                    style = MaterialTheme.typography.labelMedium,
                                    text = it.label,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                if (it.trailingIcon != null)
                                    Icon(
                                        imageVector = it.trailingIcon,
                                        contentDescription = "",
                                    )
                            }

                        }

                    }
                }
            }
        }
    }

}

@Composable
private fun <T> makeMenusFake(item: T) = listOf(
    ContextMenu(
        model = item,
        id = "",
        leadingIcon = Icons.Default.Menu,
        label = "(MOVE_BACK_ID)"
    ),
    ContextMenu(
        model = item,
        id = "",
        leadingIcon = Icons.Default.Menu,
        label = "Sposta in avanti",
        trailingIcon = Icons.Default.Menu,


        ),
    ContextMenu(
        model = item,
        id = "",
        leadingIcon = Icons.Default.Menu,
        label = "Sposta indietro",
    )
)

data class ContextMenu<T>(
    val id: String = UUID.randomUUID().toString(),
    val label: String,
    val model: T,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
)


sealed class ContextMenuEvent : UIEvent {

    data object ContainerClicked :
        ContextMenuEvent() {
    }

    data class MenuItemClicked<T>(val item: ContextMenu<T>) :
        ContextMenuEvent()
}


class ContextPopupPositionProvider(
    private val anchorGravity: AnchorGravity,
    private val containerCoordinates: LayoutCoordinates,
    private val density: Density,
    private val screenWidth: Dp

) : PopupPositionProvider {


    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val containerBoundsInRoot = containerCoordinates.boundsInRoot()

        // Calculate popup position based on container's top-left
        val containerTopLeftX = containerBoundsInRoot.topLeft.x.toInt()
        val containerTopLeftY = containerBoundsInRoot.topLeft.y.toInt()
        val containerBottomLeftY = containerBoundsInRoot.bottomLeft.y.toInt()


        val popupWidthPx = popupContentSize.width
        val screenWidthPx = with(density) { screenWidth.roundToPx() }

        val popupY = when (anchorGravity) {
            AnchorGravity.TOP_START, AnchorGravity.TOP_CENTER,
            AnchorGravity.TOP_END -> containerTopLeftY
            AnchorGravity.BOTTOM_START, AnchorGravity.BOTTOM_CENTER,
            AnchorGravity.BOTTOM_END -> containerBottomLeftY
        }

        val popupX =
            when (anchorGravity) {
                AnchorGravity.BOTTOM_CENTER,
                AnchorGravity.TOP_CENTER-> containerTopLeftX + ( containerBoundsInRoot.width.toInt() / 2) - (popupWidthPx / 2)
                AnchorGravity.TOP_START, AnchorGravity.BOTTOM_START -> containerTopLeftX.coerceIn(0, (screenWidthPx - popupWidthPx).coerceAtLeast(0))
                AnchorGravity.TOP_END, AnchorGravity.BOTTOM_END -> containerBoundsInRoot.topRight.x.toInt()
            }
        return IntOffset(
            popupX, popupY
        )
    }

}

enum class AnchorGravity {
    TOP_START, TOP_END, TOP_CENTER, BOTTOM_CENTER, BOTTOM_START, BOTTOM_END
}
