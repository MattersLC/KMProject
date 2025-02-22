package org.miniabastos.kmproject.domain

import org.miniabastos.kmproject.models.Expense
import org.miniabastos.kmproject.models.ExpenseCategory

interface ExpenseRepository {
    fun getAllExpenses(): List<Expense>
    fun addExpense(expense: Expense)
    fun editExpense(expense: Expense)
    fun deleteExpense(expense: Expense): List<Expense>
    fun getCategories(): List<ExpenseCategory>
}