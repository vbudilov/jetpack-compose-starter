package com.budilov.starter.model

import androidx.compose.Model

@Model
data class AuthStatus(val loggedIn: Boolean = false)

@Model
data class Navigation(val currentScreen: String? = null, val history: List<String> = emptyList())