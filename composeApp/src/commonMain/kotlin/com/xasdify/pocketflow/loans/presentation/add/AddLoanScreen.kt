package com.xasdify.pocketflow.loans.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.ui.theme.DebtOrange
import com.xasdify.pocketflow.ui.theme.IncomeGreen
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLoanScreen(
    editLoanId: Long? = null,
    onNavigateBack: () -> Unit = {}
) {
    val viewModel: AddLoanViewModel = koinInject { org.koin.core.parameter.parametersOf(editLoanId) }
    val formState by viewModel.formState.collectAsState()
    val scrollState = rememberScrollState()

    // Navigate back when saved
    LaunchedEffect(formState.isSaved) {
        if (formState.isSaved) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editLoanId != null) "Edit Loan" else "Add New Loan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Loan Type Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LoanTypeButton(
                    text = "I Borrowed",
                    isSelected = formState.type == LoanType.TAKEN,
                    color = DebtOrange,
                    onClick = { viewModel.updateType(LoanType.TAKEN) },
                    modifier = Modifier.weight(1f)
                )
                LoanTypeButton(
                    text = "I Lent",
                    isSelected = formState.type == LoanType.GIVEN,
                    color = IncomeGreen,
                    onClick = { viewModel.updateType(LoanType.GIVEN) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Inputs
            OutlinedTextField(
                value = formState.lenderName,
                onValueChange = { viewModel.updateLenderName(it) },
                label = {
                    Text(if (formState.type == LoanType.TAKEN) "Lender Name" else "Borrower Name")
                },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = formState.validationErrors.containsKey("lenderName"),
                supportingText = formState.validationErrors["lenderName"]?.let { { Text(it) } }
            )

            OutlinedTextField(
                value = formState.principalAmount,
                onValueChange = { viewModel.updatePrincipalAmount(it) },
                label = { Text("Amount") },
                leadingIcon = { Icon(Icons.Default.Money, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = formState.validationErrors.containsKey("principalAmount"),
                supportingText = formState.validationErrors["principalAmount"]?.let { { Text(it) } }
            )

            OutlinedTextField(
                value = formState.interestRate,
                onValueChange = { viewModel.updateInterestRate(it) },
                label = { Text("Interest Rate (%)") },
                leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("0.0") },
                isError = formState.validationErrors.containsKey("interestRate"),
                supportingText = formState.validationErrors["interestRate"]?.let { { Text(it) } }
            )

            OutlinedTextField(
                value = formState.description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("Description (Optional)") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Due Date (Placeholder)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Due Date (Optional)") },
                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                placeholder = { Text("Select Date") },
                supportingText = { Text("Defaults to 30 days from now") }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Error message
            formState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Button(
                onClick = { viewModel.saveLoan() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !formState.isLoading
            ) {
                if (formState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        if (editLoanId != null) "Update Loan" else "Save Loan",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LoanTypeButton(
    text: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) color else Color.Transparent
    val contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = null
    ) {
        Text(text)
    }
}