package com.example.baubaptechchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.baubaptechchallenge.presentation.login.LoginScreen
import com.example.baubaptechchallenge.presentation.login.LoginViewModel
import com.example.baubaptechchallenge.presentation.theme.BaubapTechChallengeTheme
import com.example.baubaptechchallenge.presentation.theme.md_theme_light_onPrimaryContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaubapTechChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = md_theme_light_onPrimaryContainer
                ) {
                    LoginScreen(loginViewModel = loginViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaubapTechChallengeTheme {
        Greeting("Android")
    }
}