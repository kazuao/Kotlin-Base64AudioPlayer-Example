package com.example.base64audioplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.base64audioplayer.ui.theme.Base64AudioPlayerTheme

class MainActivity : ComponentActivity() {

    private val speechService = SpeechServiceImpl(context = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Base64AudioPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        SpeechButton(speechService = speechService)
                        IncreaseButton(speechService = speechService)
                        DecreaseButton(speechService = speechService)
                        ResetButton(speechService = speechService)
                    }
                }
            }
        }
    }
}

@Composable
fun SpeechButton(speechService: SpeechService) {
    Button(
        onClick = {
            speechService.speech(base64)
        }
    ) {
        Text("Speech")
    }
}

@Composable
fun IncreaseButton(speechService: SpeechService) {
    Button(
        onClick = {
            speechService.increase()
        }
    ) {
        Text("Increase")
    }
}

@Composable
fun DecreaseButton(speechService: SpeechService) {
    Button(
        onClick = {
            speechService.decrease()
        }
    ) {
        Text("Decrease")
    }
}

@Composable
fun ResetButton(speechService: SpeechService) {
    Button(
        onClick = {
            speechService.reset()
        }
    ) {
        Text("Reset")
    }
}