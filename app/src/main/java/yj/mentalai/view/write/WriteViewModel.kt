package yj.mentalai.view.write

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.vertexai.vertexAI
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    val firebaseAuth = Firebase.auth

    fun finish(){
        (context as Activity).finish()
    }

    fun sendToGemini(
        statement : String
    ){
        val geminiModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")

        val db = Firebase.firestore

        CoroutineScope(Dispatchers.IO).launch {
            val response = geminiModel.generateContent(statement).text.orEmpty()
        }
    }
}