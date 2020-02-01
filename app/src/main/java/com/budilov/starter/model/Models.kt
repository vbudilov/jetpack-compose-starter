package com.budilov.starter.model

import androidx.compose.Model

@Model
data class AuthStatus(
    var status: MobileUserState = MobileUserState.SIGNED_OUT,
    var message: String? = null
) {

    enum class MobileUserState {
        SIGNED_IN, GUEST, SIGNED_OUT_FEDERATED_TOKENS_INVALID, SIGNED_OUT_USER_POOLS_TOKENS_INVALID, SIGNED_OUT, UNKNOWN
    }
}

@Model
data class Navigation(
    val currentScreen: String? = null,
    val history: List<String> = emptyList()
)