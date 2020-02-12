package com.budilov.starter.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.remember
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.res.imageResource
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.amazonaws.mobile.client.AWSMobileClient
import com.budilov.starter.R
import com.budilov.starter.service.auth.CognitoAuthService
import com.budilov.starter.ui.AvailableTopLevelScreens
import com.budilov.starter.ui.colors
import com.budilov.starter.ui.topLevelNavigation

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

const val TAG = "HomeScreen"

@Composable
fun HomeScreen() {
    val scaffoldState = remember { ScaffoldState() }


    val imgDrawer = imageResource(R.drawable.img_drawer_top)

    if (AWSMobileClient.getInstance() == null || !AWSMobileClient.getInstance().isSignedIn) {
        Log.i(TAG, "Not logged in")
        topLevelNavigation(AvailableTopLevelScreens.AUTH)
    } else {
        Log.i(TAG, "logged in")
        topLevelNavigation(AvailableTopLevelScreens.HOME)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Container(alignment = Alignment.TopLeft, expanded = true) {
                Column {
                    Container(height = 150.dp, expanded = true) {
                        DrawImage(image = imgDrawer)
                    }
                    VerticalScroller(modifier = LayoutHeight.Fill) {
                        Padding(left = 15.dp) {
                            Column {
                                Spacer(modifier = LayoutHeight(15.dp))
                                Text("Your Profile")
                                Button(
                                    style = TextButtonStyle(shape = RoundedCornerShape(10.dp)),
                                    modifier = LayoutPadding(5.dp), text = "Personal Info"
                                )
                                Button(
                                    style = TextButtonStyle(shape = RoundedCornerShape(10.dp)),
                                    modifier = LayoutPadding(5.dp), text = "Messages"
                                )

                                Spacer(modifier = LayoutHeight(15.dp))
                                Text("Notifications")
                                Button(
                                    style = TextButtonStyle(shape = RoundedCornerShape(10.dp)),
                                    modifier = LayoutPadding(5.dp), text = "Messaging"
                                )
                                Button(
                                    style = TextButtonStyle(shape = RoundedCornerShape(10.dp)),
                                    modifier = LayoutPadding(5.dp), text = "External"
                                )

                                Spacer(modifier = LayoutHeight(15.dp))
                                Button(
                                    style = TextButtonStyle(shape = RoundedCornerShape(10.dp)),
                                    text = "My Connections"
                                )
                            }
                        }
                    }
                }
            }
        },
        topAppBar = { TopBar(callback = { scaffoldState.drawerState = DrawerState.Opened }) },
        floatingActionButtonPosition = Scaffold.FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = { /* fab click handler */ }) {
                Text("DO")
            }
        },
        bodyContent = { modifier ->
            VerticalScroller(modifier = LayoutHeight.Fill) {
                Padding(padding = 20.dp) {
                    Column {
                        Text(text = "You're logged in...welcome ${AWSMobileClient.getInstance()?.username}")

                        Spacer(modifier = LayoutHeight(20.dp))

                        Button(modifier = LayoutWidth.Fill, text = "Sign-out", style = ButtonStyle(
                            elevation = 3.dp,
                            backgroundColor = colors.primary, contentColor = Color.White,
                            shape = RoundedCornerShape(3.dp)
                        ),
                            onClick = {
                                CognitoAuthService.signOut {
                                    topLevelNavigation(
                                        AvailableTopLevelScreens.AUTH
                                    )
                                }
                            })
                    }
                }
            }
        }
    )
}

@Composable
fun TopBar(callback: () -> Unit) {
    val imgHamburger = imageResource(R.drawable.img_menu_hamburger)

    TopAppBar(
        title = { Text("Simple Scaffold Screen") },
        navigationIcon = {
            AppBarIcon(imgHamburger, onClick = {
                callback()
            })
        }
    )
}

@Composable
private fun BottomBar() {
    val context = ambient(ContextAmbient)
    Surface(elevation = 2.dp) {
        Container(height = 50.dp) {
            Row(modifier = LayoutWidth.Fill) {
                BottomBarAction(R.drawable.ic_share_24) {
                    sharePost(context)
                }
                Container { }
                BottomBarAction(R.drawable.ic_share_24) {

                }
            }
        }

    }
}

@Composable
private fun BottomBarAction(
    @DrawableRes id: Int,
    onClick: () -> Unit
) {
    Ripple(
        bounded = false,
        radius = 24.dp
    ) {
        Clickable(onClick = onClick) {
            Container(modifier = LayoutPadding(12.dp)) {
                DrawVector(vectorResource(id))
            }
        }
    }
}

private fun sharePost(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, "title")
        putExtra(Intent.EXTRA_TEXT, "url")
    }
    context.startActivity(Intent.createChooser(intent, "Share post"))
}