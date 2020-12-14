package me.jantuck.potionitems.pdc

import me.jantuck.potionitems.PotionItems
import me.jantuck.potionitems.types.PotionContainer
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class PDCHandler(val plugin: PotionItems) {
    private val key = NamespacedKey(plugin, "potioncontainers");
    private val typeAdapter = PDCAdapter()
    fun retrieveEmbeddedInformation(itemStack: ItemStack) : List<PotionContainer>? {
            return itemStack.itemMeta.persistentDataContainer.get(key, typeAdapter)
    }

    fun replaceEmbeddedInformation(itemStack: ItemStack, information: List<PotionContainer>){
        val itemMeta1 = itemStack.itemMeta
        val container = itemMeta1.persistentDataContainer
        if (information.isEmpty())
            container.remove(key)
        else
            container.set(
                key,
                typeAdapter,
                information
            )
        itemStack.itemMeta = itemMeta1
    }

}