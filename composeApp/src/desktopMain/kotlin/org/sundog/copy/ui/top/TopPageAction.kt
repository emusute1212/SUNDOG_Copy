package org.sundog.copy.ui.top

fun interface TopPageAction {
    operator fun invoke(uiAction: TopPageUiAction)
}

sealed interface TopPageUiAction {
    data class ClickCopyContent(
        val copyText: String,
    ) : TopPageUiAction

    data class ClickAlwaysTopWindowButton(
        val enable: Boolean,
    ) : TopPageUiAction

    data object CloseDialogButton : TopPageUiAction
}
