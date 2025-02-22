package org.miniabastos.kmproject.data

import org.miniabastos.kmproject.domain.ExpenseRepository
import org.miniabastos.kmproject.models.Expense
import org.miniabastos.kmproject.models.ExpenseCategory

class ExpenseRepoImplement(private val expenseManager: ExpenseManager): ExpenseRepository {
    override fun getAllExpenses(): List<Expense> {
        return expenseManager.fakeExpenseList
    }

    override fun addExpense(expense: Expense) {
        expenseManager.addNewExpense(expense)
    }

    override fun editExpense(expense: Expense) {
        expenseManager.editExpense(expense)
    }

    override fun deleteExpense(expense: Expense): List<Expense> {
        TODO("Not yet implemented")
    }

    override fun getCategories(): List<ExpenseCategory> {
        return expenseManager.getCategories()
    }
}