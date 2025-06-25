package com.example.expensetracker.data.repositoryimp

import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.domain.ExpenseRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import android.util.Log

class ExpenseRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ExpenseRepository
{
    override suspend fun addExpense(expense: Expense) {
        val docRef= firestore.collection("expense").document()
        firestore.collection("expense").document(docRef.id).set(expense.copy(userId = docRef.id))
    }

    override suspend fun getExpense(userId: String): List<Expense> {
        return try {
            var snapshot = firestore.collection("expense")
               // .whereEqualTo("userId",userId)
                .get()
                .await()
            Log.d("NotError","there isnot ")
            Log.d("checkuserId","fetching expenses for user id $userId")
            snapshot.documents.mapNotNull { it.toObject(Expense::class.java) }
        }catch (e: Exception){
            Log.d("FirebaseError","Fetch not from firebase")
            emptyList()
        }


    }

}