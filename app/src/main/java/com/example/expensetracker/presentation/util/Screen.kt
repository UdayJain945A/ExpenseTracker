package com.example.expensetracker.presentation.util

sealed class Screen(var route :String) {
    object LoginScreen : Screen("Login")
    object SignUp : Screen("SignUp")
    object Home : Screen("Home")
}