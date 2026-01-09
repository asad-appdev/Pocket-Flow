package com.xasdify.pocketflow.transactions.presentation.income

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xasdify.pocketflow.core.presentation.navigation.transaction.IncomeScreenComponent
import com.xasdify.pocketflow.transactions.data.entities.IncomeEntity
import com.xasdify.pocketflow.ui.components.*
import com.xasdify.pocketflow.ui.theme.IncomeGreen
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeScreen(component: IncomeScreenComponent) {
    val repository: com.xasdify.pocketflow.transactions.data.repository.IncomeRepository = koinInject()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var amount by remember { mutableStateOf("") }
    var selectedSource by remember { mutableStateOf<IncomeSource?>(null) }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Today") }
    var isRecurring by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    
    // Validation states
    var amountError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }
    var sourceError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Income",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { component.onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Amount Input
            item {
                Column {
                    AmountInput(
                        value = amount,
                        onValueChange = { 
                            amount = it
                            amountError = null
                        },
                        currencySymbol = "$",
                        label = "Income Amount",
                        modifier = Modifier.fillMaxWidth()
                    )
                    amountError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }
            }

            // Source Selection
            item {
                Column {
                    Text(
                        text = "Income Source",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (sourceError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                    sourceError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.height(200.dp)
                    ) {
                        items(getIncomeSources()) { source ->
                            CategoryChip(
                                label = source.name,
                                icon = source.icon,
                                isSelected = selectedSource == source,
                                selectedColor = IncomeGreen,
                                onClick = { 
                                    selectedSource = source
                                    sourceError = null
                                }
                            )
                        }
                    }
                }
            }

            // Recurring Income Toggle
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Recurring Income",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "This income repeats monthly",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = isRecurring,
                            onCheckedChange = { isRecurring = it }
                        )
                    }
                }
            }

            // Date Selection
            item {
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    label = { Text("Date") },
                    leadingIcon = {
                        Icon(Icons.Default.CalendarToday, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )
            }

            // Description
            item {
                Column {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { 
                            description = it
                            descriptionError = null
                        },
                        label = { Text("Description") },
                        placeholder = { Text("What is this income for?") },
                        leadingIcon = {
                            Icon(Icons.Default.Description, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = descriptionError != null
                    )
                    descriptionError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }
            }

            // Notes (Optional)
            item {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)") },
                    placeholder = { Text("Add any additional notes") },
                    leadingIcon = {
                        Icon(Icons.Default.Notes, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )
            }

            // Save Button
            item {
                PrimaryButton(
                    text = if (isSaving) "Saving..." else "Save Income",
                    onClick = {
                        // Validate inputs
                        var hasError = false
                        
                        // Validate amount
                        val amountVal = amount.toDoubleOrNull()
                        when {
                            amount.isBlank() -> {
                                amountError = "Amount is required"
                                hasError = true
                            }
                            amountVal == null -> {
                                amountError = "Please enter a valid number"
                                hasError = true
                            }
                            amountVal <= 0 -> {
                                amountError = "Amount must be greater than 0"
                                hasError = true
                            }
                            amountVal > 999999999.99 -> {
                                amountError = "Amount is too large"
                                hasError = true
                            }
                        }
                        
                        // Validate description
                        when {
                            description.isBlank() -> {
                                descriptionError = "Description is required"
                                hasError = true
                            }
                            description.length < 3 -> {
                                descriptionError = "Description must be at least 3 characters"
                                hasError = true
                            }
                            description.length > 200 -> {
                                descriptionError = "Description is too long (max 200 characters)"
                                hasError = true
                            }
                        }
                        
                        // Validate source
                        if (selectedSource == null) {
                            sourceError = "Please select an income source"
                            hasError = true
                        }
                        
                        // If no errors, save
                        if (!hasError) {
                            isSaving = true
                            scope.launch {
                                try {
                                    repository.insertIncome(
                                        IncomeEntity(
                                            title = description.trim(),
                                            amount = amountVal!!,
                                            category = selectedSource!!.name,
                                            date = getCurrentTimeMilli(),
                                            notes = notes.trim().takeIf { it.isNotBlank() }
                                        )
                                    )
                                    // Show success message
                                    snackbarHostState.showSnackbar(
                                        message = "Income saved successfully!",
                                        duration = SnackbarDuration.Short
                                    )
                                    // Navigate back
                                    component.onBack()
                                } catch (e: Exception) {
                                    // Show error message
                                    isSaving = false
                                    snackbarHostState.showSnackbar(
                                        message = "Failed to save income: ${e.message ?: "Unknown error"}",
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }
                        }
                    },
                    enabled = !isSaving,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// Sample income sources
private data class IncomeSource(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private fun getIncomeSources() = listOf(
    IncomeSource("Salary", Icons.Default.AccountBalance),
    IncomeSource("Freelance", Icons.Default.Work),
    IncomeSource("Business", Icons.Default.Business),
    IncomeSource("Investment", Icons.Default.TrendingUp),
    IncomeSource("Gift", Icons.Default.CardGiftcard),
    IncomeSource("Other", Icons.Default.MoreHoriz)
)