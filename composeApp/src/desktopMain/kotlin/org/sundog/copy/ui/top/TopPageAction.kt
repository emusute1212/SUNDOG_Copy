package org.sundog.copy.ui.top

import org.sundog.copy.data.entity.CopyContent

fun interface TopPageAction {
    operator fun invoke(onAction: TopPageUiAction)
}

sealed interface TopPageUiAction {
    data class ClickCopyContent(
        val copyText: String,
    ) : TopPageUiAction

    data class ClickAlwaysTopWindowButton(
        val enable: Boolean,
    ) : TopPageUiAction

    data object CloseDialogButton : TopPageUiAction
    data class ClickSettingButton(
        val currentCopyContents: List<CopyContent>,
    ) : TopPageUiAction
}
