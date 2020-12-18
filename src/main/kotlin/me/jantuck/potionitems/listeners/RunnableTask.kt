package me.jantuck.potionitems.listeners

import com.okkero.skedule.SynchronizationContext
import com.okkero.skedule.schedule
import me.jantuck.potionitems.PotionItems
import me.jantuck.potionitems.pdc.PDCHandler
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class RunnableTask(private val pdcHandler: PDCHandler) : BukkitRunnable() {
    
    private val lastPassThrough = mutableMapOf<UUID, MutableMap<EquipmentSlot, ItemStack>>()
    
    private fun getLastPassEquipment(uuid: UUID, equipmentSlot: EquipmentSlot) : ItemStack {
        return lastPassThrough.getOrDefault(uuid, mutableMapOf()).getOrDefault(equipmentSlot, ItemStack(Material.AIR))
    }
    
    private fun setLastPassEquipment(uuid: UUID, equipmentSlot: EquipmentSlot, itemStack: ItemStack?){
        val lastPassThroughItems = lastPassThrough.getOrDefault(uuid, mutableMapOf())
        lastPassThroughItems[equipmentSlot] = itemStack ?: ItemStack(Material.AIR)
        lastPassThrough[uuid] = lastPassThroughItems
    }


    private fun checkData(player: Player, uuid: UUID, itemStack: ItemStack?, equipmentSlot: EquipmentSlot){
        val oldItem = getLastPassEquipment(uuid, equipmentSlot)
        var itemDataOld =  pdcHandler.retrieveEmbeddedInformation(oldItem)
        val itemDataNew = pdcHandler.retrieveEmbeddedInformation(itemStack ?: ItemStack(Material.AIR))
        //if (itemDataOld != null && (itemStack == null || )){
        //    pdcHandler.plugin.handleSwitch(player, )
        // }

        setLastPassEquipment(uuid, equipmentSlot, itemStack)
    }
    
    override fun run() {
        pdcHandler.plugin.server.onlinePlayers.forEach { 
            val equipment = it.equipment ?: return@forEach
            pdcHandler.plugin.schedule {

                val hand = equipment.itemInMainHand?.clone()
                val offHand = equipment.itemInOffHand?.clone()
                val feet = equipment.boots?.clone()
                val legs = equipment.leggings?.clone()
                val chest = equipment.chestplate?.clone()
                val helmet = equipment.helmet?.clone()
                val uuid = it.uniqueId
                switchContext(SynchronizationContext.ASYNC)
                checkData(it, uuid, hand, EquipmentSlot.HAND)
                checkData(it, uuid, offHand, EquipmentSlot.OFF_HAND)
                checkData(it, uuid, feet, EquipmentSlot.FEET)
                checkData(it, uuid, legs, EquipmentSlot.LEGS)
                checkData(it, uuid, chest, EquipmentSlot.CHEST)
                checkData(it, uuid, helmet, EquipmentSlot.HEAD)
                
            }
        }
    }
}