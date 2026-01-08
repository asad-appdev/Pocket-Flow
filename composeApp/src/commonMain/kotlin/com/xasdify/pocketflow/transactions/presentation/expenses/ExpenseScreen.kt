package com.xasdify.pocketflow.transactions.presentation.expenses

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
import com.xasdify.pocketflow.core.presentation.navigation.transaction.ExpenseScreenComponent
import com.xasdify.pocketflow.ui.components.*
import com.xasdify.pocketflow.ui.theme.ExpenseRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(component: ExpenseScreenComponent) {
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("Today") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Expense",
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
        }
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
                AmountInput(
                    value = amount,
                    onValueChange = { amount = it },
                    currencySymbol = "$",
                    label = "Expense Amount",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Category Selection
            item {
                Column {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.height(240.dp)
                    ) {
                        items(getExpenseCategories()) { category ->
                            CategoryChip(
                                label = category.name,
                                icon = category.icon,
                                isSelected = selectedCategory == category,
                                selectedColor = ExpenseRed,
                                onClick = { selectedCategory = category }
                            )
                        }
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
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    placeholder = { Text("What did you spend on?") },
                    leadingIcon = {
                        Icon(Icons.Default.Description, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
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
                    text = "Save Expense",
                    onClick = {
                        // TODO: Save expense logic
                        component.onBack()
                    },
                    enabled = amount.isNotEmpty() && selectedCategory != null,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// Sample expense categories
private data class ExpenseCategory(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private fun getExpenseCategories() = listOf(
    ExpenseCategory("Food", Icons.Default.Restaurant),
    ExpenseCategory("Shopping", Icons.Default.ShoppingCart),
    ExpenseCategory("Transport", Icons.Default.DirectionsCar),
    ExpenseCategory("Bills", Icons.Default.Receipt),
    ExpenseCategory("Health", Icons.Default.LocalHospital),
    ExpenseCategory("Entertainment", Icons.Default.Movie),
    ExpenseCategory("Education", Icons.Default.School),
    ExpenseCategory("Travel", Icons.Default.Flight),
    ExpenseCategory("Other", Icons.Default.MoreHoriz)
)