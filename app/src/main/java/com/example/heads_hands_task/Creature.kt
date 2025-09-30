package com.example.heads_hands_task

abstract class Creature(
    val attack: Int,
    val defence: Int,
    var health: Int,
    val maxHealth: Int,
    val damageRange: IntRange
) {

    companion object {
        private const val MIN_STAT_VALUE = 1
        private const val MAX_STAT_VALUE = 30
        private const val MIN_HEALTH = 0
        private const val MIN_DAMAGE = 1
    }

    init {
        require(attack in MIN_STAT_VALUE..MAX_STAT_VALUE) { "Attack must be between 1 and 30" }
        require(defence in MIN_STAT_VALUE..MAX_STAT_VALUE) { "Defence must be between 1 and 30" }
        require(maxHealth > MIN_HEALTH) { "maxHealth must be positive" }
        require(health in MIN_HEALTH..maxHealth) { "health must be between 0 and maxHealth" }
        require(damageRange.first >= MIN_DAMAGE) { "damageRange start must be positive" }
        require(damageRange.last >= damageRange.first) { "damageRange end must be greater than or equal to start" }
    }
    fun isAlive() = health > MIN_HEALTH

    open fun takeDamage(damage: Int) {
        health = (health - damage).coerceAtLeast(0)
    }
}