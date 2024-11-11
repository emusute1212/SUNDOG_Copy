package org.sundog.copy.ui.top

import androidx.compose.runtime.*
import org.sundog.copy.data.entity.CopyContent
import org.sundog.copy.utils.GovernCopy
import org.sundog.copy.viewModel.TopPageViewModel

data class TopPageState(
    val alwaysOnTop: Boolean,
    val shouldShowRestoreDialog: Boolean,
    val copyContents: List<CopyContent>,
    val onAction: TopPageAction,
)

@Composable
fun rememberTopPageState(
    viewModel: TopPageViewModel,
    isCurrentAlwaysOnTop: Boolean,
    changeWindowAlwaysTop: (Boolean) -> Unit,
    onClickSettingButton: () -> Unit,
): TopPageState? {
    val loadedDataContent by viewModel.loadedDataContent.collectAsState()
    var shouldShowRestoreDialog by remember { mutableStateOf(false) }
    val onAction = remember {
        TopPageAction {
            when (it) {
                is TopPageUiAction.ClickCopyContent -> {
                    GovernCopy.doCopy(it.copyText)
                }

                is TopPageUiAction.ClickAlwaysTopWindowButton -> {
                    changeWindowAlwaysTop(it.enable)
                }

                TopPageUiAction.CloseDialogButton -> {
                    shouldShowRestoreDialog = false
                }

                TopPageUiAction.ClickSettingButton -> {
                    onClickSettingButton()
                }
            }
        }
    }
    return remember(
        isCurrentAlwaysOnTop,
        loadedDataContent,
        shouldShowRestoreDialog,
        onAction,
    ) {
        loadedDataContent?.let {
            TopPageState(
                alwaysOnTop = isCurrentAlwaysOnTop,
                shouldShowRestoreDialog = shouldShowRestoreDialog,
                copyContents = it.copyContent,
                onAction = onAction,
            )
        }
    }
}