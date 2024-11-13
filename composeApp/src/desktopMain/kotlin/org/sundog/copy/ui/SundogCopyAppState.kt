package org.sundog.copy.ui

import androidx.compose.runtime.*
import org.sundog.copy.data.entity.CopyContent

data class SundogCopyAppState(
    val pageType: PageType,
    val isAlwaysOnTop: Boolean,
    val onAction: WindowAction,
)

sealed interface PageType {
    data object TopPage : PageType
    data class SettingPage(
        val currentCopyContents: List<CopyContent>,
    ) : PageType
}

@Composable
fun rememberSundogCopyAppState(
    firstPage: PageType = PageType.TopPage,
    onCloseRequest: () -> Unit = {},
): SundogCopyAppState {
    var isAlwaysOnTop by remember { mutableStateOf(false) }
    var currentPageType by remember { mutableStateOf(firstPage) }
    val onAction = remember {
        WindowAction {
            when (it) {
                WindowOnAction.MoveToTop -> {
                    currentPageType = PageType.TopPage
                }

                is WindowOnAction.MoveToSettings -> {
                    currentPageType = PageType.SettingPage(
                        currentCopyContents = it.currentCopyContents,
                    )
                }

                WindowOnAction.Close -> {
                    onCloseRequest()
                }

                is WindowOnAction.ChangeAlwaysOnTop -> {
                    isAlwaysOnTop = it.enable
                }
            }
        }
    }
    return remember(
        currentPageType,
        isAlwaysOnTop,
        onAction,
    ) {
        SundogCopyAppState(
            pageType = currentPageType,
            isAlwaysOnTop = isAlwaysOnTop,
            onAction = onAction,
        )
    }
}