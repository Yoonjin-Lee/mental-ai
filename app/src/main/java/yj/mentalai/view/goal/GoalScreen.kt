package yj.mentalai.view.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yj.mentalai.R
import yj.mentalai.ui.theme.Purple40
import yj.mentalai.ui.theme.PurpleGrey80
import yj.mentalai.view.home.GoalList
import yj.mentalai.view.home.HomeViewModel
import yj.mentalai.view.profile.ProfileViewModel

@Composable
fun GoalScreen() {
    val viewModel: GoalViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val profileViewModel : ProfileViewModel = hiltViewModel()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            var goal by remember { mutableStateOf("") }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "목표 설정하기",
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = {
                    if (goal.isBlank()) {
                        viewModel.toast("목표를 입력해주세요")
                    } else {
                        keyboardController?.hide()
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.saveGoal(
                                goal
                            )
                            withContext(Dispatchers.Main){
                                homeViewModel.getData()
                                profileViewModel.getProfile()
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.round_add_24),
                        contentDescription = "목표 추가"
                    )
                }
            }
            TextField(
                value = goal,
                onValueChange = { field ->
                    goal = field
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = PurpleGrey80,
                    unfocusedContainerColor = PurpleGrey80,
                    focusedIndicatorColor = Purple40,
                    unfocusedIndicatorColor = Purple40
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Divider(
                modifier = Modifier.padding(vertical = 16.dp)
            )
            GoalList()
        }
    }
}