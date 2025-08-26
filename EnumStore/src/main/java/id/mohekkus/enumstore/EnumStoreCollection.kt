package id.mohekkus.enumstore

import kotlin.reflect.KClass

interface EnumStoreMarker

interface EnumStoreCollection: EnumStoreMarker

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PartOf(val collection: KClass<out EnumStoreCollection>)

interface EnumStoreShared: EnumStoreMarker