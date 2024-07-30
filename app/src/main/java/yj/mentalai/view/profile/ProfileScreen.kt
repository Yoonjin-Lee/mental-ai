package yj.mentalai.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yj.mentalai.R
import yj.mentalai.ui.theme.MentalAITheme
import yj.mentalai.ui.theme.Pink60
import yj.mentalai.ui.theme.Purple40
import yj.mentalai.ui.theme.PurpleGrey80

@Composable
fun ProfileScreen() {
    Scaffold(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            var logoutClicked by remember { mutableStateOf(false) }

            if (logoutClicked) {
                AlertDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Purple40
                            )
                        ) {
                            Text(text = "네")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { logoutClicked = !logoutClicked },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Purple40
                            )) {
                            Text(text = "아니오")
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.round_logout_24),
                            contentDescription = "로그아웃"
                        )
                    },
                    title = { Text(text = "로그아웃") },
                    text = { Text(text = "로그아웃 하시겠습니까?") },
                    titleContentColor = Purple40,
                    containerColor = PurpleGrey80,
                    iconContentColor = Purple40
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PurpleGrey80, RoundedCornerShape(16.dp))
                    .padding(20.dp, 30.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "사용자 이름",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            ProfileCard(title = "일기 시작일", value = "2024년 7월 30일")
                            ProfileCard(title = "설정 목표 수", value = "10")
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            ProfileCard(title = "마지막 작성일", value = "2024년 7월 31일")
                            ProfileCard(title = "현재 진행 수", value = "10")
                        }
                    }
                }
            }
            Button(
                onClick = {
                    logoutClicked = !logoutClicked
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink60
                )
            ) {
                Text(text = "로그아웃")
            }
        }

    }

}

@Composable
fun ProfileCard(
    title: String,
    value: String
) {
    Column {
        Text(text = title)
        Text(text = value, fontSize = 13.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MentalAITheme {
        ProfileScreen()
    }
}