package id.mohekkus.enumstore.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import id.mohekkus.enumstore.processor.generator.EncryptedEnumGenerator

class EncryptedEnumProcessor(
    private val environment: SymbolProcessorEnvironment
): SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        symbolResolver(resolver).let { symbols ->
            symbols.forEach {
                it.generateEncryptedEnumHandle()
            }

        }
        return emptyList()
    }

    private fun symbolResolver(resolver: Resolver): Sequence<KSClassDeclaration> {
        return resolver
            .getSymbolsWithAnnotation("id.mohekkus.enumstore.annotations.EncryptedEnum")
            .filterIsInstance<KSClassDeclaration>()
    }

    private fun KSClassDeclaration.generateEncryptedEnumHandle() {
        val enumName = simpleName.asString()
        val packageName = packageName.asString()
        val annotation = annotations.first {
            it.shortName.asString() == "EncryptedEnum"
        }

        val keyAlias = annotation.arguments
            .firstOrNull { it.name?.asString() == "keyAlias" }
            ?.value as? String ?: ""
        val useSalt = annotation.arguments
            .firstOrNull { it.name?.asString() == "useSalt" }
            ?.value as? Boolean ?: true

        EncryptedEnumGenerator(
            this@EncryptedEnumProcessor.environment,
            packageName,
            enumName,
            keyAlias,
            useSalt
        ).generate()
    }
}