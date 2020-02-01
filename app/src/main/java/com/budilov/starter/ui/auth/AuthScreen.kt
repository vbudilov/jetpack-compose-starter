package com.budilov.starter.ui.auth

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
import androidx.ui.layout.Column
import androidx.ui.tooling.preview.Preview

enum class AvailableAuthScreen {
    LOGIN, REGISTRATION, PASSWORD_RESET
}

@Model
data class CurrentScreen(var currentAuthScreen: AvailableAuthScreen = AvailableAuthScreen.LOGIN)

val currentScreen = CurrentScreen()

@Composable
fun AuthScreen() {
    val state = state { CurrentScreen() }

    Column {
        when (currentScreen.currentAuthScreen) {
            AvailableAuthScreen.LOGIN -> LoginScreen()
            AvailableAuthScreen.REGISTRATION -> RegisterScreen()
        }
    }
}

@Preview("Login")
@Composable
fun AuthScreenLoginPreview() {
    LoginScreen()
}

@Preview("Register")
@Composable
fun AuthScreenRegisterPreview() {
    RegisterScreen()
}

/**
 * AuthScreen navigator
 *
 */
fun authScreenNavigation(destination: AvailableAuthScreen) {
    currentScreen.currentAuthScreen = destination
}
