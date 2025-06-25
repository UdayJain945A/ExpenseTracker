package com.example.expensetracker.data.model

import com.google.firebase.Timestamp

data class Expense(
 //   var id: String,
    var userId : String="",
    var amount: Double=0.0,
    var category: String="",
    var note : String="",
    var date:String=""
)
