package org.sundog.copy.ui

import androidx.compose.runtime.*

data class SundogCopyAppState(
    val pageType: PageType,
    val isAlwaysOnTop: Boolean,
    val onAction: WindowAction,
)

sealed interface PageType {
    data object TopPage : PageType
    data object SettingPage : PageType
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

                WindowOnAction.MoveToSettings -> {
                    currentPageType = PageType.SettingPage
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