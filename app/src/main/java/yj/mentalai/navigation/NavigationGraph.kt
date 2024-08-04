package yj.mentalai.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import yj.mentalai.view.goal.GoalScreen
import yj.mentalai.view.home.HomeScreen
import yj.mentalai.view.profile.ProfileScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navController)
        }
        composable("GoalScreen"){
            GoalScreen(navController)
        }
        composable("ProfileScreen"){
            ProfileScreen(navController)
        }
    }
}