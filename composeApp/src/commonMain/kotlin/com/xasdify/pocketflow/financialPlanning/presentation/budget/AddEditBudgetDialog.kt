package com.xasdify.pocketflow.financialPlanning.presentation.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.xasdify.pocketflow.ui.components.CategoryChip

data class BudgetCategory(
    val id: String,
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun AddEditBudgetDialog(
    budgetId: Long? = null, // null for add, non-null for edit
    initialCategory: String = "",
    initialAmount: String = "",
    onDismiss: () -> Unit,
    onSave: (category: String, amount: Double) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var amount by remember { mutableStateOf(initialAmount) }
    var showError by remember { mutableStateOf(false) }

    val categories = remember {
        listOf(
            BudgetCategory("food", "Food & Dining", Icons.Default.Restaurant),
            BudgetCategory("transport", "Transportation", Icons.Default.DirectionsCar),
            BudgetCategory("shopping", "Shopping", Icons.Default.ShoppingCart),
            BudgetCategory("entertainment", "Entertainment", Icons.Default.Movie),
            BudgetCategory("utilities", "Utilities", Icons.Default.Bolt),
            BudgetCategory("healthcare", "Healthcare", Icons.Default.LocalHospital),
            BudgetCategory("education", "Education", Icons.Default.School),
            BudgetCategory("other", "Other", Icons.Default.MoreHoriz)
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Title
                Text(
                    text = if (budgetId == null) "Add Budget" else "Edit Budget",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Category Selection
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Category Grid
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories.chunked(2)) { rowCategories ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowCategories.forEach { category ->
                                CategoryChip(
                                    label = category.name,
                                    icon = category.icon,
                                    isSelected = selectedCategory == category.id,
                                    onClick = { selectedCategory = category.id },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            // Fill empty space if odd number
                            if (rowCategories.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Amount Input
                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        amount = it
                        showError = false
                    },
                    label = { Text("Budget Amount") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Icon(Icons.Default.AttachMoney, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    isError = showError,
                    supportingText = if (showError) {
                        { Text("Please enter a valid amount") }
                    } else null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val amountValue = amount.toDoubleOrNull()
                            if (selectedCategory.isNotEmpty() && amountValue != null && amountValue > 0) {
                                onSave(selectedCategory, amountValue)
                                onDismiss()
                            } else {
                                showError = true
                            }
                        },
                        enabled = selectedCategory.isNotEmpty() && amount.isNotEmpty()
                    ) {
                        Text(if (budgetId == null) "Add" else "Save")
                    }
                }
            }
        }
    }
}