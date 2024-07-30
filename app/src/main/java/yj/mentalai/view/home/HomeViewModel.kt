package yj.mentalai.view.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import yj.mentalai.view.progress.ProgressActivity
import yj.mentalai.view.write.WriteActivity
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    fun writeDiary(
        date: String
    ){
        val intent = Intent(context, WriteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("date", date)
        context.startActivity(intent)
    }

    fun goToDetails(){
        val intent = Intent(context, ProgressActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}