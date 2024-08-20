package com.example.homemaster

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codewithfk.expensetracker.android.widget.ExpenseTextView
import com.example.homemaster.data.model.ExpanseEntity
import com.example.homemaster.viewmodel.AddExpViewModel
import com.example.homemaster.viewmodel.AddExpViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun AddExpanse(modifier: Modifier = Modifier) {
    val viewModel = AddExpViewModelFactory(LocalContext.current).create(AddExpViewModel::class.java)
    val coroutineScope = rememberCoroutineScope()
    Surface {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs();
            Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 20.dp, end = 20.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    modifier = Modifier.align(Alignment.CenterStart),
                    contentDescription = null
                )
                Text(
                    text = "Add Your Expanse",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    ),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
                Image(
                    painter = painterResource(id = R.drawable.dots_menu), contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            DataForm(modifier = Modifier.constrainAs(card) {
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }, addOnExpense = {
                coroutineScope.launch {
                    viewModel.insertExpanse(it)
                }
            })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddExpanseScreenPreview(modifier: Modifier = Modifier) {
    AddExpanse(modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataForm(modifier: Modifier, addOnExpense: (ExpanseEntity) -> Unit) {
    val name = remember {
        mutableStateOf("")
    }
    val amount = remember {
        mutableStateOf("")
    }
    val date = remember {
        mutableStateOf(0L)
    }
    val selectedCategory = remember {
        mutableStateOf("")
    }
    val selectedType = remember {
        mutableStateOf("")
    }

    var showDatePicker = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
            .padding(top = 20.dp)
            .shadow(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        ExpenseTextView(text = "Name", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = name.value, onValueChange = {
            name.value = it
        }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(8.dp))

        ExpenseTextView(text = "Amount", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(value = amount.value, onValueChange = {
            amount.value = it
        }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.size(8.dp))


        //Date pricker
        ExpenseTextView(text = "Date", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = if (date.value == 0L) "" else Utils.convertMillisToDate(date.value),
            onValueChange = { },
            trailingIcon = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker.value = true },
            enabled = false
        )

//        Expanse dropdown
        ExpenseTextView(text = "Category", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        Spacer(modifier = Modifier.size(8.dp))
        ExpanseDropdownMenuBox(
            listOfItems = listOf(
                "Netflix",
                "Paypal",
                "Upwork",
                "Salary",
                "Starbucks"
            ), onItemSelected = {selectedCategory.value = it})
        
        Spacer(modifier = Modifier.size(8.dp))
//      Expanse Type
        ExpenseTextView(text = "Type", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        Spacer(modifier = Modifier.size(8.dp))
        ExpanseDropdownMenuBox(
            listOfItems = listOf(
                "Income",
                "Expanse",
            ), onItemSelected = {selectedType.value = it})


        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val model = ExpanseEntity(
                id = null,
                title = name.value,
                amount = amount.value.toDoubleOrNull() ?: 0.0,
                date = Utils.convertMillisToDate(date.value),
                category = selectedCategory.value,
                type = selectedType.value
            )
            addOnExpense(model)
        }, modifier = Modifier.fillMaxWidth()) {
            ExpenseTextView(text = "Add Extension")
        }

    }
    if (showDatePicker.value) {
        ExpaseDatePickerDialog(onDateSelected = {
            date.value = it
            showDatePicker.value = false
        }, onDimission = {
            showDatePicker.value = false
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpanseDropdownMenuBox(listOfItems: List<String>, onItemSelected: (String) -> Unit) {
    val context = LocalContext.current
    var expanded = remember { mutableStateOf(false) }
    var selectedText = remember { mutableStateOf(listOfItems[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                expanded.value = it
            }
        ) {
            OutlinedTextField(
                value = selectedText.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                modifier = Modifier.menuAnchor().fillMaxWidth())
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                listOfItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText.value = item
                            onItemSelected(selectedText.value)
                            expanded.value = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ExpaseDatePickerDialog(
    modifier: Modifier = Modifier,
    onDateSelected: (date: Long) -> Unit,
    onDimission: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(onDismissRequest = { onDimission() }, confirmButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            Text(text = "Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = { onDimission() }) {
            Text(text = "Cancel")
        }
    }) {
        DatePicker(state = datePickerState)
    }
}