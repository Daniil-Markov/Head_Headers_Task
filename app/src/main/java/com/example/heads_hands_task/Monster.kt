package com.example.heads_hands_task

class Monster(val type: EnemyType) : Creature(
    attack = type.attack,
    defence = type.defence,
    health = type.maxHealth,
    maxHealth = type.maxHealth,
    damageRange = type.damageRange
) {

    val name: String get() = type.displayName
}
