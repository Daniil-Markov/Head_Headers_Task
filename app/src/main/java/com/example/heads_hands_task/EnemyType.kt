package com.example.heads_hands_task

enum class EnemyType(
    val attack: Int,
    val defence: Int,
    val maxHealth: Int,
    val damageRange: IntRange,
    val displayName: String

) {
    GHOST(attack = 10, defence = 5, maxHealth = 60, damageRange = 1..6, displayName = "Призрак"),
    ORC(attack = 15, defence = 10, maxHealth = 80, damageRange = 2..8, displayName = "Орк"),
    TROLL(attack = 20, defence = 15, maxHealth = 100, damageRange = 4..10, displayName = "Тролль")
}