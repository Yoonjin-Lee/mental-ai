package yj.mentalai.letter

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import yj.mentalai.R
import yj.mentalai.data.server.LetterData
import yj.mentalai.ui.theme.PurpleGrey80
import yj.mentalai.view.write.WriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterScreen(
    date : String,
    letter : String
) {
    val viewModel : WriteViewModel = hiltViewModel()

    var content by remember { mutableStateOf<LetterData?>(null) }

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
            Text(text = date)
            Text(
                text = letter,
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
