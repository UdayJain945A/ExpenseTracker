package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.presentation.screen.HomeScreen
import com.example.expensetracker.presentation.screen.LoginScreen
import com.example.expensetracker.presentation.screen.RegisterScreen
import com.example.expensetracker.presentation.util.Screen

import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var navcontroller = rememberNavController()
            NavHost(navcontroller, startDestination = Screen.LoginScreen.route ){
              composable(Screen.LoginScreen.route) {
                  LoginScreen(navcontroller)
              }
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.SignUp.route) {
                    RegisterScreen(navcontroller)
                }
            }
          //  HomeScreen()

        }
    }
}



