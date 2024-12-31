package org.sundog.copy.ui.about

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import java.io.FileInputStream
import java.util.*

@Composable
fun AboutPageHost(
    onCloseRequest: () -> Unit,
) {
    Window(
        state = rememberWindowState(
            width = 300.dp,
            height = 350.dp,
            position = WindowPosition(Alignment.Center),
        ),
        onCloseRequest = onCloseRequest,
        title = stringResource(Res.string.about_page_title),
    ) {
        AboutPage()
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun AboutPage() {
    val urlHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 8.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(Res.string.about_page_title),
            fontFamily = FontFamily("メイリオ"),
            fontSize = 36.sp,
        )
        Text(
            text = stringResource(Res.string.about_page_using_version_label),
            fontFamily = FontFamily("メイリオ"),
            fontSize = 16.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = getVersionName(),
                fontFamily = FontFamily("メイリオ"),
                fontSize = 28.sp,
            )
            Text(
                text = stringResource(Res.string.about_page_desu),
                fontFamily = FontFamily("メイリオ"),
                fontSize = 16.sp,
            )
        }
        Button(
            content = {
                Text(
                    text = stringResource(Res.string.about_page_check_update),
                    fontFamily = FontFamily("メイリオ"),
                    fontSize = 16.sp,
                )
            },
            onClick = {
                urlHandler.openUri("https://www.google.com/")
            }
        )
        Text(
            text = stringResource(Res.string.about_page_boot_browser),
            fontFamily = FontFamily("メイリオ"),
            fontSize = 12.sp,
        )
        Text(
            text = stringResource(Res.string.about_page_copyright),
            fontFamily = FontFamily("メイリオ"),
            fontSize = 9.sp,
        )
    }
}

private fun getVersionName(): String {
    return Properties().let {
        it.load(
            FileInputStream("version.properties")
        )
        return@let "${it.getProperty("app.version.code")} ${it.getProperty("app.version.name")}"
    }
}
