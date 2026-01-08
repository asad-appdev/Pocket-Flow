package com.xasdify.pocketflow.financialPlanning.presentation.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.core.presentation.navigation.finance.BudgetScreenComponent
import com.xasdify.pocketflow.ui.components.*
import com.xasdify.pocketflow.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(component: BudgetScreenComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Budget Management",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { component.onAddBudgetClicked() },
                containerColor = BudgetBlue
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Budget")
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

            // Monthly Budget Overview and Stats...
            // (Keeping existing UI structure for Overview and Stats)
            
            // Monthly Budget Overview
            item {
                MoneyCard(
                    modifier = Modifier.fillMaxWidth(),
                    useGradient = true,
                    gradientColors = listOf(BudgetBlue, BudgetBlueDark)
                ) {
                    Text(
                        text = "Monthly Budget",
                        style = MaterialTheme.typography.titleMedium,
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$5,000.00",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LinearProgressIndicator(
                        progress = { 0.75f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = androidx.compose.ui.graphics.Color.White,
                        trackColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.3f),
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Spent",
                                style = MaterialTheme.typography.bodySmall,
                                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "$3,750.00",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                        Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                            Text(
                                text = "Remaining",
                                style = MaterialTheme.typography.bodySmall,
                                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "$1,250.00",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }
                }
            }

            // Budget Stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        label = "Categories",
                        value = "8",
                        icon = Icons.Default.Category,
                        iconBackgroundColor = BudgetBlue.copy(alpha = 0.1f),
                        iconTint = BudgetBlue,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Over Budget",
                        value = "2",
                        icon = Icons.Default.Warning,
                        iconBackgroundColor = ExpenseRed.copy(alpha = 0.1f),
                        iconTint = ExpenseRed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Category Budgets
            item {
                Text(
                    text = "Category Budgets",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(getSampleBudgets()) { budget ->
                ProgressCard(
                    title = budget.category,
                    currentAmount = budget.spent,
                    targetAmount = budget.budget,
                    currencySymbol = "$",
                    progressColor = if (budget.spent > budget.budget) ExpenseRed else BudgetBlue,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// Sample budget data
private data class BudgetItem(
    val category: String,
    val budget: Double,
    val spent: Double
)

private fun getSampleBudgets() = listOf(
    BudgetItem("Food & Dining", 800.0, 650.0),
    BudgetItem("Transportation", 300.0, 280.0),
    BudgetItem("Shopping", 500.0, 620.0),
    BudgetItem("Entertainment", 200.0, 150.0),
    BudgetItem("Utilities", 400.0, 450.0),
    BudgetItem("Healthcare", 300.0, 180.0),
    BudgetItem("Education", 600.0, 420.0),
    BudgetItem("Other", 400.0, 200.0)
)