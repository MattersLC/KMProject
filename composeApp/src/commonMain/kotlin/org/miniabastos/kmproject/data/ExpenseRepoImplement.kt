package org.miniabastos.kmproject.data

import com.expenseApp.db.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.miniabastos.kmproject.domain.ExpenseRepository
import org.miniabastos.kmproject.models.Expense
import org.miniabastos.kmproject.models.ExpenseCategory
import org.miniabastos.kmproject.models.NetworkExpense

private const val BASE_URL = "http://172.16.3.172:8080"

class ExpenseRepoImplement(
    private val appDatabase: AppDatabase,
    private val httpClient: HttpClient
) : ExpenseRepository {

    private val queries = appDatabase.expenseAppQueries

    override suspend fun getAllExpenses(): List<Expense> {
        return if (queries.selectAll().executeAsList().isEmpty()) {
            val networkResponse = httpClient.get("$BASE_URL/expenses").body<List<NetworkExpense>>()
            if (networkResponse.isEmpty()) return emptyList()

            val expenses = networkResponse.map { networkExpense ->
                Expense(
                    id = networkExpense.id,
                    amount = networkExpense.amount,
                    category = ExpenseCategory.valueOf(networkExpense.categoryName),
                    description = networkExpense.description
                )
            }
            expenses.forEach {
                queries.insert(it.id, it.amount, it.category.name, it.description)
            }
            expenses
        } else {
            queries.selectAll().executeAsList().map {
                Expense(
                    id = it.id,
                    amount = it.amount,
                    category = ExpenseCategory.valueOf(it.categoryName),
                    description = it.description
                )
            }
        }
    }

    override suspend fun addExpense(expense: Expense) {
        val networkResponse: NetworkExpense = httpClient.post("$BASE_URL/expenses") {
            contentType(ContentType.Application.Json)
            setBody(NetworkExpense(
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            ))
        }.body()

        val newExpense = Expense(
            id = networkResponse.id,
            amount = networkResponse.amount,
            category = ExpenseCategory.valueOf(networkResponse.categoryName),
            description = networkResponse.description
        )

        queries.transaction {
            queries.insert(
                id = newExpense.id,
                amount = newExpense.amount,
                categoryName = newExpense.category.name,
                description = newExpense.description
            )
        }
    }

    override suspend fun editExpense(expense: Expense) {
        queries.transaction {
            queries.update(
                id = expense.id,
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            )
        }
        httpClient.post("$BASE_URL/expenses/${expense.id}") {
            contentType(ContentType.Application.Json)
            setBody(NetworkExpense(
                id = expense.id,
                amount = expense.amount,
                categoryName = expense.category.name,
                description = expense.description
            ))
        }
    }

    override suspend fun deleteExpense(id: Long) {
        httpClient.delete("$BASE_URL/expenses/${id}")
        queries.transaction {
            queries.delete( id = id )
        }
    }

    override fun getCategories(): List<ExpenseCategory> {
        return queries.categories().executeAsList().map {
            ExpenseCategory.valueOf(it)
        }
    }
}