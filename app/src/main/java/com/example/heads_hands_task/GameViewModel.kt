package com.example.heads_hands_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {

    val gameState: StateFlow<GameState> = Repository.state

    fun attackEnemy() = viewModelScope.launch { Repository.attackEnemy() }

    fun startGame() = viewModelScope.launch { Repository.startGame() }

    fun healPlayer() = viewModelScope.launch { Repository.healPlayer() }

    fun resetGame() = viewModelScope.launch { Repository.resetGame() }
}