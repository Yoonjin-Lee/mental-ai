package yj.mentalai.view.write

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.vertexai.vertexAI
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yj.mentalai.data.server.LetterData
import yj.mentalai.letter.LetterActivity
import yj.mentalai.view.main.MainActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val db = Firebase.firestore

    fun finish() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun sendToGemini(
        date : String,
        statement: String
    ) {
        val geminiModel = Firebase.vertexAI.generativeModel("gemini-1.5-flash")

        CoroutineScope(Dispatchers.IO).launch {
            val response = geminiModel.generateContent(statement).text.orEmpty()

            Log.d("fun sendToGemini", "response -> $response")

            withContext(Dispatchers.IO) {
                saveDB( // DB 저장
                    LetterData(
                        date = date,
                        letter = statement
                    )
                )
                withContext(Dispatchers.Main){
                    val intent = Intent(context, LetterActivity::class.java)
                    intent.putExtra("date", date)
                    intent.putExtra("letter", response)
                    Log.d("fun sendToGemini intent", "response -> $response")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
            }
        }
    }

    fun saveDB(
        letterData: LetterData
    ) {
        val docRef = db.collection("diary").document(Firebase.auth.uid.toString())

        docRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            if (doc.exists() && data != null) {
                val list = data["list"] as ArrayList<HashMap<String, String?>>
                for(l in list){
                    if(l["date"] == letterData.date){
                        l["letter"] = letterData.letter
                        break
                    }
                }
                docRef.update("list", list).addOnSuccessListener {
                    Log.d("fun saveDB save to diary", "success -> $it")
                }.addOnFailureListener {
                    Log.d("fun saveDB save to diary", "fail -> $it")
                }
            }
        }

        val profileRef = db.collection("profile").document(Firebase.auth.uid.toString())

        profileRef.get().addOnSuccessListener {doc ->
            val data = doc.data
            if (doc.exists() && data != null) {
                val map = hashMapOf(
                    "uid" to Firebase.auth.uid.toString(),
                    "startDate" to data["startDate"],
                    "lastDate" to LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일")),
                    "diary_num" to data["diary_num"] as Long + 1,
                    "goal_mum" to data["goal_num"]
                )
                profileRef.update(map).addOnSuccessListener {
                    Log.d("fun saveDB save to profile", "profile success -> $it")
                }.addOnFailureListener {
                    Log.d("fun saveDB save to profile", "profile fail -> $it")
                }
            }
        }
    }

    fun showToast(
        message : String
    ){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}