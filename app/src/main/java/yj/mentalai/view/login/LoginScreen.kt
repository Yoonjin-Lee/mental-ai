package yj.mentalai.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import yj.mentalai.R

@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var email by remember { mutableStateOf(String()) }
            var password by remember { mutableStateOf(String()) }

            val keyboardController = LocalSoftwareKeyboardController.current

            val passwordVisibility = remember { mutableStateOf(false) }
            val passwordIcon = if (passwordVisibility.value) {
                ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
            } else {
                ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)
            }

            Text(
                text = "Mental AI",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            TextField(
                value = email,
                onValueChange = { emailField ->
                    email = emailField
                },
                label = { Text("Email") },
                modifier = Modifier.padding(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            TextField(
                value = password,
                onValueChange = { passwordField ->
                    password = passwordField
                },
                label = { Text("password") },
                modifier = Modifier.padding(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(imageVector = passwordIcon, contentDescription = "비밀번호 보이기")
                    }
                },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation()
            )
            Button(onClick = {
                viewModel.signIn(email, password)
            }) {
                Text(text = "Login with Email")
            }
            Button(onClick = {
                viewModel.signUp(email, password)
            }) {
                Text(text = "Create Account with Email")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}