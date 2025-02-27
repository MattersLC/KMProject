package org.miniabastos.kmproject.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.koin.core.parameter.parametersOf
import org.miniabastos.kmproject.getColorsTheme
import org.miniabastos.kmproject.presentacion.ExpensesViewModel
import org.miniabastos.kmproject.ui.ExpensesDetailScreen
import org.miniabastos.kmproject.ui.ExpensesScreen

@Composable
fun Navigation(navigator: Navigator) {
    val colors = getColorsTheme()
    val viewModel = koinViewModel(ExpensesViewModel::class) { parametersOf() }

    NavHost(
        modifier = Modifier.background(colors.background),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(uiState = uiState, onExpenseClick = { expense ->
                navigator.navigate("/addExpenses/${expense.id}")
            }, onDeleteExpense = { expenseToDelete ->
                viewModel.deleteExpense(expenseToDelete.id)
            })
        }

        scene(route = "/addExpenses/{id}?") { backStackEntry ->
            val idFromPath = backStackEntry.path<Long>("id")
            val expenseToEditOrAdd = idFromPath?.let { id -> viewModel.getExpenseWithId(id) }

            ExpensesDetailScreen(
                expenseToEdit = expenseToEditOrAdd,
                categoryList = viewModel.getCategories()
            ) { expense ->
                if (expenseToEditOrAdd == null) {
                    viewModel.addExpense(expense)
                } else {
                    viewModel.editExpense(expense)
                }

                navigator.popBackStack()
            }
        }
    }
}