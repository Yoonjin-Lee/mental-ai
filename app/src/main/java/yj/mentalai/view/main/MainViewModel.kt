package yj.mentalai.view.main

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    fun setting(){
        val db = Firebase.firestore
        val docRef = db.collection("users").document(Firebase.auth.uid.toString())
        docRef.get().addOnSuccessListener {doc ->
            if(!doc.exists()){ // 문서가 없는 경우
                val data = hashMapOf(
                    "uid" to Firebase.auth.uid.toString(),
                    "startDate" to LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                    "lastDate" to LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")),
                    "diary_num" to 0,
                    "goal_mum" to 0
                )
                // uid로 문서 id 지정 후, 저장
                db.collection("users").document(Firebase.auth.uid.toString()).set(data)
            }
        }
    }
}