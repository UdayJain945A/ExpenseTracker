package com.example.expensetracker.di

import com.example.expensetracker.data.repositoryimp.ExpenseRepoImpl
import com.example.expensetracker.domain.ExpenseRepository
import com.example.expensetracker.domain.use_case.AddExpenseUseCase
import com.example.expensetracker.domain.use_case.ExpenseUseCase
import com.example.expensetracker.domain.use_case.GetExpenseUsecase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providefirebaseauth() : FirebaseAuth{
        var auth= FirebaseAuth.getInstance()
        return auth
    }

    @Singleton
    @Provides
    fun providefirebasefirestore() : FirebaseFirestore
    {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideRepositoryImp(firestore: FirebaseFirestore) : ExpenseRepository{
       return ExpenseRepoImpl(firestore)
    }

    @Singleton
    @Provides
    fun provideUseCase(repository: ExpenseRepository) : ExpenseUseCase{
        return ExpenseUseCase(
            AddExpenseUseCase(repository),
            GetExpenseUsecase(repository)
        )
    }



}