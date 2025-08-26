package id.mohekkus.example.storage

import id.mohekkus.enumstore.EnumStoreCollection
import id.mohekkus.enumstore.EnumStoreShared
import id.mohekkus.enumstore.PartOf

enum class Variable: EnumStoreCollection {
    GREETING
}

@PartOf(Variable::class)
enum class SubVariable: EnumStoreShared {
    SUBGREETING
}