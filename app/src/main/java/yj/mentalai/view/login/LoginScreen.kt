package yj.mentalai.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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

@Composable
fun LoginScreen() {
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
                onValueChange = {emailField ->
                    email = emailField
                },
                label = { Text("Email") },
                modifier = Modifier.padding(16.dp),
                maxLines = 1
            )
            TextField(
                value = password,
                onValueChange = {passwordField ->
                    password = passwordField
                },
                label = { Text("password") },
                modifier = Modifier.padding(16.dp),
                maxLines = 1
            )
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Login with Google")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}