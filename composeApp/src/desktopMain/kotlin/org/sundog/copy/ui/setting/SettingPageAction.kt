package org.sundog.copy.ui.setting

import org.sundog.copy.data.entity.CopyContent

fun interface SettingPageAction {
    operator fun invoke(onAction: SettingPageOnAction)
}

sealed interface SettingPageOnAction {
    data object AddCopyContent : SettingPageOnAction
    data class DeleteCopyContent(
        val copyContent: CopyContent,
    ) : SettingPageOnAction

    data object SaveCopyContents : SettingPageOnAction
    data object MoveToAboutApp : SettingPageOnAction
    data class ChangeCopyContent(
        val index: Int,
        val copyContent: CopyContent,
    ) : SettingPageOnAction
}
