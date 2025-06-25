package com.example.expensetracker.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.presentation.util.Screen
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel

//@Preview(showBackground = true)
@Composable
fun LoginScreen(
    navController: NavController,
    expenseViewModel: ExpenseViewModel = hiltViewModel()
){
    var email: String by remember {
        mutableStateOf("")
    }
    var password : String by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Expenset.",
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0061F2)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Login",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = "$email",
            onValueChange = { it ->
                email = it
            },
            label = { Text("Email") },
            placeholder = { Text("Enter your Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

//            var password by remember {
//                mutableStateOf(false)
//            }
        OutlinedTextField(
            value = "$password",
            onValueChange = { it ->
                password = it
            },
            label = { Text("Password") },
            placeholder = { Text("***********") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Forgot password",
            modifier = Modifier
                .align(Alignment.End)
                .clickable {

                },
            color = Color(0xFF0061F2),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                expenseViewModel.login(email,password,{
                    navController.navigate(Screen.Home.route)
                })

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0061F2)
            )
        ) {
            Text(text = "Login", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Or Login with")

        Spacer(modifier = Modifier.height(16.dp))

        socialloginscreen("Continue with Google", painterResource(R.drawable.img), {

        })
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Text("Did not have an account? ")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Sign Up", color = Color(0xFF0061F2),
                modifier = Modifier.clickable{
                  navController.navigate(Screen.SignUp.route)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun socialloginscreen(
    text: String, icon: Painter, Onclick: () -> Unit
) {
    OutlinedButton(
        onClick = Onclick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Icon(painter = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color(0xFF0061F2))
    }
}