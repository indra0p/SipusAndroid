plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sipus.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.lifecycle.viewmodel.compose)

    api(platform(libs.compose.bom))
    api(libs.compose.ui)
    api(libs.compose.ui.graphics)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.material3)
    api(libs.compose.material.icons)
    debugApi(libs.compose.ui.tooling)

    api(libs.navigation.compose)
    api(libs.hilt.android)
    ksp(libs.hilt.compiler)
    api(libs.hilt.navigation.compose)

    // Networking
    api(libs.retrofit)
    api(libs.retrofit.moshi)
    api(libs.okhttp)
    api(libs.okhttp.logging)
    api(libs.moshi)
    api(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    // Room
    api(libs.room.runtime)
    api(libs.room.ktx)
    ksp(libs.room.compiler)

    // DataStore
    api(libs.datastore.preferences)

    // Coil
    api(libs.coil.compose)

    // ML Kit Barcode
    api(libs.mlkit.barcode)

    // CameraX
    api(libs.camerax.core)
    api(libs.camerax.camera2)
    api(libs.camerax.lifecycle)
    api(libs.camerax.view)

    // Coroutines
    api(libs.coroutines.core)
    api(libs.coroutines.android)

    // Serialization
    api(libs.kotlinx.serialization)

    // QR Code generation
    api("com.google.zxing:core:3.5.3")
}
