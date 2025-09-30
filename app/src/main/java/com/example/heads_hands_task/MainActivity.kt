package com.example.heads_hands_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heads_hands_task.ui.theme.Head_Headers_TaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Head_Headers_TaskTheme {
                val viewModel: GameViewModel = viewModel()
                val state by viewModel.gameState.collectAsState()
                if (state.player == null) {
                    StartScreen(viewModel)
                } else {
                    GameScreen()
                }
            }
        }
    }
}
