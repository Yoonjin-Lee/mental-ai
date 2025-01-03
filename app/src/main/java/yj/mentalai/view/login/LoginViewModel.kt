package yj.mentalai.view.login

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import yj.mentalai.view.home.HomeViewModel
import yj.mentalai.view.main.MainActivity
import javax.inject.Inject

/*
1234@naver.com
123456
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()


    // 홈 화면으로 이동하는 함수
    fun goToMain() {
        Log.d("LoginViewModel", "goToMain: success")
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    // 회원 가입 하는 함수
    fun signUp(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d("LoginViewModel", "signUp: success")
            goToMain()
        }.addOnFailureListener {
            it.printStackTrace()
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    // 로그인 하는 함수
    fun signIn(
        email: String,
        password: String
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            goToMain()
            Log.d("LoginViewModel", "signIn: success")
        }.addOnFailureListener {
            it.printStackTrace()
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}