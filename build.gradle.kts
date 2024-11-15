plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    dependencies {
        // Specify a compatible version for Kotlin (e.g., 1.9.0)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath("com.android.tools.build:gradle:8.0.1")
        classpath("com.google.gms:google-services:4.4.2") // Ensure the correct version
    }
}
