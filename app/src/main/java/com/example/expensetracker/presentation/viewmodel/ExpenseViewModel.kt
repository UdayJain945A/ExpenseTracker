package com.example.expensetracker.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Paragraph
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.domain.use_case.ExpenseUseCase
import com.example.expensetracker.domain.use_case.GetExpenseUsecase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.exp

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseUsecase: ExpenseUseCase,
    private val auth: FirebaseAuth
 ) : ViewModel()
{
     private var _toastmessage = MutableSharedFlow<String>()
    var toastmessage = _toastmessage
    var expense = mutableStateListOf<Expense>()
        private set

    var isloggin = mutableStateOf(false)
        private set

    init {
        isloggin.value = auth.currentUser !=null
        if(isloggin.value) loadexpense()
    }

    fun login(email:String,password:String,onSuccess : ()->Unit)
    {
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                isloggin.value = true
                Log.d("userId","${auth.currentUser?.uid}")
                loadexpense()
                onSuccess()
            }
            .addOnFailureListener {
              Log.d("uday","error")
            }
    }
     fun register(email:String, password:String, confirm:String, onSuccess1 : ()-> Unit)
    {
//        if(email.isBlank() == true || password.isBlank() == true || confirm.isBlank()==true){
//            viewModelScope.launch {
//                _toastmessage.emit("Filed is empty")
//            }
//        }
//        else if(password != confirm) {
//            viewModelScope.launch {
//                _toastmessage.emit("Password is not correct ")
//            }
//        }
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            isloggin.value = true
            onSuccess1()
        }
            .addOnFailureListener {
                Log.d("uday1","error in register")
            }
    }

    fun addexpense(category: String,amount: Double,data:String,note: String){
        var userId=auth.currentUser?.uid ?:return
        var expense = Expense(userId,amount,category, note,data)
        viewModelScope.launch {
            expenseUsecase.addExpenseUseCase.invoke(expense)
            loadexpense()
        }
    }
    fun loadexpense() {
        var userId= auth.currentUser?.uid?:return
        viewModelScope.launch {
            var expen= expenseUsecase.getExpenseUsecase.invoke("RgWnvfnzkeBqyQKTM6Xn")
            Log.d("data","$expen")
            expense.clear()
            expense.addAll(expen)
        }
    }

}