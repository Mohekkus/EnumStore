plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
}

android {
    namespace = "id.mohekkus.enumstore"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core Datastore usage
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.datastore.preferences.rxjava3)

    // Poet + Processor
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinpoet)
    implementation(libs.symbol.processing.api)
}