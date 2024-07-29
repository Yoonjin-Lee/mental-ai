package yj.mentalai.view.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import yj.mentalai.view.main.MainActivity
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun goToMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun signUp(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d("LoginViewModel", "signUp: success")
            goToMain()
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    fun signIn(
        email: String,
        password: String
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            goToMain()
            Log.d("LoginViewModel", "signIn: success")
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }
}