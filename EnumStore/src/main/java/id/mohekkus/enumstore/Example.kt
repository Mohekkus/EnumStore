package id.mohekkus.enumstore

import id.mohekkus.enumstore.Pero.Companion.ppp

enum class Example: Pero {
    DUCK,
    PECKING
}

interface Pero {
    companion object {
        fun Enum<*>.ppp() =
            EnumStoreExtension.getList(name)
    }
}

class Oui {
    fun main() {
        Example.PECKING.ppp()
    }
}