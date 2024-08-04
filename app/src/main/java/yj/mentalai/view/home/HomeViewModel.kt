package yj.mentalai.view.home

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
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
import yj.mentalai.view.progress.ProgressActivity
import yj.mentalai.view.write.WriteActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val db = Firebase.firestore
    private val _test = MutableLiveData<ArrayList<LetterData>>()
    val test: MutableLiveData<ArrayList<LetterData>>
        get() = _test

    fun goToWrite(
        letterData: LetterData
    ) {
        val intent = Intent(context, WriteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("date", letterData.date)
        intent.putExtra("letter", letterData.letter)
        context.startActivity(intent)
    }

    fun goToLetter(
        letterData: LetterData
    ){
        val intent = Intent(context, LetterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("date", letterData.date)
        intent.putExtra("letter", letterData.letter)
        context.startActivity(intent)
    }

    fun goToProgress(
        goal : String
    ) {
        val intent = Intent(context, ProgressActivity::class.java)
        intent.putExtra("goal", goal)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun getData(){
        val docRef = db.collection("diary").document(Firebase.auth.uid.toString())
        docRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            if (doc.exists() && data != null) { // 문서가 있는 경우
                val saveList = ArrayList<LetterData>()
                val list = data["list"] as ArrayList<HashMap<String, String?>>
                for (i in list){
                    saveList.add(
                        LetterData(
                            i["date"].toString(),
                            i["letter"].toString()
                        )
                    )
                }
                _test.value = saveList

                Log.d("HomeViewModel", "getData: ${saveList}")
            } else { // 문서가 없는 경우
                Log.d("HomeViewModel", "getData: 문서가 없습니다.")
            }
        }
    }

    fun setting() {
        val docRef = db.collection("profile").document(Firebase.auth.uid.toString())

        docRef.get().addOnSuccessListener { doc ->
            if (!doc.exists()) { // 문서가 없는 경우
                val data = hashMapOf(
                    "uid" to Firebase.auth.uid.toString(),
                    "startDate" to LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일")),
                    "lastDate" to LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("MM월 dd일")),
                    "diary_num" to 0,
                    "goal_mum" to 0
                )
                // uid로 문서 id 지정 후, 저장
                docRef.set(data).addOnSuccessListener {
                    Log.d("HomeViewModel", "setting: 저장 완료")
                }.addOnFailureListener {
                    Log.d("HomeViewModel", "error: 저장 실패 ${it.message}")
                }
            }
        }.addOnFailureListener {
            Log.d("HomeViewModel", "failure: ${it.message}")
        }
    }

    fun dailyUpdate(){
        val docRef = db.collection("diary").document(Firebase.auth.uid.toString())

        docRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일"))
            if (doc.exists() && data != null ) { // 문서가 있는 경우
                // 오늘 날짜 추가
                val list = data["list"] as ArrayList<HashMap<String, String?>>
                if (list[list.size - 1]["date"] != today){ // 마지막 개체가 오늘 날짜인지 확인
                    list.add(
                        hashMapOf(
                            "date" to today,
                            "letter" to null
                        )
                    )
                    docRef.update("list", list).addOnSuccessListener {
                        Log.d("HomeViewModel", "dailyUpdate: 저장 완료")
                    }.addOnFailureListener {
                        Log.d("HomeViewModel", "dailyUpdate: 저장 실패 ${it.message}")
                    }
                }
            } else { // 문서가 없는 경우
                val list = ArrayList<LetterData>()
                list.add(
                    LetterData(
                        date = today,
                        letter = null
                    )
                )
                val map = hashMapOf(
                    "list" to list
                )
                docRef.set(map).addOnSuccessListener {
                    Log.d("HomeViewModel", "dailyUpdate: 초기 설정 완료")
                }.addOnFailureListener {
                    Log.d("HomeViewModel", "dailyUpdate: 초기 설정 실패 ${it.message}")
                }
            }
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setting()
            dailyUpdate()
            withContext(Dispatchers.IO){
                getData()
            }
        }
    }
}