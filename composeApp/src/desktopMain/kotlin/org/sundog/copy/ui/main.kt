package org.sundog.copy.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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
import org.sundog.copy.ui.setting.SettingPageHost
import org.sundog.copy.ui.top.TopPageHost

fun main() = application {
    val sundogCopyAppState = rememberSundogCopyAppState(
        onCloseRequest = ::exitApplication,
    )
    App(
        sundogCopyAppState = sundogCopyAppState,
    )
}

@Composable
private fun App(
    sundogCopyAppState: SundogCopyAppState,
) {
    MaterialTheme {
        KoinApplication(application = {
            modules(appModule())
        }) {
            when (val pageType = sundogCopyAppState.pageType) {
                PageType.TopPage -> {
                    Window(
                        state = rememberWindowState(
                            width = 320.dp,
                            height = 400.dp,
                            position = WindowPosition(Alignment.Center),
                        ),
                        onCloseRequest = {
                            sundogCopyAppState.onAction(
                                onAction = WindowOnAction.Close
                            )
                        },
                        title = stringResource(Res.string.app_name),
                        alwaysOnTop = sundogCopyAppState.isAlwaysOnTop,
                    ) {
                        TopPageHost(
                            isCurrentAlwaysOnTop = sundogCopyAppState.isAlwaysOnTop,
                            changeWindowAlwaysTop = {
                                sundogCopyAppState.onAction(
                                    onAction = WindowOnAction.ChangeAlwaysOnTop(it)
                                )
                            },
                            onClickSettingButton = {
                                sundogCopyAppState.onAction(
                                    onAction = WindowOnAction.MoveToSettings(
                                        currentCopyContents = it
                                    )
                                )
                            },
                        )
                    }
                }

                is PageType.SettingPage -> {
                    Window(
                        state = rememberWindowState(
                            width = 500.dp,
                            height = 400.dp,
                            position = WindowPosition(Alignment.Center),
                        ),
                        onCloseRequest = {
                            sundogCopyAppState.onAction(
                                onAction = WindowOnAction.MoveToTop
                            )
                        },
                        title = stringResource(Res.string.app_name),
                    ) {
                        SettingPageHost(
                            currentCopyContents = pageType.currentCopyContents,
                        )
                    }
                }
            }
        }
    }
}