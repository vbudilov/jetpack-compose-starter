package com.budilov.starter

import androidx.compose.Model

@Model
data class Navigation(
    val currentScreen: String? = null,
    val history: List<String> = emptyList()
)