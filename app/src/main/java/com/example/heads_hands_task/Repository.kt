package com.example.heads_hands_task

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.ceil
import kotlin.random.Random

object Repository {
    private val _state = MutableStateFlow(GameState.initial())

    val state: StateFlow<GameState> = _state.asStateFlow()

    private object Dice {
        const val MIN_VALUE = 1
        const val MAX_VALUE = 6
        const val SUCCESS_THRESHOLD = 5
        const val MIN_ROLLS = 1
    }

    fun startGame() {
        _state.value = GameState(
            player = Player(attack = 12, defence = 8, maxHealth = 100, damageRange = 5..12, initialHeals = 4),
            enemy = createEnemyForLevel(1),
            level = 1,
            message = "Игра начата. Уровень 1"
        )
    }

    fun resetGame() {
        _state.value = GameState.initial()
    }

    private fun createEnemyForLevel(level: Int): Monster {
        return when (level) {
            1 -> Monster(EnemyType.GHOST)
            2 -> Monster(EnemyType.ORC)
            else -> Monster(EnemyType.TROLL)
        }
    }

    fun attackEnemy() {
        val currentState = _state.value
        if (currentState.isGameOver || currentState.player == null || currentState.enemy == null) {
            _state.value = currentState.copy(message = "Невозможно атаковать: игра не начата или завершена")
            return
        }

        val player = currentState.player
        val enemy = currentState.enemy

        val playerHit = rollAttackSuccess(attacker = player, defender = enemy)
        var playerDamage = 0
        if (playerHit) {
            playerDamage = player.damageRange.random()
            enemy.takeDamage(playerDamage)
        }

        var enemyHit = false
        var enemyDamage = 0
        if (enemy.isAlive()) {
            enemyHit = rollAttackSuccess(attacker = enemy, defender = player)
            if (enemyHit) {
                enemyDamage = enemy.damageRange.random()
                player.takeDamage(enemyDamage)
            }
        }

        val enemyAlive = enemy.isAlive()
        val playerAlive = player.isAlive()

        val newState = when {
            !playerAlive -> {
                currentState.copy(
                    isGameOver = true,
                    message = "Вы погибли. Урон от врага: $enemyDamage"
                )
            }
            !enemyAlive -> {
                val newLevel = currentState.level + 1
                currentState.copy(
                    enemy = createEnemyForLevel(newLevel),
                    level = newLevel,
                    message = "Враг побеждён! Вы нанесли $playerDamage урона. Переход на уровень $newLevel"
                )
            }
            else -> {
                val msg = buildString {
                    if (playerHit) append("Вы нанесли $playerDamage урона. ") else append("Вы промахнулись. ")
                    if (enemyHit) append("Враг нанес $enemyDamage урона.") else append("Враг промахнулся.")
                }
                currentState.copy(message = msg)
            }
        }
        _state.value = newState
    }

    fun healPlayer() {
        val currentState = _state.value
        if (currentState.isGameOver || currentState.player == null) {
            _state.value = currentState.copy(message = "Исцеление недоступно: игра не начата или завершена")
            return
        }

        val player = currentState.player
        if (!player.canHeal()) {
            _state.value = currentState.copy(message = "Исцеление недоступно")
            return
        }

        val healAmount = ceil(player.maxHealth * 0.3).toInt()
        player.health = (player.health + healAmount).coerceAtMost(player.maxHealth)
        player.consumeHeal()

        _state.value = currentState.copy(message = "Вы исцелились на $healAmount HP")
    }

    private fun rollAttackSuccess(attacker: Creature, defender: Creature): Boolean {
        val modifier = (attacker.attack - defender.defence + 1).coerceAtLeast(Dice.MIN_ROLLS)
        repeat(modifier) {
            val roll = Random.nextInt(Dice.MIN_VALUE, Dice.MAX_VALUE)
            if (roll >= Dice.SUCCESS_THRESHOLD) return true
        }
        return false
    }
}
