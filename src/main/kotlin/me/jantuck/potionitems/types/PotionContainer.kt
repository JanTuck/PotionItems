package me.jantuck.potionitems.types

import org.bukkit.inventory.EquipmentSlot
import org.bukkit.potion.PotionEffectType
import java.util.*

/**
 *
 */
data class PotionContainer(val potionType: PotionEffectType,
                           val amplifier: Int,
                           val potionLengthType: PotionLength,
                           var potionLength: Long,
                           var activatedAt: Long,
                           val equipmentSlot: EquipmentSlot,
                           val itemUUID: UUID
                           ){
    override fun toString(): String {
        return "PotionContainer(potionType=$potionType, amplifier=$amplifier, potionLengthType=$potionLengthType, potionLength=$potionLength, activatedAt=$activatedAt, equipmentSlot=$equipmentSlot)"
    }
}
