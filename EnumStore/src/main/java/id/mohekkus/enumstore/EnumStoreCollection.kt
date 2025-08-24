package id.mohekkus.enumstore

import kotlin.reflect.KClass

interface EnumStoreMarker

interface EnumStoreCollection

annotation class PartOf(val collection: KClass<out EnumStoreCollection>)
interface EnumStoreShared: EnumStoreCollection