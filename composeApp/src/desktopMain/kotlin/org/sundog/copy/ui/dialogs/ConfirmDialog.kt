package org.sundog.copy.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow

@OptIn(ExperimentalTextApi::class)
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    onClickPositiveButton: () -> Unit,
    negativeButtonText: String,
    onClickNegativeButtonClick: () -> Unit,
    onCloseRequest: () -> Unit,
) {
    DialogWindow(
        onCloseRequest = {
            onCloseRequest()
        },
        title = title,
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = message,
                fontFamily = FontFamily("メイリオ"),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(onClick = {
                    onClickNegativeButtonClick()
                }) {
                    Text(
                        text = negativeButtonText,
                        fontFamily = FontFamily("メイリオ"),
                    )
                }

                Button(onClick = {
                    onClickPositiveButton()
                }) {
                    Text(
                        text = positiveButtonText,
                        fontFamily = FontFamily("メイリオ"),
                    )
                }
            }
        }
    }
}
