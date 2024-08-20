package com.example.homemaster

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.codewithfk.expensetracker.android.ui.theme.Zinc
import com.codewithfk.expensetracker.android.widget.ExpenseTextView
import com.example.homemaster.data.model.ExpanseEntity
import com.example.homemaster.viewmodel.HomeViewModel
import com.example.homemaster.viewmodel.HomeViewModelFactory

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            // Create references for the composables to constrain
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
                .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {

                Column {
                    ExpenseTextView(
                        text = "Good Morning,",
                        style = TextStyle(fontSize = 16.sp, color = Color.White)
                    )
                    ExpenseTextView(
                        text = "Coding with KK", modifier = Modifier.padding(top = 5.dp),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }

            val state = viewModel.expanse.collectAsState(initial = emptyList())
            println("State Value: " + state.value)
            val expanses = viewModel.getTotalExpanse(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val balance = viewModel.getBalance(state.value)

            CardItem(
                modifier = Modifier
                    .height(300.dp)
                    .padding(top = 150.dp)
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Zinc)
                    .padding(all = 10.dp)
                    .constrainAs(card) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, expanses, income, balance
            )

            TransactionList(modifier = Modifier.padding(horizontal = 20.dp).constrainAs(list) {
                top.linkTo(card.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },list = state.value, viewModel)

        }
    }
}

@Composable
fun CardItem(modifier: Modifier = Modifier, expanses: String, income: String, balance: String) {
    Column(modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(modifier = Modifier.align(Alignment.CenterStart)) {
                ExpenseTextView(
                    text = "Total Balance",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp)
                )
                ExpenseTextView(
                    text = balance,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Image(
                painter = painterResource(id = R.drawable.dots_menu), contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            CardRowItem(
                title = "Income",
                icon = R.drawable.ic_income,
                amount = income,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            CardRowItem(
                title = "Expanse",
                icon = R.drawable.ic_expense,
                amount = expanses,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}


@Composable
fun TransactionList(modifier: Modifier = Modifier, list: List<ExpanseEntity>, viewModel: HomeViewModel) {
    LazyColumn(modifier) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                ExpenseTextView(
                    text = "Recent Transaction",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                ExpenseTextView(
                    text = "See All",
                    style = TextStyle(),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        items(list) {
            TransactionItem(
                title = it.title,
                date = it.date.toString(),
                icon = viewModel.getItemIcon(it),
                amount = it.amount.toString()
            )
        }
    }
}

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    title: String,
    date: String,
    icon: Int,
    amount: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row() {
            Image(
                painter = painterResource(id = icon),
                modifier = Modifier.size(35.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(verticalArrangement = Arrangement.Center) {
                ExpenseTextView(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(fontSize = 16.sp)
                )
                ExpenseTextView(text = date, style = TextStyle(fontSize = 12.sp))
            }
        }
        ExpenseTextView(
            text = amount,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = Color.Red,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CardRowItem(modifier: Modifier = Modifier, title: String, icon: Int, amount: String) {
    Column(modifier) {
        Row() {
            Image(painter = painterResource(id = icon), contentDescription = null)
            ExpenseTextView(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
        }
        ExpenseTextView(
            text = amount,
            style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen(modifier: Modifier = Modifier) {
    HomeScreen(modifier = modifier)
}