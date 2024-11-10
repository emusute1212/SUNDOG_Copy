package org.sundog.copy.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplication
import org.sundog.copy.di.appModule
import org.sundog.copy.ui.top.TopPageHost

fun main() = application {
    val windowState = rememberWindowState(
        width = 320.dp,
        height = 400.dp,
        position = WindowPosition(Alignment.Center)
    )
    var isAlwaysOnTop: Boolean by remember { mutableStateOf(false) }
    val changeWindowAlwaysTop: (Boolean) -> Unit = remember {
        { isAlwaysOnTop = it }
    }
    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = stringResource(Res.string.app_name),
        alwaysOnTop = isAlwaysOnTop,
    ) {
        KoinApplication(application = {
            modules(appModule())
        }) {
            MaterialTheme {
                TopPageHost(
                    isCurrentAlwaysOnTop = isAlwaysOnTop,
                    changeWindowAlwaysTop = changeWindowAlwaysTop,
                )
            }
        }
    }
}
