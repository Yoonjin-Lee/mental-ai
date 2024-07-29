package yj.mentalai.view.write

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    fun finish(){
        (context as Activity).finish()
    }
}