package org.sundog.copy.ui

import org.sundog.copy.data.entity.CopyContent

fun interface WindowAction {
    operator fun invoke(onAction: WindowOnAction)
}

sealed interface WindowOnAction {
    data class MoveToSettings(
        val currentCopyContents: List<CopyContent>,
    ) : WindowOnAction

    data object MoveToTop : WindowOnAction
    data object Close : WindowOnAction
    data class ChangeAlwaysOnTop(
        val enable: Boolean,
    ) : WindowOnAction
}