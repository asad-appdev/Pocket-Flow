package com.xasdify.pocketflow.loans.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.loans.data.repository.LoanRepositoryImpl
import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.ui.components.EmptyState
import com.xasdify.pocketflow.ui.components.LoanCard
import com.xasdify.pocketflow.ui.components.MoneyCard
import com.xasdify.pocketflow.ui.theme.DebtOrange
import com.xasdify.pocketflow.ui.theme.DebtOrangeDark
import com.xasdify.pocketflow.ui.theme.IncomeGreen
import com.xasdify.pocketflow.ui.theme.IncomeGreenDark
import com.xasdify.pocketflow.utils.formatCurrency
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansListScreen(
    onNavigateToAddLoan: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onNavigateToLoanDetail: (Long) -> Unit = {}
) {
    val repository: LoanRepositoryImpl = koinInject()
    var selectedTab by remember { mutableStateOf(0) }
    var statusFilter by remember { mutableStateOf("All") }

    val allLoans by repository.getAllLoans().collectAsState(initial = emptyList())

    val takenLoans = allLoans.filter { it.type == LoanType.TAKEN.name }
    val givenLoans = allLoans.filter { it.type == LoanType.GIVEN.name }

    val currentLoans = if (selectedTab == 0) takenLoans else givenLoans
    val filteredLoans = when (statusFilter) {
        "Active" -> currentLoans.filter { it.status == LoanStatus.ACTIVE.name }
        "Closed" -> currentLoans.filter { it.status == LoanStatus.CLOSED.name }
        else -> currentLoans
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Loans",
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
                onClick = onNavigateToAddLoan,
                containerColor = if (selectedTab == 0) DebtOrange else IncomeGreen
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Loan")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Taken") },
                    icon = { Icon(Icons.Default.TrendingDown, contentDescription = null) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Given") },
                    icon = { Icon(Icons.Default.TrendingUp, contentDescription = null) }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }

                // Summary Card
                item {
                    LoanSummaryCard(
                        loans = currentLoans,
                        type = if (selectedTab == 0) LoanType.TAKEN else LoanType.GIVEN
                    )
                }

                // Filter Chips
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("All", "Active", "Closed").forEach { filter ->
                            FilterChip(
                                selected = statusFilter == filter,
                                onClick = { statusFilter = filter },
                                label = { Text(filter) },
                                leadingIcon = if (statusFilter == filter) {
                                    {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                } else null
                            )
                        }
                    }
                }

                // Loans List
                if (filteredLoans.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Default.AccountBalance,
                            title = "No Loans",
                            description = if (selectedTab == 0)
                                "You haven't borrowed any money yet"
                            else
                                "You haven't lent any money yet",
                            action = {
                                Button(onClick = onNavigateToAddLoan) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Add Loan")
                                }
                            }
                        )
                    }
                } else {
                    items(filteredLoans) { loan ->
                        LoanCard(
                            loan = loan,
                            onClick = { onNavigateToLoanDetail(loan.id) }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
private fun LoanSummaryCard(
    loans: List<Loan>,
    type: LoanType
) {
    val activeLoans = loans.filter { it.status == LoanStatus.ACTIVE.name }
    val totalPrincipal = activeLoans.sumOf { it.principalAmount }
    val totalRemaining = activeLoans.sumOf { it.remainingBalance }
    val totalPaid = activeLoans.sumOf { it.totalPaid }

    MoneyCard(
        modifier = Modifier.fillMaxWidth(),
        useGradient = true,
        gradientColors = if (type == LoanType.TAKEN)
            listOf(DebtOrange, DebtOrangeDark)
        else
            listOf(IncomeGreen, IncomeGreenDark)
    ) {
        Text(
            text = if (type == LoanType.TAKEN) "Total Borrowed" else "Total Lent",
            style = MaterialTheme.typography.titleMedium,
            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$${totalPrincipal.formatCurrency()}",
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
                    text = "Paid",
                    style = MaterialTheme.typography.bodySmall,
                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = "$${totalPaid.formatCurrency()}",
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
                    text = "$${totalRemaining.formatCurrency()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}