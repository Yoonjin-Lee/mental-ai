package yj.mentalai.view.home

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yj.mentalai.data.server.LetterData
import yj.mentalai.letter.LetterActivity
import yj.mentalai.view.progress.ProgressActivity
import yj.mentalai.view.write.WriteActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val db = Firebase.firestore
    private val _test = MutableLiveData<ArrayList<LetterData>>()

    // 작성한 글 리스트
    val test : LiveData<ArrayList<LetterData>>
        get() = _test

    // 목표 리스트
    private val _goalList = MutableLiveData<ArrayList<String>>()
    val goalList: LiveData<ArrayList<String>>
        get() = _goalList

    /*
    * 작성 화면으로 이동
     */
    fun goToWrite(
        letterData: LetterData
    ) {
        val intent = Intent(context, WriteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("date", letterData.date)
        intent.putExtra("letter", letterData.letter)
        context.startActivity(intent)
    }

    /*
    * 편지를 볼 수 있는 화면으로 이동
     */
    fun goToLetter(
        letterData: LetterData
    ) {
        val intent = Intent(context, LetterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("date", letterData.date)
        intent.putExtra("letter", letterData.letter)
        context.startActivity(intent)
    }

    /*
     * 지금까지 작성한 글을 볼 수 있는 화면으로 이동
     */
    fun goToProgress(
        goal: String
    ) {
        val intent = Intent(context, ProgressActivity::class.java)
        intent.putExtra("goal", goal)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /*
    * 데이터베이스에서 데이터를 가져오는 함수
     */
    private fun getData() {
        val docRef = db.collection("diary").document(Firebase.auth.uid.toString())
        docRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            if (doc.exists() && data != null) { // 문서가 있는 경우
                val saveList = ArrayList<LetterData>()
                val list : ArrayList<HashMap<String, String?>>

                // 문서가 있어도 data["list"]가 없을 수 있으므로 null 체크
                if (data["list"] == null) {
                    list = arrayListOf()
                }else {
                    list = data["list"] as ArrayList<HashMap<String, String?>>
                }
                for (i in list) {
                    saveList.add(
                        LetterData(
                            i["date"].toString(),
                            i["letter"].toString()
                        )
                    )
                }
                _test.value = saveList

                Log.d("HomeViewModel", "getData: $saveList")
            } else { // 문서가 없는 경우
                Log.d("HomeViewModel", "getData: 문서가 없습니다.")
            }
        }

        val goalRef = db.collection("goal").document(Firebase.auth.uid.toString())
        goalRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            if (doc.exists() && data != null) { // 문서가 있는 경우
                val saveList = ArrayList<String>()
                // 문서가 있어도 data["list"]가 없을 수 있으므로 null 체크
                if (data["list"] == null) {
                    _goalList.value = saveList
                    return@addOnSuccessListener
                }
                val list = data["list"] as ArrayList<HashMap<String, Any>>
                for (i in list) {
                    saveList.add(
                        i["name"].toString()
                    )
                }
                _goalList.value = saveList
            }
        }
    }

    /*
    * 설정 화면으로 이동하는 함수
     */
    private fun setting() {
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

    /*
     * 문서에서 오늘 날짜 문서를 확인하고, 없으면 추가하는 함수
     */
    private fun dailyUpdate() {
        val docRef = db.collection("diary").document(Firebase.auth.uid.toString())

        docRef.get().addOnSuccessListener { doc ->
            val data = doc.data
            val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일"))
            if (doc.exists() && data != null) { // 문서가 있는 경우
                // 오늘 날짜 추가
                val list = data["list"] as ArrayList<HashMap<String, String?>>
                if (list[list.size - 1]["date"] != today) { // 마지막 개체가 오늘 날짜인지 확인
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

    /*
     * 데이터베이스에 데이터를 추가하는 함수
     */
    fun addData(
        goal : String
    ){
        val list = _goalList.value ?: emptyList()
        val newList = list + goal
        _goalList.value = newList.toCollection(ArrayList())
        Log.d("HomeViewModel", "addData: ${_goalList.value}")
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            // 설정과 편지 문서의 날짜를 확인하고
            setting()
            dailyUpdate()
            withContext(Dispatchers.IO) {
                // 최신 데이터를 가져온다.
                getData()
            }
        }
    }
}