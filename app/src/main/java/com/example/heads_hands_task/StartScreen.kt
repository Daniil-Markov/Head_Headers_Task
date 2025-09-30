package com.example.heads_hands_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StartScreen(viewModel: GameViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray), // фон на весь экран
        contentAlignment = Alignment.Center
    ) {
        StyledButton(
            onClick = { viewModel.startGame() },
            text = "Начать игру",
            modifier = Modifier.padding(horizontal = 32.dp) // отступы от краёв
        )
    }
}
