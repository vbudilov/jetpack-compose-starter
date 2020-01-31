package com.budilov.starter.ui

import androidx.compose.Composable
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
import com.budilov.starter.model.AuthStatus
import com.budilov.starter.model.Navigation
import com.budilov.starter.ui.auth.AuthScreen
import com.budilov.starter.ui.home.HomeScreen
import com.budilov.starter.ui.auth.LoginScreen


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

@Composable
fun MyAppUI(authStatus: AuthStatus, navigation: Navigation? = null) {
    MaterialTheme(colors = colors, typography = typography) {
            Column {
                if (authStatus.loggedIn)
                    HomeScreen()
                else
                    AuthScreen()
            }
    }
}

@Preview("LoggedIn")
@Composable
fun MyAppPreviewLoggedIn() {
    MyAppUI(
        authStatus = AuthStatus(
            true
        )
    )
}

@Preview("Not LoggedIn")
@Composable
fun MyAppPreviewNotLoggedIn() {
    MyAppUI(
        authStatus = AuthStatus(
            false
        )
    )
}