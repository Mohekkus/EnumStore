package id.mohekkus.enumstore

import kotlin.reflect.KClass

interface EnumStoreCollection

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PartOf(val collection: KClass<out EnumStoreCollection>)

interface EnumStoreShared: EnumStoreCollection