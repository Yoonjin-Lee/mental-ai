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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yj.mentalai.R
import yj.mentalai.ui.theme.MentalAITheme
import yj.mentalai.ui.theme.Purple40
import yj.mentalai.ui.theme.PurpleGrey80
import yj.mentalai.view.home.GoalList

@Composable
fun GoalScreen() {
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
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.round_add_24),
                        contentDescription = "목표 추가"
                    )
                }
            }
            TextField(
                value = goal,
                onValueChange = {field ->
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

@Preview(showBackground = true)
@Composable
fun GoalScreenPreview() {
    MentalAITheme {
        GoalScreen()
    }
}