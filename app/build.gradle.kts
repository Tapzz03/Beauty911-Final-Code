plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.beauty911"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.beauty911"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // Make sure this matches your Compose version
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    // Jetpack Compose dependencies
    implementation("androidx.compose.ui:ui:1.5.0") // Replace with the latest stable version
    implementation("androidx.compose.material3:material3:1.1.1") // Replace with the latest stable version
    implementation("androidx.activity:activity-compose:1.7.2") // Replace with the latest stable version
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation ("com.google.firebase:firebase-database:21.0.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")

    // Firebase core SDK
    implementation("com.google.firebase:firebase-analytics:21.3.0")

    // Firestore SDK
    implementation("com.google.firebase:firebase-firestore:25.1.1") // Corrected version

    // Other Firebase SDKs you might be using, like Firebase Authentication
    implementation("com.google.firebase:firebase-auth:23.1.0")

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")

    // Optional: For UI testing with Compose
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.0")
}

