package yj.mentalai.view.goal

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
        toast("목표를 저장했습니다")
    }

    fun toast(
        message : String
    ){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}