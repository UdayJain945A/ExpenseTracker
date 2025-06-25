package com.example.expensetracker.domain.use_case

import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.domain.ExpenseRepository
import javax.inject.Inject

class GetExpenseUsecase @Inject constructor(
    private val repository: ExpenseRepository
) {

    suspend fun invoke(userId: String) : List<Expense>{
        return repository.getExpense(userId)
    }

}