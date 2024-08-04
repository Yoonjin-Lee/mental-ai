package yj.mentalai.view.progress

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import yj.mentalai.view.main.MainActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _historyList = MutableLiveData<ArrayList<String>>()
    val historyList: MutableLiveData<ArrayList<String>>
        get() = _historyList

    fun finish() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun saveDate(
        goal: String
    ) {
        val db = Firebase.firestore
        val docRef = db.collection("goal").document(Firebase.auth.uid.toString())

        docRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            if (doc.exists() && data != null) {
                val list = data["list"] as ArrayList<HashMap<String, Any>>
                for (l in list) {
                    if (l["goal"] == goal) {
                        val today =
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
                        val history = l["history"] as ArrayList<String>
                        if (history[history.size - 1] != today) { // 오늘 저장이 안 되어있으면
                            history.add(today)
                            _historyList.value = history
                            l["history"] = history
                            break
                        }
                    }
                }
                docRef.update("list", list)
            }
        }
    }

    fun getHistory() {

    }
}