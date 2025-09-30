package com.example.heads_hands_task

data class GameState(
    val player: Player? = null,
    val enemy: Monster? = null,
    val level: Int = 0,
    val isGameOver: Boolean = false,
    val message: String? = null
){
    companion object {
        fun initial() = GameState()
    }
}