package org.sundog.copy.ui.setting

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.sundog.copy.data.entity.CopyContent
import org.sundog.copy.ui.dialogs.ConfirmDialog
import org.sundog.copy.viewModel.SettingPageViewModel

@Composable
fun SettingPageHost(
    viewModel: SettingPageViewModel = koinViewModel(),
    shouldShowDialog: Boolean,
    currentCopyContents: List<CopyContent>,
    onMoveToTopPage: () -> Unit,
    onCancelClose: () -> Unit,
    onChangeCopyContent: () -> Unit,
    onSaveCopyContent: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.initialize(
            currentCopyContents = currentCopyContents,
        )
    }

    val pageState = rememberSettingPageState(
        viewModel = viewModel,
        shouldShowDialog = shouldShowDialog,
        onMoveToTopPage = onMoveToTopPage,
        onCancelClose = onCancelClose,
        onChangeCopyContent = onChangeCopyContent,
        onSaveCopyContent = onSaveCopyContent,
    )

    if (pageState.shouldDontSaveShowDialog) {

        ConfirmDialog(
            title = stringResource(Res.string.setting_close_confirm_dialog_title),
            message = stringResource(Res.string.setting_close_confirm_dialog_message),
            positiveButtonText = stringResource(Res.string.setting_close_confirm_dialog_positive),
            onClickPositiveButton = {
                pageState.onAction(
                    SettingPageOnAction.SaveCopyContents
                )
            },
            negativeButtonText = stringResource(Res.string.setting_close_confirm_dialog_negative),
            onClickNegativeButtonClick = {
                pageState.onAction(
                    SettingPageOnAction.CloseDialogButton,
                )
            },
            onCloseRequest = {
                pageState.onAction(
                    SettingPageOnAction.CloseDialogButton,
                )
            }
        )
    }

    SettingPage(
        pageState = pageState,
    )
}

@Composable
private fun SettingPage(
    pageState: SettingPageState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SettingHeader()
        SettingContent(
            copyContents = pageState.copyContents,
            onClickDeleteButton = {
                pageState.onAction(
                    onAction = SettingPageOnAction.DeleteCopyContent(it)
                )
            },
            onChangeCopyContent = { index, copyContent ->
                pageState.onAction(
                    onAction = SettingPageOnAction.ChangeCopyContent(
                        index = index,
                        copyContent = copyContent
                    )
                )
            },
            modifier = Modifier.weight(1f),
        )
        SettingFooter(
            onClickAddButton = {
                pageState.onAction(
                    onAction = SettingPageOnAction.AddCopyContent
                )
            },
            onClickSaveButton = {
                pageState.onAction(
                    onAction = SettingPageOnAction.SaveCopyContents
                )
            },
            onClickAboutButton = {
                pageState.onAction(
                    onAction = SettingPageOnAction.MoveToAboutApp
                )
            }
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun SettingHeader() {
    Text(
        text = stringResource(Res.string.setting_name),
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
private fun SettingContent(
    modifier: Modifier,
    copyContents: List<CopyContent>,
    onClickDeleteButton: (copyContent: CopyContent) -> Unit,
    onChangeCopyContent: (index: Int, copyContent: CopyContent) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 8.dp,
            ),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                SettingContentHeader()
            }
            itemsIndexed(copyContents) { index, copyContent ->
                Row(
                    modifier = Modifier
                        .height(34.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedTextFieldInSundogCopy(
                        value = copyContent.label,
                        textStyle = TextStyle.Default.copy(
                            fontFamily = FontFamily("メイリオ"),
                            fontSize = 12.sp,
                        ),
                        onValueChange = {
                            onChangeCopyContent(
                                index,
                                copyContent.copy(
                                    label = it,
                                )
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                    OutlinedTextFieldInSundogCopy(
                        value = copyContent.copyText,
                        textStyle = TextStyle.Default.copy(
                            fontFamily = FontFamily("メイリオ"),
                            fontSize = 12.sp,
                        ),
                        onValueChange = {
                            onChangeCopyContent(
                                index,
                                copyContent.copy(
                                    copyText = it,
                                )
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                    Button(
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = { onClickDeleteButton(copyContent) }
                    ) {
                        Text(
                            text = stringResource(Res.string.setting_delete_copy_content_button),
                            fontFamily = FontFamily("メイリオ"),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OutlinedTextFieldInSundogCopy(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        textStyle = textStyle,
    ) { innerTextField ->
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            visualTransformation = visualTransformation,
            innerTextField = innerTextField,
            singleLine = singleLine,
            enabled = enabled,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(4.dp),
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun SettingContentHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.setting_copy_label_header_label),
            fontFamily = FontFamily("メイリオ"),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = stringResource(Res.string.setting_copy_content_header_label),
            fontFamily = FontFamily("メイリオ"),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = stringResource(Res.string.setting_delete_copy_content_header_label),
            fontFamily = FontFamily("メイリオ"),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun SettingFooter(
    onClickAddButton: () -> Unit,
    onClickSaveButton: () -> Unit,
    onClickAboutButton: () -> Unit,
) {
    Column {
        Divider(modifier = Modifier.fillMaxWidth())
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 4.dp
                ),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onClickAddButton()
                },
            ) {
                Text(
                    text = stringResource(Res.string.setting_add_copy_content_button),
                    fontFamily = FontFamily("メイリオ"),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onClickSaveButton()
                },
            ) {
                Text(
                    text = stringResource(Res.string.setting_save_copy_content_button),
                    fontFamily = FontFamily("メイリオ"),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onClickAboutButton()
                },
            ) {
                Text(
                    text = stringResource(Res.string.setting_about_app_copy_content_button),
                    fontFamily = FontFamily("メイリオ"),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
