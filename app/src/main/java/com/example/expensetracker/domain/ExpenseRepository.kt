package com.example.expensetracker.domain

import com.example.expensetracker.data.model.Expense

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense)
    suspend fun getExpense(userID: String) : List<Expense>
}