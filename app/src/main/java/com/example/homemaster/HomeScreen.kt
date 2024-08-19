package com.example.homemaster

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.codewithfk.expensetracker.android.ui.theme.Zinc

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
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
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }){

                Column {
                    Text(text = "Good Morning,",
                        style = TextStyle(fontSize = 16.sp, color = Color.White))
                    Text(text = "Coding with KK", modifier = Modifier.padding(top = 5.dp),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                }

                Image(painter = painterResource(id = R.drawable.ic_notification), contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                    )

            }

            CardItem(modifier = Modifier
                .height(300.dp)
                .padding(top = 150.dp)
                .padding(horizontal = 25.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Zinc)
                .padding(all = 10.dp)
                .constrainAs(card){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            TransactionItem(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp)
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                })

        }
    }
}

@Composable
fun CardItem(modifier: Modifier = Modifier) {
    Column(modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column(modifier = Modifier.align(Alignment.CenterStart)){
                Text(text = "Total Balance", color = Color.White, style = TextStyle(fontSize = 16.sp))
                Text(text = "$0.00", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White))
            }
            Image(painter = painterResource(id = R.drawable.dots_menu), contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Box (modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {

            CardRowItem(title = "Income",
                icon = R.drawable.ic_income,
                amount = "$ 200.00",
                modifier = Modifier.align(Alignment.CenterStart)
            )

            CardRowItem(
                title = "Expanse",
                icon = R.drawable.ic_expense,
                amount = "$ 100.00",
                modifier = Modifier.align(Alignment.CenterEnd)
                )
        }
    }
}

@Composable
fun TransactionItem(modifier: Modifier = Modifier) {
        Column(modifier) {
            Box(modifier = Modifier.fillMaxWidth()){
                Text(text = "Recent Transaction", style = TextStyle(fontSize = 16.sp))
                Text(text = "See All", style = TextStyle(), modifier = Modifier.align(Alignment.CenterEnd))
            }
        }
}

@Composable
fun CardRowItem(modifier: Modifier = Modifier, title:String, icon:Int, amount:String) {
    Column (modifier) {
        Row () {
            Image(painter = painterResource(id = icon), contentDescription = null,)
            Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
        }
        Text(text = amount, style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold))
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen(modifier: Modifier = Modifier) {
        HomeScreen(modifier = modifier)
}