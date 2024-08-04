package yj.mentalai.view.goal

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import yj.mentalai.view.home.HomeViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    fun saveGoal(goal: String) {
        val db = Firebase.firestore
        val docRef = db.collection("goal").document(Firebase.auth.uid.toString())

        docRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            if (doc.exists() && data != null) { // 문서가 존재할 경우
                val list = data["list"] as ArrayList<HashMap<Any, Any>>
                val history = listOf<String>()
                list.add(hashMapOf("name" to goal, "history" to history))
                docRef.update("list", list)
            } else { // 문서가 존재하지 않을 경우
                val arrayListOf =
                    listOf(
                        hashMapOf("name" to goal, "history" to listOf<String>())
                    )
                docRef.set(
                    "list" to arrayListOf
                ).addOnSuccessListener {
                    Log.d("GoalViewModel", "Goal saved successfully")
                }.addOnFailureListener {
                    Log.d("GoalViewModel", "Goal saved failed : $it")
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
                    "diary_num" to data["diary_num"],
                    "goal_mum" to data["goal_mum"] as Long + 1
                )
                profileRef.update(map).addOnSuccessListener {
                    Log.d("fun writeDiary", "profile success -> $it")
                }.addOnFailureListener {
                    Log.d("fun writeDiary", "profile fail -> $it")
                }
            }
        }
    }
}