package org.sundog.copy.ui

fun interface WindowAction {
    operator fun invoke(onAction: WindowOnAction)
}

sealed interface WindowOnAction {
    data object MoveToSettings : WindowOnAction
    data object MoveToTop : WindowOnAction
    data object Close : WindowOnAction
    data class ChangeAlwaysOnTop(
        val enable: Boolean,
    ) : WindowOnAction
}