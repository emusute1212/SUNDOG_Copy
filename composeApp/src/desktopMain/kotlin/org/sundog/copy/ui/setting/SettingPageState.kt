package org.sundog.copy.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.sundog.copy.data.entity.CopyContent
import org.sundog.copy.viewModel.SettingPageViewModel

data class SettingPageState(
    val copyContents: List<CopyContent>,
    val onAction: SettingPageAction,
)

@Composable
fun rememberSettingPageState(
    viewModel: SettingPageViewModel,
): SettingPageState {
    val copyContents by viewModel.currentCopyContents.collectAsState()
    val onAction = remember(viewModel) {
        SettingPageAction {
            when (it) {
                SettingPageOnAction.AddCopyContent -> {
                    viewModel.onAddCopyContent()
                }

                is SettingPageOnAction.DeleteCopyContent -> {
                    viewModel.onDeleteCopyContent(it.copyContent)
                }

                SettingPageOnAction.MoveToAboutApp -> {
                }

                SettingPageOnAction.SaveCopyContents -> {
                    viewModel.onSaveCopyContents()
                }

                is SettingPageOnAction.ChangeCopyContent -> {
                    viewModel.onChangeCopyContent(
                        targetIndex = it.index,
                        targetCopyContent = it.copyContent,
                    )
                }
            }
        }
    }

    return remember(
        copyContents,
        onAction,
    ) {
        SettingPageState(
            copyContents = copyContents,
            onAction = onAction,
        )
    }
}
