package yj.mentalai.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yj.mentalai.R
import yj.mentalai.data.GoalData
import yj.mentalai.ui.theme.MentalAITheme
import yj.mentalai.ui.theme.Pink80
import yj.mentalai.ui.theme.Purple80
import yj.mentalai.ui.theme.PurpleGrey40
import yj.mentalai.ui.theme.PurpleGrey80
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            DiaryList()
            Spacer(modifier = Modifier.padding(10.dp))
            GoalList()
        }
    }
}

@Composable
fun DiaryList() {
    Box(
        modifier = Modifier
            .background(
                color = Pink80,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        val diaryList = listOf(
            "7월 29일",
            "7월 30일"
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(diaryList) {
                DiaryItem(it)
            }
        }
    }
}

@Composable
fun DiaryItem(
    date: String
) {
    Card(
        modifier = Modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clip(shape = CircleShape)
                    .size(50.dp)
            )
        }
    }
}

@Composable
fun GoalList() {
    val goalList = listOf(
        GoalData(
            "밥 먹기",
            7,
            3
        ),
        GoalData(
            "밥 먹기",
            7,
            3
        )
    )

    Box(
        modifier = Modifier
            .background(
                color = Purple80,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(goalList) {
                GoalItem(it)
            }
        }
    }
}

@Composable
fun GoalItem(
    data: GoalData
) {
    Card(
        modifier = Modifier
            .background(
                color = PurpleGrey80,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.title,
                Modifier.padding(10.dp)
            )
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.round_keyboard_arrow_right_24),
                        contentDescription = "화면 이동",
                    )
                }
                Text(
                    text = (data.progress.toFloat() / data.total * 100).roundToInt().toString() + "%",
                    modifier = Modifier.padding(10.dp)
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(100.dp),
                    progress = data.progress.toFloat() / data.total,
                    color = PurpleGrey40,
                    trackColor = PurpleGrey80
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MentalAITheme {
        HomeScreen()
    }
}