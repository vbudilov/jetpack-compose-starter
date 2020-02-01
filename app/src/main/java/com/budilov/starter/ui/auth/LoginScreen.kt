package com.budilov.starter.ui.auth

import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Alignment
import androidx.ui.core.Opacity
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.ButtonStyle
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.budilov.starter.R
import com.budilov.starter.common.PasswordBox
import com.budilov.starter.common.TextBox
import com.budilov.starter.common.Title
import com.budilov.starter.service.auth.CognitoAuthService
import com.budilov.starter.ui.AvailableTopLevelScreens
import com.budilov.starter.ui.colors
import com.budilov.starter.ui.topLevelNavigation

@Model
data class LoginInput(
    var username: String = "",
    var password: String = ""
)

enum class LoginStateEnum {
    SMS_MFA, LOGGED_IN, NEW_PASSWORD_REQUIRED, LOGIN_ERROR
}

@Model
data class LoginState(var state: LoginStateEnum? = null, var message: String? = null)

@Composable
fun LoginScreen() {

    val loginInput = LoginInput()
    val image = imageResource(R.drawable.img_logo)

    Surface(modifier = LayoutHeight.Fill, color = Color.White) {
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

                VerticalScroller {

                    Column(arrangement = Arrangement.SpaceEvenly) {
                        Spacer(modifier = LayoutHeight(100.dp))
                        Title(
                            text = "Login",
                            color = colors.primary
                        )

                        Spacer(modifier = LayoutHeight(30.dp))
                        TextBox(onValueChange = { input -> loginInput.username = input })

                        Spacer(modifier = LayoutHeight(5.dp))
                        PasswordBox(onValueChange = { input -> loginInput.password = input })

                        Spacer(modifier = LayoutHeight(15.dp))
                        Container(alignment = Alignment.Center, expanded = true) {

                            Button(modifier = LayoutWidth.Fill, text = "Login", style = ButtonStyle(
                                elevation = 3.dp,
                                backgroundColor = colors.primary, contentColor = Color.White,
                                shape = RoundedCornerShape(3.dp)
                            ),
                                onClick = {
                                    CognitoAuthService.signIn(
                                        loginInput.username,
                                        loginInput.password
                                    ) {
                                        when (it) {
                                            LoginStateEnum.LOGGED_IN -> topLevelNavigation(
                                                AvailableTopLevelScreens.HOME
                                            )
                                            LoginStateEnum.LOGIN_ERROR -> println("LOGIN_ERROR")
                                        }
                                    }
                                })
                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}