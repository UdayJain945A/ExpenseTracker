package com.example.expensetracker.domain.use_case

import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.domain.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
)
{
    suspend fun invoke(expense: Expense){
        repository.addExpense(expense)
    }

}