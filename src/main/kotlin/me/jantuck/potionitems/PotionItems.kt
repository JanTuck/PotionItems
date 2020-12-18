package me.jantuck.potionitems

import com.okkero.skedule.schedule
import me.jantuck.potionitems.listeners.HeldItemSwitchEvent
import me.jantuck.potionitems.pdc.PDCHandler
import me.jantuck.potionitems.types.PotionContainer
import me.jantuck.potionitems.types.PotionLength
import me.jantuck.potionitems.types.SwitchAction
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PotionItems : JavaPlugin(), Listener {

    private val pdcHandler = PDCHandler(this)

    override fun onEnable() {
        server.pluginManager.registerEvents(HeldItemSwitchEvent(pdcHandler), this)
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun AsyncPlayerChatEvent.chat(){
        this@PotionItems.schedule {
            if (this@chat.message == "h"){
                val itemStack = ItemStack(Material.DIAMOND)
                this@chat.player.inventory.addItem(
                    itemStack
                )
            }
        }
    }

    fun handleSwitch(player: Player, itemStack: ItemStack, action: SwitchAction, data: List<PotionContainer>){
        if (action == SwitchAction.FROM){
            println(data)
            data.forEach {
                if (it.activatedAt != -1L && it.potionLengthType != PotionLength.INFINITE) {
                    val now = System.currentTimeMillis()
                    val activeDuration = now - it.activatedAt
                    it.potionLength = it.potionLength - activeDuration
                    it.activatedAt = -1
                    player.removePotionEffect(it.potionType)
                }
            }
            println(data)
            pdcHandler.replaceEmbeddedInformation(itemStack, data.filterNot { it.potionLength <= 0L })
        }
        else if (action == SwitchAction.TO){
            data.forEach {
                it.activatedAt = System.currentTimeMillis()
                player.addPotionEffect(PotionEffect(
                    it.potionType,
                    (it.potionLength / 50).toInt(),
                    it.amplifier
                ))
            }
            pdcHandler.replaceEmbeddedInformation(itemStack, data.filterNot { it.potionLength <= 0L  })
        }
    }
}