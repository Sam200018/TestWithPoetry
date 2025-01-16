package com.example.testwithpoetry.ui.main.interactions

import com.example.testwithpoetry.localModels.User

data class UserInfoState(
    val user: User? = null,
    val message: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false
)
