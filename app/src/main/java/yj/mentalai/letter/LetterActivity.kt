package yj.mentalai.letter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import yj.mentalai.ui.theme.MentalAITheme

@AndroidEntryPoint
class LetterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MentalAITheme {
                LetterScreen()
            }
        }
    }
}