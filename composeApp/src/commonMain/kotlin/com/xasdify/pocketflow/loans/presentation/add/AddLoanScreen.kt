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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.ui.theme.DebtOrange
import com.xasdify.pocketflow.ui.theme.IncomeGreen
import com.xasdify.pocketflow.utils.getCurrentTimeMilli

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLoanScreen(
    context: ComponentContext, // Changed parameter name to match actual usage
    onNavigateBack: () -> Unit,
    onSave: (Loan) -> Unit = {} // Placeholder for simple injection
) {
    var loanType by remember { mutableStateOf(LoanType.TAKEN) }
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Loan") },
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
                    isSelected = loanType == LoanType.TAKEN,
                    color = DebtOrange,
                    onClick = { loanType = LoanType.TAKEN },
                    modifier = Modifier.weight(1f)
                )
                LoanTypeButton(
                    text = "I Lent",
                    isSelected = loanType == LoanType.GIVEN,
                    color = IncomeGreen,
                    onClick = { loanType = LoanType.GIVEN },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Inputs
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(if (loanType == LoanType.TAKEN) "Lender Name" else "Borrower Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = showError && name.isBlank()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                leadingIcon = { Icon(Icons.Default.Money, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = showError && amount.toDoubleOrNull() == null
            )

            OutlinedTextField(
                value = interestRate,
                onValueChange = { interestRate = it },
                label = { Text("Interest Rate (%)") },
                leadingIcon = { Icon(Icons.Default.Percent, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("0.0") }
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
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

            Button(
                onClick = {
                    val amountVal = amount.toDoubleOrNull()
                    if (name.isNotBlank() && amountVal != null && amountVal > 0) {
                        val loan = Loan(
                            id = 0, // Auto-generated
                            type = loanType.name,
                            lenderName = name,
                            principalAmount = amountVal,
                            interestRate = interestRate.toDoubleOrNull() ?: 0.0,
                            currencyCode = "USD",
                            startDate = getCurrentTimeMilli(),
                            dueDate = getCurrentTimeMilli() + 30L * 24 * 60 * 60 * 1000, // Default 30 days
                            status = LoanStatus.ACTIVE.name,
                            description = description,
                            totalPaid = 0.0,
                            remainingBalance = amountVal,
                            createdAt = getCurrentTimeMilli(),
                            updatedAt = getCurrentTimeMilli()
                        )
                        onSave(loan)
                        onNavigateBack()
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save Loan", fontWeight = FontWeight.Bold)
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