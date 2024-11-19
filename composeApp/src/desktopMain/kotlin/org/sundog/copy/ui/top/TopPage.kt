package org.sundog.copy.ui.top

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.sundog.copy.data.entity.CopyContent
import org.sundog.copy.ui.dialogs.InformationDialog
import org.sundog.copy.viewModel.TopPageViewModel

@Composable
fun TopPageHost(
    viewModel: TopPageViewModel = koinViewModel(),
    isCurrentAlwaysOnTop: Boolean,
    changeWindowAlwaysTop: (Boolean) -> Unit,
    onClickSettingButton: (currentCopyContents: List<CopyContent>) -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.initialize()
    }
    val pageState = rememberTopPageState(
        viewModel = viewModel,
        isCurrentAlwaysOnTop = isCurrentAlwaysOnTop,
        changeWindowAlwaysTop = changeWindowAlwaysTop,
        onClickSettingButton = onClickSettingButton,
    ) ?: return

    TopPage(pageState)
}

@Composable
private fun TopPage(
    pageState: TopPageState,
) {
    if (pageState.shouldShowRestoreDialog) {
        InformationDialog(
            title = stringResource(Res.string.data_restore_dialog_title),
            message = stringResource(Res.string.data_restore_dialog_message),
            buttonText = stringResource(Res.string.data_restore_dialog_button),
            onCloseRequest = {
                pageState.onAction(
                    TopPageUiAction.CloseDialogButton
                )
            }
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SundogCopyHeader()
        CopyContent(
            copyContents = pageState.copyContents,
            onClickContent = {
                pageState.onAction(
                    TopPageUiAction.ClickCopyContent(
                        copyText = it.copyText,
                    )
                )
            }
        )
        SundogCopyFooter(
            alwaysOnTop = pageState.alwaysOnTop,
            onCheckedChange = {
                pageState.onAction(
                    TopPageUiAction.ClickAlwaysTopWindowButton(
                        enable = it,
                    )
                )
            },
            onClickSettingButton = {
                pageState.onAction(
                    onAction = TopPageUiAction.ClickSettingButton(
                        currentCopyContents = pageState.copyContents,
                    )
                )
            }
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun SundogCopyHeader() {
    Text(
        text = stringResource(Res.string.app_name),
        fontFamily = FontFamily("メイリオ"),
        fontSize = 36.sp,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
            )
    )
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Black,
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun ColumnScope.CopyContent(
    copyContents: List<CopyContent>,
    onClickContent: (CopyContent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp),
    ) {
        items(copyContents) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    onClickContent(it)
                }
            ) {
                Text(
                    text = it.label,
                    fontFamily = FontFamily(
                        "メイリオ"
                    ),
                    fontSize = 24.sp,
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun SundogCopyFooter(
    alwaysOnTop: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    onClickSettingButton: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onCheckedChange(
                        alwaysOnTop.not()
                    )
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = alwaysOnTop,
                onCheckedChange = {
                    onCheckedChange(it)
                }
            )
            Text(
                text = stringResource(Res.string.always_top_window),
                fontFamily = FontFamily("メイリオ"),
                fontSize = 12.sp,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            contentPadding = PaddingValues(
                all = 0.dp,
            ),
            onClick = {
                onClickSettingButton()
            },
        ) {
            Text(
                text = stringResource(Res.string.setting_name),
                fontFamily = FontFamily("メイリオ"),
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
@Preview
fun PreviewTopPage() {
    TopPage(
        pageState = TopPageState(
            alwaysOnTop = false,
            shouldShowRestoreDialog = false,
            copyContents = listOf(
                CopyContent(
                    label = "item_1",
                    copyText = "copy_1"
                ),
                CopyContent(
                    label = "item_2",
                    copyText = "copy_2"
                ),
                CopyContent(
                    label = "item_3",
                    copyText = "copy_3"
                ),
            ),
            onAction = {},
        )
    )
}