package yj.mentalai.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import yj.mentalai.R
import yj.mentalai.navigation.NaviDestination
import yj.mentalai.navigation.NavigationGraph
import yj.mentalai.ui.theme.MentalAITheme
import yj.mentalai.ui.theme.PurpleGrey80
import yj.mentalai.view.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

    val icons = listOf(
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.round_home_24),
            "홈",
            "HomeScreen"
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.round_add_chart_24),
            "목표 설정",
            "GoalScreen"
        ),
        NaviDestination(
            ImageVector.vectorResource(id = R.drawable.round_person_24),
            "프로필",
            "ProfileScreen"
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MentalAI"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleGrey80
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = PurpleGrey80
            ) {
                icons.forEachIndexed { index, it ->
                    NavigationBarItem(
                        selected = (selectedItem == index),
                        onClick = {
                            selectedItem = index
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(imageVector = it.icon, contentDescription = it.title)
                        },
                        label = {
                            Text(text = it.title)
                        }
                    )
                }
            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                NavigationGraph(navController = navController)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MentalAITheme {
        MainScreen()
    }
}