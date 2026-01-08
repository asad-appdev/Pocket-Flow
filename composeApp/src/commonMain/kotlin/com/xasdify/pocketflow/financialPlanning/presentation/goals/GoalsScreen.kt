package com.xasdify.pocketflow.financialPlanning.presentation.goals

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
import com.xasdify.pocketflow.core.presentation.navigation.finance.GoalScreenComponent
import com.xasdify.pocketflow.ui.components.*
import com.xasdify.pocketflow.ui.theme.*
import com.xasdify.pocketflow.utils.formatCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(component: GoalScreenComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Financial Goals",
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
                onClick = { /* TODO: Add goal dialog */ },
                containerColor = GoalPurple
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Goal")
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

            // Goals Overview
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        label = "Active Goals",
                        value = "5",
                        icon = Icons.Default.Flag,
                        iconBackgroundColor = GoalPurple.copy(alpha = 0.1f),
                        iconTint = GoalPurple,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Completed",
                        value = "3",
                        icon = Icons.Default.CheckCircle,
                        iconBackgroundColor = IncomeGreen.copy(alpha = 0.1f),
                        iconTint = IncomeGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Active Goals
            item {
                Text(
                    text = "Active Goals",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(getSampleGoals()) { goal ->
                GoalCard(
                    goal = goal,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun GoalCard(
    goal: Goal,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = goal.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = goal.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = goal.icon,
                    contentDescription = null,
                    tint = GoalPurple,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val progress = (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0).toFloat()
            
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = GoalPurple,
                trackColor = GoalPurple.copy(alpha = 0.2f),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Saved",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${ goal.currentAmount.formatCurrency()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GoalPurple
                    )
                }

                Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                    Text(
                        text = "Target",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${goal.targetAmount.formatCurrency()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (goal.deadline.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Deadline: ${goal.deadline}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Sample goal data
private data class Goal(
    val name: String,
    val category: String,
    val currentAmount: Double,
    val targetAmount: Double,
    val deadline: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private fun getSampleGoals() = listOf(
    Goal(
        name = "Emergency Fund",
        category = "Savings",
        currentAmount = 8500.0,
        targetAmount = 10000.0,
        deadline = "Dec 2026",
        icon = Icons.Default.Shield
    ),
    Goal(
        name = "New Car",
        category = "Purchase",
        currentAmount = 12000.0,
        targetAmount = 25000.0,
        deadline = "Jun 2027",
        icon = Icons.Default.DirectionsCar
    ),
    Goal(
        name = "Vacation Fund",
        category = "Travel",
        currentAmount = 2500.0,
        targetAmount = 5000.0,
        deadline = "Aug 2026",
        icon = Icons.Default.Flight
    ),
    Goal(
        name = "Home Down Payment",
        category = "Purchase",
        currentAmount = 35000.0,
        targetAmount = 100000.0,
        deadline = "Dec 2028",
        icon = Icons.Default.Home
    ),
    Goal(
        name = "Investment Portfolio",
        category = "Investment",
        currentAmount = 15000.0,
        targetAmount = 50000.0,
        deadline = "",
        icon = Icons.Default.TrendingUp
    )
)

