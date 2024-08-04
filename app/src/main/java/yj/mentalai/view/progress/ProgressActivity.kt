package yj.mentalai.view.progress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import yj.mentalai.ui.theme.MentalAITheme

@AndroidEntryPoint
class ProgressActivity : ComponentActivity() {
    val viewModel : ProgressViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val goal = intent.getStringExtra("goal").toString()
        viewModel.getHistory(goal)
        setContent {
            MentalAITheme {
                ProgressScreen(goal)
            }
        }
    }
}