package org.miniabastos.kmproject.presentacion

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.miniabastos.kmproject.domain.ExpenseRepository
import org.miniabastos.kmproject.models.Expense
import org.miniabastos.kmproject.models.ExpenseCategory

sealed class ExpensesUiState {
    object Loading: ExpensesUiState()
    data class Success(val expenses: List<Expense>, val total: Double): ExpensesUiState()
    data class Error(val message: String): ExpensesUiState()
}

class ExpensesViewModel(private val repo: ExpenseRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getExpensesList()
    }

    private fun getExpensesList() {
        viewModelScope.launch {
            try {
                val expenses = repo.getAllExpenses()
                _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
            } catch (e: Exception) {
                _uiState.value = ExpensesUiState.Error(e.message ?: "Ocurrió un error")
            }
        }
    }

    private suspend fun updateExpensesList() {
        try {
            val expenses = repo.getAllExpenses()
            _uiState.value = ExpensesUiState.Success(expenses, expenses.sumOf { it.amount })
        } catch (e: Exception) {
            _uiState.value = ExpensesUiState.Error(e.message ?: "Ocurrió un error")
        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.addExpense(expense)
                updateExpensesList()
            } catch (e: Exception) {
                _uiState.value = ExpensesUiState.Error(e.message ?: "Ocurrió un error")
            }
        }
    }

    fun editExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                repo.editExpense(expense)
                updateExpensesList()
            } catch (e: Exception) {
                _uiState.value = ExpensesUiState.Error(e.message ?: "Ocurrió un error")
            }
        }
    }

    fun deleteExpense(id: Long) {
        viewModelScope.launch {
            try {
                repo.deleteExpense(id)
                updateExpensesList()
            } catch (e: Exception) {
                _uiState.value = ExpensesUiState.Error(e.message ?: "Ocurrió un error")
            }
        }
    }

    fun getExpenseWithId(id: Long): Expense? {
        return (_uiState.value as? ExpensesUiState.Success)?.expenses?.firstOrNull { it.id == id }
    }

    fun getCategories(): List<ExpenseCategory> {
        return repo.getCategories()
    }
}