package yj.mentalai.view.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
) : ViewModel() {

    fun logout(){
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    fun getProfile(){
        val db = Firebase.firestore
    }
}