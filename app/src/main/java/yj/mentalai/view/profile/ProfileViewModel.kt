package yj.mentalai.view.profile

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import yj.mentalai.data.server.ProfileData
import yj.mentalai.view.login.LoginActivity
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _profileFlow = MutableStateFlow<ProfileData?>(null)
    val profileFlow = _profileFlow.asStateFlow()

    fun logout(){
        val auth = Firebase.auth
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun getProfile(){
        val db = Firebase.firestore
        val auth = Firebase.auth
        val docRef = db.collection("profile").document(auth.uid.toString())

        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("Firestore", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val data = snapshot.data
                if (data != null) {
                    val profileData = ProfileData(
                        startDate = data["startDate"].toString(),
                        lastDate = data["lastDate"].toString(),
                        diaryNum = data["diary_num"].toString(),
                        goalNum = data["goal_mum"].toString(),
                    )
                    viewModelScope.launch {
                        Log.d("Flow", "emit")
                        _profileFlow.emit(profileData)
                    }
                }
            }
        }
    }
    init {
        getProfile()
    }
}