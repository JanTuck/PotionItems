package me.jantuck.potionitems.listeners

import me.jantuck.potionitems.PotionItems
import me.jantuck.potionitems.pdc.PDCHandler
import me.jantuck.potionitems.types.SwitchAction
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

class HeldItemSwitchEvent(private val pdcHandler: PDCHandler) : Listener {

    @EventHandler
    fun PlayerItemHeldEvent.onEvent(){
        val itemNew = player.inventory.getItem(this.newSlot)
        val oldItem = player.inventory.getItem(this.previousSlot)

        if (oldItem != null){
            val oldItemInfo = pdcHandler.retrieveEmbeddedInformation(oldItem)
            if (oldItemInfo != null && oldItemInfo.isNotEmpty())
                pdcHandler.plugin.handleSwitch(player, oldItem, SwitchAction.FROM, oldItemInfo)
        }

        if (itemNew != null && newSlot != previousSlot){
            val newItemInfo = pdcHandler.retrieveEmbeddedInformation(itemNew)
            if (newItemInfo != null && newItemInfo.isNotEmpty())
                pdcHandler.plugin.handleSwitch(player, itemNew, SwitchAction.TO, newItemInfo)
        }


    }
}