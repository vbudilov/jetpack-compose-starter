package com.budilov.starter.ui.auth

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
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
import com.budilov.starter.ui.colors

enum class AuthStateEnum {
    LOGIN, REGISTER
}

@Model
data class AuthState(val currentState: AuthStateEnum = AuthStateEnum.LOGIN)

@Model
data class LoginInput(
    var username: String = "",
    var usernameEntered: Boolean = false,
    var password: String = ""
)

@Composable
fun AuthScreen() {
    val state = state { AuthState() }

    Column {
        when (state.value.currentState) {
            AuthStateEnum.LOGIN -> LoginScreen(state.value)
            AuthStateEnum.REGISTER -> RegisterScreen(state.value)
        }
    }
}

@Composable
fun LoginScreen(authState: AuthState) {

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
                        TextBox(loginInput = loginInput)

                        Spacer(modifier = LayoutHeight(5.dp))
                        PasswordBox(loginInput = loginInput)

                        Spacer(modifier = LayoutHeight(15.dp))
                        Container(alignment = Alignment.Center, expanded = true) {

                            Button(modifier = LayoutWidth.Fill, text = "Login", style = ButtonStyle(
                                elevation = 3.dp,
                                backgroundColor = colors.primary, contentColor = Color.White,
                                shape = RoundedCornerShape(3.dp)
                            ),
                                onClick = {
                                    CognitoAuthService.signIn("user", "pass")
                                })
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun RegisterScreen(authState: AuthState) {

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
                            text = "Register",
                            color = colors.primary
                        )

                        Spacer(modifier = LayoutHeight(30.dp))
                        TextBox(loginInput = loginInput)

                        Spacer(modifier = LayoutHeight(5.dp))
                        PasswordBox(loginInput = loginInput)

                        Spacer(modifier = LayoutHeight(15.dp))
                        Container(alignment = Alignment.Center, expanded = true) {

                            Button(modifier = LayoutWidth.Fill, text = "Create Account", style = ButtonStyle(
                                elevation = 3.dp,
                                backgroundColor = colors.primary, contentColor = Color.White,
                                shape = RoundedCornerShape(3.dp)
                            ),
                                onClick = {
                                    CognitoAuthService.signUp("user", "pass")
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
    LoginScreen(authState = AuthState())
}