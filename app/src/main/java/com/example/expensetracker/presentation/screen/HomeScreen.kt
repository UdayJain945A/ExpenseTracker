package com.example.expensetracker.presentation.screen

import android.app.DatePickerDialog
import android.graphics.drawable.Icon
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.presentation.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.exp

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    expenseViewModel: ExpenseViewModel = hiltViewModel()
){
    var showDialog by remember { mutableStateOf(false) }
//    var expense = remember {
//        mutableStateListOf<Expense>(Expense("123", 52.0, "Food", "chandrakata", "22-5-25"),
//                Expense("223", 52.0, "Food", "chandrakata", "22-5-25")
//        )

    var expense1 = expenseViewModel.expense

    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    var selectedMonth by remember { mutableStateOf("Jun") }

    val filteredExpenses = expense1.filter {
        val parts = it.date.split(" ")
        val month = parts.getOrNull(1)
        month != null && month == selectedMonth
    }
    val totalAmount = filteredExpenses.sumOf { it.amount }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Expense Tracker ",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    ))
            },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color(0xFF0061F2)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
               showDialog=true
            }) {
                Icon(Icons.Default.Add, contentDescription = null)

            }
        }
    ) {it->
        if(showDialog){
            ShowDialog(
               onDismiss = {showDialog = false},
             onAdd = {category, amount, date, note ->
                showDialog = false
                 expenseViewModel.addexpense(category,amount,date,note)
             }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Text(text = "Welcome to Expense Tracker", style = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = 20.sp
            ))
            Spacer(modifier = Modifier.height(20.dp))


            TotalExpenseCard(682.0,"Jun",months,{})
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Recent Expenses", style = TextStyle(
                fontSize = 20.sp,
            ))
            Spacer(modifier = Modifier.size(20.dp))
            if(expense1.isEmpty()){
                Text(text = "Expense list is empty")
            }
            else{
                LazyColumn()
                {
                    items(expense1.size){
                        it->
                        ExpenseCard(expense1[it],{})

                    }

                }

            }


            
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ShowDialog(
    onDismiss: () -> Unit,
    onAdd: (String, Double, String, String) -> Unit
) {
    val categories = listOf("Food", "Transport", "Shopping", "Electronics", "Health", "Other")
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    var expanded by remember { mutableStateOf(false) }

    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val formatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }
    var date by remember { mutableStateOf(formatter.format(calendar.time)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                    onAdd(selectedCategory, amount.toDouble(), date, note)
                    onDismiss()

            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Add Expense") },
        text = {
            Column {
                // Dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Amount
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Note
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Note") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date Picker
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.DateRange, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = date, modifier = Modifier.weight(1f))
                    TextButton(onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(Calendar.YEAR, year)
                                calendar.set(Calendar.MONTH, month)
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                date = formatter.format(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }) {
                        Text("Select Date")
                    }
                }
            }
        }
    )
}
@Composable
fun TotalExpenseCard(
    totalAmount: Double,
    selectedMonth: String,
    months: List<String>,
    onMonthSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0061F2)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Total Spent",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "₹$totalAmount",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Box {
                OutlinedButton(onClick = { expanded = true })
                {
                    Text(selectedMonth, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    months.forEach { month ->
                        DropdownMenuItem(
                            onClick = {
                                onMonthSelected(month)
                                expanded = false
                            },
                            text = { Text(month) }
                        )
                    }
                }
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun showpreviewcard(){
//    TotalExpenseCard(5000.0)
//}

@Composable
fun ExpenseCard(expense: Expense,onDelete : ()  -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
            , horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "₹${expense.amount}", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Category: ${expense.category}")
                Text(text = "Note: ${expense.note}")
                Text(text = "Date: ${expense.date}")
            }
            IconButton(
                onClick = onDelete
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }

        }

    }
}

