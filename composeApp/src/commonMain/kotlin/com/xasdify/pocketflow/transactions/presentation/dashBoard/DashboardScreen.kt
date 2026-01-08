package com.xasdify.pocketflow.transactions.presentation.dashBoard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionDashBoardScreenComponent
import com.xasdify.pocketflow.ui.components.*
import com.xasdify.pocketflow.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(component: TransactionDashBoardScreenComponent) {
    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Transactions",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallFloatingActionButton(
                    onClick = { component.onNavigateToIncome() },
                    containerColor = IncomeGreen
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Income")
                }
                SmallFloatingActionButton(
                    onClick = { component.onNavigateToExpense() },
                    containerColor = ExpenseRed
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Add Expense")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Summary Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        label = "Total Income",
                        value = "$8,200",
                        icon = Icons.Default.TrendingUp,
                        iconBackgroundColor = IncomeGreen.copy(alpha = 0.1f),
                        iconTint = IncomeGreen,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Total Expenses",
                        value = "$3,750",
                        icon = Icons.Default.TrendingDown,
                        iconBackgroundColor = ExpenseRed.copy(alpha = 0.1f),
                        iconTint = ExpenseRed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search transactions...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            // Filter Chips
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Income", "Expense").forEach { filter ->
                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            label = { Text(filter) },
                            leadingIcon = if (selectedFilter == filter) {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                            } else null
                        )
                    }
                }
            }

            // Date Header
            item {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Transactions List
            items(getFilteredTransactions(selectedFilter)) { transaction ->
                TransactionItem(
                    title = transaction.title,
                    category = transaction.category,
                    amount = transaction.amount,
                    date = transaction.date,
                    isIncome = transaction.isIncome,
                    icon = transaction.icon,
                    onClick = { /* TODO: Navigate to transaction details */ }
                )
            }

            // Empty State
            if (getFilteredTransactions(selectedFilter).isEmpty()) {
                item {
                    EmptyState(
                        icon = Icons.Default.Receipt,
                        title = "No Transactions",
                        description = "Start tracking your finances by adding your first transaction",
                        action = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { component.onNavigateToIncome() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = IncomeGreen
                                    )
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Income")
                                }
                                Button(
                                    onClick = { component.onNavigateToExpense() },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ExpenseRed
                                    )
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Add Expense")
                                }
                            }
                        }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// Sample transaction data
private data class TransactionData(
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val isIncome: Boolean,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private fun getAllTransactions() = listOf(
    TransactionData("Salary", "Income", "5,000.00", "Jan 7", true, Icons.Default.AccountBalance),
    TransactionData("Grocery Shopping", "Food & Dining", "125.50", "Jan 6", false, Icons.Default.ShoppingCart),
    TransactionData("Freelance Project", "Income", "1,200.00", "Jan 5", true, Icons.Default.Work),
    TransactionData("Electric Bill", "Utilities", "85.00", "Jan 4", false, Icons.Default.Bolt),
    TransactionData("Coffee", "Food & Dining", "12.50", "Jan 3", false, Icons.Default.LocalCafe),
    TransactionData("Investment Return", "Income", "2,000.00", "Jan 2", true, Icons.Default.TrendingUp),
    TransactionData("Gas", "Transportation", "60.00", "Jan 1", false, Icons.Default.LocalGasStation),
    TransactionData("Restaurant", "Food & Dining", "85.00", "Dec 31", false, Icons.Default.Restaurant),
    TransactionData("Bonus", "Income", "1,000.00", "Dec 30", true, Icons.Default.CardGiftcard),
    TransactionData("Phone Bill", "Utilities", "45.00", "Dec 29", false, Icons.Default.Phone)
)

private fun getFilteredTransactions(filter: String): List<TransactionData> {
    return when (filter) {
        "Income" -> getAllTransactions().filter { it.isIncome }
        "Expense" -> getAllTransactions().filter { !it.isIncome }
        else -> getAllTransactions()
    }
}