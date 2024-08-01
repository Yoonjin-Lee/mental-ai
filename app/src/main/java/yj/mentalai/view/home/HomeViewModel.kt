package yj.mentalai.view.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import yj.mentalai.view.progress.ProgressActivity
import yj.mentalai.view.write.WriteActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    fun writeDiary(
        date: String
    ) {
        val intent = Intent(context, WriteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("date", date)
        context.startActivity(intent)
    }

    fun goToDetails() {
        val intent = Intent(context, ProgressActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun setting() {
        val db = Firebase.firestore
        val docRef = db.collection("users").document(Firebase.auth.uid.toString())
        docRef.get().addOnSuccessListener { doc ->
            if (!doc.exists()) { // 문서가 없는 경우
                val data = hashMapOf(
                    "uid" to Firebase.auth.uid.toString(),
                    "startDate" to LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                    "lastDate" to LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                    "diary_num" to 0,
                    "goal_mum" to 0
                )
                // uid로 문서 id 지정 후, 저장
                db.collection("users").document(Firebase.auth.uid.toString()).set(data)
            }
        }
    }

    init {
        setting()
    }
}