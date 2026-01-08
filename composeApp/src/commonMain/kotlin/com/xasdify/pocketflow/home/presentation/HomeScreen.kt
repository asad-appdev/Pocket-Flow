package com.xasdify.pocketflow.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeScreenComponent
import com.xasdify.pocketflow.core.presentation.navigation.main.MainRoute
import com.xasdify.pocketflow.ui.components.*
import com.xasdify.pocketflow.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeScreenComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Welcome Back!",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Pocket Flow",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { component.onNavigateTo(MainRoute.Profile) }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { component.onNavigateTo(MainRoute.Transactions) },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Transaction") },
                containerColor = MaterialTheme.colorScheme.primary
            )
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

            // Total Balance Card
            item {
                MoneyCard(
                    modifier = Modifier.fillMaxWidth(),
                    useGradient = true,
                    gradientColors = listOf(GradientStart, GradientEnd)
                ) {
                    Text(
                        text = "Total Balance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$12,450.00",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.TrendingUp,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Income",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                            Text(
                                text = "$8,200.00",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.TrendingDown,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Expenses",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                            Text(
                                text = "$3,750.00",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Quick Stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        label = "This Month",
                        value = "$4,450",
                        icon = Icons.Default.CalendarMonth,
                        iconBackgroundColor = IncomeGreen.copy(alpha = 0.1f),
                        iconTint = IncomeGreen,
                        trend = "+12.5%",
                        trendUp = true,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = "Budget Left",
                        value = "$1,250",
                        icon = Icons.Default.Wallet,
                        iconBackgroundColor = BudgetBlue.copy(alpha = 0.1f),
                        iconTint = BudgetBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Actions
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = "Transactions",
                        icon = Icons.Default.SwapHoriz,
                        color = md_theme_light_primary,
                        onClick = { component.onNavigateTo(MainRoute.Transactions) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = "Budget",
                        icon = Icons.Default.PieChart,
                        color = BudgetBlue,
                        onClick = { component.onNavigateTo(MainRoute.Finance) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = "Analytics",
                        icon = Icons.Default.BarChart,
                        color = GoalPurple,
                        onClick = { component.onNavigateTo(MainRoute.Analytics) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = "Settings",
                        icon = Icons.Default.Settings,
                        color = DebtOrange,
                        onClick = { component.onNavigateTo(MainRoute.Profile) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Recent Transactions
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { component.onNavigateTo(MainRoute.Transactions) }) {
                        Text("See All")
                    }
                }
            }

            // Sample Transactions
            items(getSampleTransactions()) { transaction ->
                TransactionItem(
                    title = transaction.title,
                    category = transaction.category,
                    amount = transaction.amount,
                    date = transaction.date,
                    isIncome = transaction.isIncome,
                    icon = transaction.icon,
                    onClick = { component.onNavigateTo(MainRoute.Transactions) }
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

// Sample data
private data class SampleTransaction(
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val isIncome: Boolean,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private fun getSampleTransactions() = listOf(
    SampleTransaction(
        title = "Salary",
        category = "Income",
        amount = "5,000.00",
        date = "Today",
        isIncome = true,
        icon = Icons.Default.AccountBalance
    ),
    SampleTransaction(
        title = "Grocery Shopping",
        category = "Food & Dining",
        amount = "125.50",
        date = "Yesterday",
        isIncome = false,
        icon = Icons.Default.ShoppingCart
    ),
    SampleTransaction(
        title = "Freelance Project",
        category = "Income",
        amount = "1,200.00",
        date = "Jan 5",
        isIncome = true,
        icon = Icons.Default.Work
    ),
    SampleTransaction(
        title = "Electric Bill",
        category = "Utilities",
        amount = "85.00",
        date = "Jan 4",
        isIncome = false,
        icon = Icons.Default.Bolt
    ),
    SampleTransaction(
        title = "Coffee",
        category = "Food & Dining",
        amount = "12.50",
        date = "Jan 3",
        isIncome = false,
        icon = Icons.Default.LocalCafe
    )
)