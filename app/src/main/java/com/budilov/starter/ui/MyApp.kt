package com.budilov.starter.ui

import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Typography
import androidx.ui.material.lightColorPalette
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.sp
import com.budilov.starter.ui.auth.AuthScreen
import com.budilov.starter.ui.home.HomeScreen


val colors = lightColorPalette(
    primary = Color(0xFF1EB980),
    surface = Color(0xFF26282F),
    onSurface = Color.White
)

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


val currentTopLevelScreens = CurrentTopLevelScreen(currentScreen = AvailableTopLevelScreens.AUTH)

@Composable
fun MyAppUI() {
    MaterialTheme(colors = colors, typography = typography) {
        Column {
            when (currentTopLevelScreens.currentScreen) {
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