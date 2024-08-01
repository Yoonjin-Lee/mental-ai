package yj.mentalai.view.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yj.mentalai.R
import yj.mentalai.ui.theme.MentalAITheme
import yj.mentalai.ui.theme.Pink80
import yj.mentalai.ui.theme.Purple40
import yj.mentalai.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen() {
    var isClicked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("숨 쉬기") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.round_keyboard_arrow_left_24),
                            contentDescription = "뒤로 가기"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PurpleGrey80
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    CircularProgressIndicator(
                        progress = 0.6f,
                        modifier = Modifier.size(120.dp),
                        color = Purple40,
                        strokeWidth = 5.dp,
                        trackColor = Color.LightGray
                    )
                    Text(
                        text = "60%",
                        fontSize = 28.sp
                    )
                }
                Button(
                    onClick = { isClicked = !isClicked },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isClicked) Color.LightGray else Purple40
                    ),
                    modifier = Modifier
                        .weight(2f)
                        .padding(30.dp),
                    enabled = !isClicked
                ) {
                    Text(text = "오늘도 했음!")
                }
            }
            Divider()
            val l = listOf("2024년 7월 20일", "2024년 7월 20일")
            Text(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth(),
                text = "실천 날짜",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                modifier = Modifier.padding(16.dp, 0.dp)
            ) {
                items(l){element ->
                    DateCard(date = element)
                }
            }
        }
    }
}

@Composable
fun DateCard(
    date: String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Pink80,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = date,
            fontStyle = FontStyle.Italic
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MentalAITheme {
        ProgressScreen()
    }
}