package yj.mentalai.letter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import yj.mentalai.R
import yj.mentalai.ui.theme.MentalAITheme
import yj.mentalai.ui.theme.PurpleGrey80
import yj.mentalai.view.write.WriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterScreen() {
    val viewModel : WriteViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "받은 편지함")
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.finish() }) {
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(text = "7월 29일")
            Text(
                text = "일기",
                modifier = Modifier
                    .background(
                        color = PurpleGrey80
                    )
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LetterScreenPreview() {
    MentalAITheme {
        LetterScreen()
    }
}