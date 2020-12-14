package me.jantuck.potionitems.pdc

import me.jantuck.potionitems.types.PotionContainer
import me.jantuck.potionitems.types.PotionLength
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffectType
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream

class PDCAdapter : PersistentDataType<ByteArray, List<PotionContainer>> {
    override fun getPrimitiveType(): Class<ByteArray> {
        return ByteArray::class.java
    }

    private val plebClass = listOf<PotionContainer>()
    override fun getComplexType(): Class<List<PotionContainer>> {
        return plebClass.javaClass
    }

    override fun toPrimitive(complex: List<PotionContainer>, context: PersistentDataAdapterContext): ByteArray {
        val array = ByteArrayOutputStream()
        val dataOutputStream = DataOutputStream(array)
        dataOutputStream.write(complex.size)

        complex.forEach {
            dataOutputStream.writeUTF(it.potionType.name)
            dataOutputStream.write(it.amplifier)
            dataOutputStream.write(it.potionLengthType.getID())
            dataOutputStream.writeLong(it.potionLength)
            dataOutputStream.writeLong(it.activatedAt)
        }

        return array.toByteArray()
    }

    override fun fromPrimitive(primitive: ByteArray, context: PersistentDataAdapterContext): List<PotionContainer> {
        val output = mutableListOf<PotionContainer>()
        val dataInputStream = DataInputStream(ByteArrayInputStream(primitive))
        for (i in 0 until dataInputStream.read()){
            val name = dataInputStream.readUTF()
            val amplifier = dataInputStream.read()
            val id = dataInputStream.read()
            val time = dataInputStream.readLong()
            val activatedAt = dataInputStream.readLong()

            output.add(
                PotionContainer(
                    PotionEffectType.getByName(
                        name
                    )!!,
                    amplifier,
                    if (id == 0) PotionLength.INFINITE else PotionLength.FINITE,
                    time,
                    activatedAt
                )
            )
        }

        return output
    }

}