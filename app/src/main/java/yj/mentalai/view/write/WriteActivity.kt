package yj.mentalai.view.write

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import yj.mentalai.ui.theme.MentalAITheme

@AndroidEntryPoint
class WriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val date = intent.getStringExtra("date").toString()
        setContent {
            MentalAITheme {
                WriteScreen(date)
            }
        }
    }
}