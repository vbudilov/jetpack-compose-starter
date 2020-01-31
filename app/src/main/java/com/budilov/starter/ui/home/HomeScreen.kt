package com.budilov.starter.ui.home

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.*
import androidx.ui.material.TopAppBar
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.budilov.starter.R

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Column {
        TopBar()
        VerticalScroller(modifier = LayoutHeight.Fill) {
            Column {
                Text(text = "hello")
                Text(text = "hello")
                Text(text = "hello")
                Text(text = "hello")
                Text(text = "hello")

            }
        }
        BottomBar()
    }
}

@Composable
private fun TopBar() {
    Column {
        TopAppBar(
            title = { Text(text = "JetPack Starter") },
            navigationIcon = {

            }
        )

    }
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