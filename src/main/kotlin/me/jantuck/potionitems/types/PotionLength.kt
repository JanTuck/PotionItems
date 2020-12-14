package me.jantuck.potionitems.types

enum class PotionLength(private val i: Int) {
    INFINITE(0), FINITE(1);

    fun getID() : Int = i
}