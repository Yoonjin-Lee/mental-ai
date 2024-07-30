package yj.mentalai.view.write

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.vertexai.vertexAI
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import yj.mentalai.view.main.MainActivity
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    val db = Firebase.firestore

    fun finish(){
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun sendToGemini(
        statement : String
    ){
        val geminiModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")

        CoroutineScope(Dispatchers.IO).launch {
            val response = geminiModel.generateContent(statement).text.orEmpty()
        }
    }

    fun getLetter(){

    }
}