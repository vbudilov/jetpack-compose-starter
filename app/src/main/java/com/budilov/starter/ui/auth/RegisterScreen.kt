package com.budilov.starter.ui.auth

import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Alignment
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.ButtonStyle
import androidx.ui.material.TextButtonStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.budilov.starter.common.PasswordBox
import com.budilov.starter.common.TextBox
import com.budilov.starter.common.Title
import com.budilov.starter.service.auth.CognitoAuthService
import com.budilov.starter.ui.colors

@Model
data class RegInput(
    var username: String = "",
    var password: String = ""
)

@Composable
fun RegisterScreen() {

    val regInput = RegInput()

    VerticalScroller {

        Column(arrangement = Arrangement.SpaceEvenly) {
            Spacer(modifier = LayoutHeight(100.dp))
            Title(
                text = "Register",
                color = colors.primary
            )

            Spacer(modifier = LayoutHeight(30.dp))
            TextBox(onValueChange = { input -> regInput.username = input })

            Spacer(modifier = LayoutHeight(5.dp))
            PasswordBox(onValueChange = { input -> regInput.password = input })

            Spacer(modifier = LayoutHeight(15.dp))
            Container(alignment = Alignment.Center, expanded = true) {

                Button(modifier = LayoutWidth.Fill,
                    text = "Create Account",
                    style = ButtonStyle(
                        elevation = 3.dp,
                        backgroundColor = colors.primary, contentColor = Color.White,
                        shape = RoundedCornerShape(3.dp)
                    ),
                    onClick = {
                        CognitoAuthService.signUp(regInput.username, regInput.password, onSignUp = {

                        })
                    })
            }

            Container(expanded = true) {
                Padding(top = 15.dp) {
                    Row(modifier = LayoutWidth.Fill) {
                        Container(alignment = Alignment.TopLeft) {

                            Button(text = "Already Registered? Login", style = TextButtonStyle(), onClick = {
                                authScreenNavigation(AvailableAuthScreen.LOGIN)
                            })
                        }
                        Container(alignment = Alignment.TopRight, expanded = true) {
                            Button(
                                text = "Confirm Account",
                                style = TextButtonStyle(),
                                onClick = {
                                    authScreenNavigation(AvailableAuthScreen.PASSWORD_CONFIRM)
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
fun RegisterScreenPreview() {
    RegisterScreen()
}