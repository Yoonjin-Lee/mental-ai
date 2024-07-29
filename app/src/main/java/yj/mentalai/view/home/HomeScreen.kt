package yj.mentalai.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import yj.mentalai.ui.theme.MentalAITheme

@Composable
fun HomeScreen(){
    Scaffold{
        Column(
            modifier = Modifier.padding(it)
        ){
            DiaryList()
        }
    }
}

@Composable
fun DiaryList(){
    Box {
        LazyRow {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    MentalAITheme {
        HomeScreen()
    }
}