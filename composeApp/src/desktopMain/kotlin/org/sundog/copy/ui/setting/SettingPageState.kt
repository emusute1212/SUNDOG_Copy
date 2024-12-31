package org.sundog.copy.ui.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import org.sundog.copy.data.entity.CopyContent
import org.sundog.copy.viewModel.SettingPageViewModel

data class SettingPageState(
    val copyContents: List<CopyContent>,
    val shouldDontSaveShowDialog: Boolean,
    val onAction: SettingPageAction,
)

@Composable
fun rememberSettingPageState(
    viewModel: SettingPageViewModel,
    shouldShowDialog: Boolean,
    onMoveToTopPage: () -> Unit,
    onCancelClose: () -> Unit,
    onChangeCopyContent: () -> Unit,
    onSaveCopyContent: () -> Unit,
    onShowAboutPage: () -> Unit,
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
                    onShowAboutPage()
                }

                SettingPageOnAction.MoveToTop -> {
                    onMoveToTopPage()
                }

                SettingPageOnAction.SaveCopyContents -> {
                    onSaveCopyContent()
                    viewModel.onSaveCopyContents()
                    onMoveToTopPage()
                }

                is SettingPageOnAction.ChangeCopyContent -> {
                    onChangeCopyContent()
                    viewModel.onChangeCopyContent(
                        targetIndex = it.index,
                        targetCopyContent = it.copyContent,
                    )
                }

                SettingPageOnAction.CloseDialogButton -> {
                    onCancelClose()
                }
            }
        }
    }

    return remember(
        copyContents,
        shouldShowDialog,
        onAction,
    ) {
        SettingPageState(
            copyContents = copyContents,
            shouldDontSaveShowDialog = shouldShowDialog,
            onAction = onAction,
        )
    }
}
