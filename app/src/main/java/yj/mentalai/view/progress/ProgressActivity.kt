package yj.mentalai.view.progress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import yj.mentalai.ui.theme.MentalAITheme

@AndroidEntryPoint
class ProgressActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalAITheme {
                ProgressScreen()
            }
        }
    }
}