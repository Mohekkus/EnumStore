package id.mohekkus.enumstore.processor.generator

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

class EncryptedEnumGenerator(
    environment: SymbolProcessorEnvironment,
    private val pkg: String,
    private val enumName: String,
    private val keyAlias: String,
    private val useSalt: Boolean
) {

    private val envCodeGenerator = environment.codeGenerator

    fun generate() {
        val className = "${enumName}Encrypted"
        val handlerType = ClassName("id.mohekkus.enumstore.crypto", "EncryptedEnumHandler")
        val enumClass = ClassName(pkg, enumName)

        val fileSpec = FileSpec.builder(pkg, className)
            .addType(
                TypeSpec.objectBuilder(className)
                    .addSuperinterface(handlerType.parameterizedBy(enumClass))
                    .addProperty(
                        PropertySpec.builder("keyAlias", String::class)
                            .initializer("%S", keyAlias)
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("encrypt")
                            .addModifiers(KModifier.OVERRIDE)
                            .addParameter("value", enumClass)
                            .returns(String::class)
                            .addStatement(
                                "return AES.encrypt(value.name, AppEncryptionKeys.resolve(keyAlias), %L)",
                                if (useSalt) "EncryptionSaltRegistry.forEnum(\"$enumName\")" else "null"
                            )
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("decrypt")
                            .addModifiers(KModifier.OVERRIDE)
                            .addParameter("encrypted", String::class)
                            .returns(enumClass)
                            .addStatement(
                                "val name = AES.decrypt(encrypted, AppEncryptionKeys.resolve(keyAlias), %L)",
                                if (useSalt) "EncryptionSaltRegistry.forEnum(\"$enumName\")" else "null"
                            )
                            .addStatement("return %T.valueOf(name)", enumClass)
                            .build()
                    )
                    .build()
            )
            .build()

        fileSpec.apply {
            envCodeGenerator.createNewFile(
                dependencies = Dependencies(false),
                packageName = packageName,
                fileName = name
            ).bufferedWriter()
                .use { writer ->
                    writeTo(writer)
                }
        }
    }
}