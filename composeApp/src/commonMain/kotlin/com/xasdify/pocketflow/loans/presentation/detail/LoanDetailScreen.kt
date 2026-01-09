package com.xasdify.pocketflow.loans.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.loans.domain.model.LoanPayment
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.ui.components.EmptyState
import com.xasdify.pocketflow.ui.components.MoneyCard
import com.xasdify.pocketflow.ui.theme.DebtOrange
import com.xasdify.pocketflow.ui.theme.DebtOrangeDark
import com.xasdify.pocketflow.ui.theme.ExpenseRed
import com.xasdify.pocketflow.ui.theme.IncomeGreen
import com.xasdify.pocketflow.ui.theme.IncomeGreenDark
import com.xasdify.pocketflow.utils.formatCurrency
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import org.koin.compose.koinInject
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun LoanDetailScreen(
    loanId: Long,
    onNavigateBack: () -> Unit = {},
    // onNavigateToAddPayment removed, handled locally
    onEditLoan: (Long) -> Unit = {},
    onDeleteLoan: (Long) -> Unit = {}
) {
    val viewModel: LoanDetailViewModel = koinInject { org.koin.core.parameter.parametersOf(loanId) }
    val uiState by viewModel.uiState.collectAsState()
    val paymentFormState by viewModel.paymentFormState.collectAsState()

    val loan = uiState.loanWithPayments?.loan
    val payments = uiState.loanWithPayments?.payments ?: emptyList()

    if (loan == null) {
        // Loading or Error State
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Loading...") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
        }) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("Loading Loan Details...") }
        }
        return
    }

    val cardColor = if (loan.type == LoanType.TAKEN.name) DebtOrange else IncomeGreen

    // Add Payment Dialog
    if (uiState.showAddPaymentDialog && loan != null) {
        AddPaymentDialog(
            loanId = loan.id,
            remainingBalance = loan.remainingBalance,
            amount = paymentFormState.amount,
            onAmountChange = { viewModel.updatePaymentAmount(it) },
            notes = paymentFormState.notes,
            onNotesChange = { viewModel.updateNotes(it) },
            validationErrors = paymentFormState.validationErrors,
            isProcessing = uiState.isProcessingPayment,
            onDismiss = { viewModel.hideAddPaymentDialog() },
            onSave = { viewModel.addPayment() }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Loan Details",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { onEditLoan(loanId) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { onDeleteLoan(loanId) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            if (loan.status == LoanStatus.ACTIVE.name) {
                FloatingActionButton(
                    onClick = { viewModel.showAddPaymentDialog() },
                    containerColor = cardColor
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Payment")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Loan Summary Card
            item {
                MoneyCard(
                    modifier = Modifier.fillMaxWidth(),
                    useGradient = true,
                    gradientColors = if (loan.type == LoanType.TAKEN.name)
                        listOf(DebtOrange, DebtOrangeDark)
                    else
                        listOf(IncomeGreen, IncomeGreenDark)
                ) {
                    Text(
                        text = if (loan.type == LoanType.TAKEN.name) "Borrowed From" else "Lent To",
                        style = MaterialTheme.typography.titleMedium,
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = loan.lenderName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Remaining Balance",
                        style = MaterialTheme.typography.bodySmall,
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "$${loan.remainingBalance.formatCurrency()}",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                }
            }

            // Progress Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
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
                            Column {
                                Text(
                                    text = "Principal",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$${loan.principalAmount.formatCurrency()}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Paid",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "$${loan.totalPaid.formatCurrency()}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = cardColor
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LinearProgressIndicator(
                            progress = { loan.progressPercentage },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = cardColor,
                            trackColor = cardColor.copy(alpha = 0.2f),
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${(loan.progressPercentage * 100).toInt()}% completed",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Loan Information
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
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
                        Text(
                            text = "Loan Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        LoanInfoRow(
                            icon = Icons.Default.Percent,
                            label = "Interest Rate",
                            value = "${loan.interestRate}% per year"
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        LoanInfoRow(
                            icon = Icons.Default.CalendarToday,
                            label = "Start Date",
                            value = formatDate(loan.startDate)
                        )
                        loan.dueDate?.let {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            LoanInfoRow(
                                icon = Icons.Default.CalendarToday,
                                label = "Due Date",
                                value = formatDate(it)
                            )
                        }
                        loan.description?.let {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            LoanInfoRow(
                                icon = Icons.Default.Person,
                                label = "Description",
                                value = it
                            )
                        }
                    }
                }
            }

            // Action Buttons
            if (loan.status == LoanStatus.ACTIVE.name) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.closeLoan() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = ExpenseRed
                            )
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Close Loan")
                        }
                    }
                }
            }

            // Payment History Header
            item {
                Text(
                    text = "Payment History (${payments.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            // Payment List
            if (payments.isEmpty()) {
                item {
                    EmptyState(
                        icon = Icons.Default.Add,
                        title = "No Payments Yet",
                        description = "Start recording payments to track your loan progress",
                        action = {
                            Button(onClick = { viewModel.showAddPaymentDialog() }) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Add Payment")
                            }
                        }
                    )
                }
            } else {
                items(payments) { payment ->
                    PaymentHistoryItem(payment = payment)
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun LoanInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun PaymentHistoryItem(payment: LoanPayment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate(payment.paymentDate),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${payment.amount.formatCurrency()}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = IncomeGreen
                )
            }

            if (payment.principalPortion > 0 || payment.interestPortion > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Principal",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$${payment.principalPortion.formatCurrency()}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Column {
                        Text(
                            text = "Interest",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$${payment.interestPortion.formatCurrency()}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            payment.notes?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    // Simple date formatting - in production use proper date formatter
    val days = (getCurrentTimeMilli() - timestamp) / (24 * 60 * 60 * 1000)
    return when {
        days == 0L -> "Today"
        days == 1L -> "Yesterday"
        days < 7 -> "$days days ago"
        days < 30 -> "${days / 7} weeks ago"
        days < 365 -> "${days / 30} months ago"
        else -> "${days / 365} years ago"
    }
}