package com.xasdify.pocketflow.financialPlanning.presentation.debt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.core.presentation.navigation.finance.DebtScreenComponent
import com.xasdify.pocketflow.ui.components.*
import com.xasdify.pocketflow.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtScreen(component: DebtScreenComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Debt Management",
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
                onClick = { /* TODO: Add debt dialog */ },
                containerColor = DebtOrange
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Debt")
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

            // Total Debt Overview
            item {
                MoneyCard(
                    modifier = Modifier.fillMaxWidth(),
                    useGradient = true,
                    gradientColors = listOf(DebtOrange, DebtOrangeDark)
                ) {
                    Text(
                        text = "Total Debt",
                        style = MaterialTheme.typography.titleMedium,
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$18,500.00",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Monthly Payment",
                                style = MaterialTheme.typography.bodySmall,
                                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "$850.00",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                        Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                            Text(
                                text = "Interest Rate",
                                style = MaterialTheme.typography.bodySmall,
                                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "4.5%",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = androidx.compose.ui.graphics.Color.White
                            )
                        }
                    }
                }
            }

            // Debt Stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        label = "Active Debts",
                        value = "3",
                        icon = Icons.Default.CreditCard,
                        iconBackgroundColor = DebtOrange.copy(alpha = 0.1f),
                        iconTint = DebtOrange,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Paid Off",
                        value = "2",
                        icon = Icons.Default.CheckCircle,
                        iconBackgroundColor = IncomeGreen.copy(alpha = 0.1f),
                        iconTint = IncomeGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Active Debts
            item {
                Text(
                    text = "Active Debts",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(getSampleDebts()) { debt ->
                ProgressCard(
                    title = debt.name,
                    currentAmount = debt.totalAmount - debt.remaining,
                    targetAmount = debt.totalAmount,
                    currencySymbol = "$",
                    progressColor = DebtOrange,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

// Sample debt data
private data class Debt(
    val name: String,
    val totalAmount: Double,
    val remaining: Double
)

private fun getSampleDebts() = listOf(
    Debt("Car Loan", 15000.0, 8500.0),
    Debt("Credit Card", 5000.0, 3200.0),
    Debt("Student Loan", 25000.0, 6800.0)
)