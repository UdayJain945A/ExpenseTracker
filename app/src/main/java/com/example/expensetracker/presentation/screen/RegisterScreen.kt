package com.example.expensetracker.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetracker.presentation.util.Screen
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel
import kotlin.math.exp


@Composable
fun RegisterScreen(
    navController : NavController,
    expenseViewModel: ExpenseViewModel = hiltViewModel()
){
    val context = LocalContext.current
    LaunchedEffect(true) {
        expenseViewModel.toastmessage.collect {message->
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }
    var email1: String by remember {
        mutableStateOf("")
    }
    var password1 : String by  remember {
        mutableStateOf("")
    }
    var confirm : String by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Sign Up", style =
            TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = "$email1",
            onValueChange = {
                email1 =it
            }
            , label = {
                Text("Email", style = TextStyle(
                    fontWeight = FontWeight.Bold
                ))

            },
            placeholder = {
                Text("Enter your Email")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = "$password1",
            onValueChange = {
                password1 = it
            },
            label = {Text("Password", style = TextStyle(
                fontWeight = FontWeight.Bold
            ))},
            placeholder = {
                Text("Enter the Password")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = "$confirm",
            onValueChange = {
                confirm = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Confirm Password", style = TextStyle(
                    fontWeight = FontWeight.Bold
                ))
            },
            placeholder = {
                Text("Enter your password")
            },
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                expenseViewModel.register(
                    email1,password1,confirm,
                    {
                        navController.navigate(Screen.Home.route)
                    }
                )

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0061F2)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign Up")
        }
    }
}