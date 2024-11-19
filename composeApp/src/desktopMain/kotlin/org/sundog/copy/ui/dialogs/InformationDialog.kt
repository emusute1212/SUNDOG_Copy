package org.sundog.copy.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
fun InformationDialog(
    title: String,
    message: String,
    buttonText: String,
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
            Button(onClick = {
                onCloseRequest()
            }) {
                Text(
                    text = buttonText,
                    fontFamily = FontFamily("メイリオ"),
                )
            }
        }
    }
}