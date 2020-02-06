package com.budilov.starter.ui.auth

import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Alignment
import androidx.ui.core.Opacity
import androidx.ui.foundation.DrawImage
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.amazonaws.mobile.client.AWSMobileClient
import com.budilov.starter.R
import com.budilov.starter.ui.AvailableTopLevelScreens
import com.budilov.starter.ui.topLevelNavigation

enum class AvailableAuthScreen {
    LOGIN, REGISTRATION, PASSWORD_RESET, PASSWORD_CONFIRM
}

@Model
data class CurrentScreen(var currentAuthScreen: AvailableAuthScreen = AvailableAuthScreen.LOGIN)

val currentScreen = CurrentScreen()

@Composable
fun AuthScreen() {

    if (AWSMobileClient.getInstance()?.isSignedIn == true)
        topLevelNavigation(AvailableTopLevelScreens.HOME)

    val image = imageResource(R.drawable.img_logo)

    Column {

        Surface(modifier = LayoutHeight.Fill, color = Color(0xFCA47B)) {
            Column(arrangement = Arrangement.Begin) {
                Container(
                    alignment = Alignment.Center, modifier = LayoutWidth.Fill,
                    expanded = true, height = 150.dp
                ) {
                    Opacity(opacity = .6f) {
                        DrawImage(image = image)
                    }
                }
                Padding(padding = 10.dp) {
                    when (currentScreen.currentAuthScreen) {
                        AvailableAuthScreen.LOGIN -> LoginScreen()
                        AvailableAuthScreen.REGISTRATION -> RegisterScreen()
                    }
                }
            }
        }

    }

}

@Preview("Login")
@Composable
fun AuthScreenLoginPreview() {
    authScreenNavigation(AvailableAuthScreen.LOGIN)
    AuthScreen()
}

@Preview("Register")
@Composable
fun AuthScreenRegisterPreview() {
    authScreenNavigation(AvailableAuthScreen.REGISTRATION)

    AuthScreen()
}

/**
 * AuthScreen navigator
 *
 */
fun authScreenNavigation(destination: AvailableAuthScreen) {
    currentScreen.currentAuthScreen = destination
}
