package yj.mentalai.view.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import yj.mentalai.R
import yj.mentalai.ui.theme.Pink60
import yj.mentalai.ui.theme.Pink80
import yj.mentalai.ui.theme.Purple80
import yj.mentalai.ui.theme.PurpleGrey80
import androidx.compose.runtime.livedata.observeAsState
import yj.mentalai.data.server.LetterData

@Composable
fun HomeScreen(
) {
    val viewModel: HomeViewModel = hiltViewModel()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            DiaryList(viewModel)
            Spacer(modifier = Modifier.padding(10.dp))
            GoalList(viewModel)
        }
    }
}

@Composable
fun DiaryList(
    viewModel: HomeViewModel
) {
    val diaryList by viewModel.test.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .background(
                color = Pink80,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(diaryList) {
                Log.d("homeScreen DiaryItem", it.toString())
                DiaryItem(it, viewModel)
            }
        }
    }
}

@Composable
fun DiaryItem(
    letterData: LetterData,
    viewModel: HomeViewModel
) {
    val isLetterEmpty = letterData.letter.equals("null")

    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable {
                if (isLetterEmpty) { // 일기 작성 안 한 경우
                    viewModel.goToWrite(letterData)
                } else {
                    viewModel.goToLetter(letterData) // 일기를 작성한 경우
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = letterData.date,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (!isLetterEmpty) Pink60 else Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun GoalList(
    viewModel: HomeViewModel
) {
    val goalList by viewModel.goalList.observeAsState(emptyList())

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
                Log.d("homeScreen GoalItem", it)
                GoalItem(it, viewModel)
            }
        }
    }
}

@Composable
fun GoalItem(
    data: String,
    viewModel: HomeViewModel
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
                text = data,
                Modifier.padding(10.dp)
            )
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    viewModel.goToProgress(
                        data
                    )
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.round_keyboard_arrow_right_24),
                        contentDescription = "화면 이동",
                    )
                }
//                Text(
//                    text = (data.progress.toFloat() / data.total * 100).roundToInt()
//                        .toString() + "%",
//                    modifier = Modifier.padding(10.dp)
//                )
//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .padding(10.dp)
//                        .width(100.dp),
//                    progress = data.progress.toFloat() / data.total,
//                    color = PurpleGrey40,
//                    trackColor = PurpleGrey80
//                )
            }
        }
    }
}