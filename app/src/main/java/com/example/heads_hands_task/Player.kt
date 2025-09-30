package com.example.heads_hands_task

class Player(
    attack: Int,
    defence: Int,
    maxHealth: Int,
    damageRange: IntRange,
    initialHeals: Int = 4
) : Creature(
    attack = attack,
    defence = defence,
    health = maxHealth,
    maxHealth = maxHealth,
    damageRange = damageRange
) {
    var healsLeft = initialHeals
        private set

    init {
        require(initialHeals >= 0) { "initialHeals must be non-negative" }
    }
    fun consumeHeal() {
        if (healsLeft > 0) healsLeft--
    }
    fun canHeal(): Boolean = healsLeft > 0 && isAlive()

}
