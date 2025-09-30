package com.example.heads_hands_task

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.heads_hands_task.R
@Composable
fun StatCard(title: String, lines: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Cyan, RoundedCornerShape(8.dp))
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = title,
            fontFamily = FontFamily.Monospace,
            fontSize = 18.sp,
            color = Color.Green
        )
        Spacer(modifier = Modifier.height(4.dp))
        lines.forEach { line ->
            Text(
                text = line,
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val state by viewModel.gameState.collectAsState(initial = GameState())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            state.player?.let { player ->
                StatCard(
                    title = "Игрок",
                    lines = listOf(
                        "Здоровье: ${player.health}/${player.maxHealth}",
                        "Исцелений : ${player.healsLeft}"
                    )
                )
            }

            state.enemy?.let { enemy ->
                StatCard(
                    title = "Враг: ${enemy.type}",
                    lines = listOf(
                        "Здоровье: ${enemy.health}/${enemy.maxHealth}"
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Black.copy(alpha = 0.3f))
                    .border(2.dp, Color.Cyan, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.player?.let { PlayerImage(it) }
                    Text(
                        text = "vs",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    state.enemy?.let { MonsterImage(it) }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StyledButton(
                    onClick = { viewModel.attackEnemy() },
                    text = "Атака",
                    enabled = !state.isGameOver,
                    modifier = Modifier.weight(1f)
                )
                StyledButton(
                    onClick = { viewModel.healPlayer() },
                    text = "Лечить",
                    enabled = !state.isGameOver,
                    modifier = Modifier.weight(1f)
                )
                StyledButton(
                    onClick = { viewModel.resetGame() },
                    text = "Заново",
                    modifier = Modifier.weight(1f)
                )
            }


            Spacer(Modifier.height(16.dp))

            Text(
                text = state.message ?: "Добро пожаловать в игру!",
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun MonsterImage(monster: Monster) {
    val imageRes = when (monster.type) {
        EnemyType.GHOST -> R.drawable.ghost
        EnemyType.ORC -> R.drawable.orc
        EnemyType.TROLL -> R.drawable.troll
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = monster.type.name,
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
    )
}

@Composable
fun PlayerImage(player: Player){
    val imageRes = R.drawable.rambo // картинка игрока в drawable, например player.png

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "Игрок",
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
    )
}


@Composable
fun StyledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .border(2.dp, Color.Cyan, RoundedCornerShape(8.dp))
            .background(Color.Black, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Text(
            text,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

