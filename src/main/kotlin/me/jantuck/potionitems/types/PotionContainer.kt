package me.jantuck.potionitems.types

import org.bukkit.potion.PotionEffectType

/**
 *
 */
data class PotionContainer(val potionType: PotionEffectType,
                           val amplifier: Int,
                           val potionLengthType: PotionLength,
                           var potionLength: Long,
                           var activatedAt: Long
                           ){
    override fun toString(): String {
        return "PotionContainer(potionType=$potionType, amplifier=$amplifier, potionLengthType=$potionLengthType, potionLength=$potionLength, activatedAt=$activatedAt)"
    }
}
