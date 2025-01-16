package com.example.testwithpoetry.ui.main

sealed class Routes(
    val route: String
) {
    data object WelcomeScreen : Routes(route = "/")
    data object TabsScreen : Routes(route = "/Home")
}

enum class TabsNav{
    Poetry, Account
}