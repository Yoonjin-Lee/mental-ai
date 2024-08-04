package yj.mentalai.view.profile

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.collectLatest
import yj.mentalai.R
import yj.mentalai.data.server.ProfileData
import yj.mentalai.ui.theme.Pink60
import yj.mentalai.ui.theme.Purple40
import yj.mentalai.ui.theme.PurpleGrey80

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val viewModel : ProfileViewModel = hiltViewModel()
    var profileData by remember { mutableStateOf<ProfileData?>(null) }

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
                    onDismissRequest = { logoutClicked = !logoutClicked },
                    confirmButton = {
                        Button(
                            onClick = { viewModel.logout() },
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
                        text = FirebaseAuth.getInstance().currentUser?.email ?: "사용자 이메일",
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
                            ProfileCard(title = "일기 시작일", value = profileData?.startDate.toString())
                            ProfileCard(title = "일기 작성 수", value = profileData?.diaryNum.toString())
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            ProfileCard(title = "마지막 작성일", value = profileData?.lastDate.toString())
                            ProfileCard(title = "설정 목표 수", value = profileData?.goalNum.toString())
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
    LaunchedEffect(Unit){
        viewModel.profileFlow.collectLatest{
            profileData = it
            Log.d("ProfileScreen", "profileData: $profileData")
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