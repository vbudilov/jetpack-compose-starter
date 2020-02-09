package com.budilov.starter.ui

import android.util.Log
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Typography
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.sp
import com.amazonaws.mobile.client.AWSMobileClient
import com.budilov.starter.ui.auth.AuthScreen
import com.budilov.starter.ui.home.HomeScreen
import com.budilov.starter.ui.home.TAG


val lightColors = lightColorPalette(
    primary = Color(0xFF1EB980),
    surface = Color(0xFF26282F),
    onSurface = Color.White
)


val darkColors = darkColorPalette(
    primary = Color(0xFF66ffc7)
)

val colors = if (isSystemInDarkTheme()) darkColors else lightColors

val typography = Typography(
    h1 = TextStyle(
        fontFamily = FontFamily("RobotoCondensed"),
        fontWeight = FontWeight.W100,
        fontSize = 96.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily("RobotoCondensed"),
        fontWeight = FontWeight.W600,
        fontSize = 14.sp
    )
)

enum class AvailableTopLevelScreens {
    AUTH, HOME
}

@Model
data class CurrentTopLevelScreen(var currentScreen: AvailableTopLevelScreens)


val currentTopLevelScreens = CurrentTopLevelScreen(currentScreen = AvailableTopLevelScreens.HOME)

@Composable
fun MyAppUI() {

    val state = state { currentTopLevelScreens }

    if (AWSMobileClient.getInstance() == null || !AWSMobileClient.getInstance().isSignedIn) {
        Log.i(TAG, "Not logged in")
        topLevelNavigation(AvailableTopLevelScreens.AUTH)

    } else {
        Log.i(TAG, "logged in")
        topLevelNavigation(AvailableTopLevelScreens.HOME)
    }

    MaterialTheme(colors = colors, typography = typography) {
        Column {
            when (state.value.currentScreen) {
                AvailableTopLevelScreens.AUTH -> AuthScreen()
                AvailableTopLevelScreens.HOME -> HomeScreen()
            }
        }
    }


}

@Preview("LoggedIn")
@Composable
fun MyAppPreviewLoggedIn() {
    currentTopLevelScreens.currentScreen = AvailableTopLevelScreens.HOME
    MyAppUI()
}

@Preview("Not LoggedIn")
@Composable
fun MyAppPreviewNotLoggedIn() {
    currentTopLevelScreens.currentScreen = AvailableTopLevelScreens.AUTH
    MyAppUI()
}

fun topLevelNavigation(currentScreen: AvailableTopLevelScreens) {
    currentTopLevelScreens.currentScreen = currentScreen
}