package org.miniabastos.kmproject.domain

import org.miniabastos.kmproject.models.Expense
import org.miniabastos.kmproject.models.ExpenseCategory

interface ExpenseRepository {
    suspend fun getAllExpenses(): List<Expense>
    suspend fun addExpense(expense: Expense)
    suspend fun editExpense(expense: Expense)
    suspend fun deleteExpense(id: Long)
    fun getCategories(): List<ExpenseCategory>
}