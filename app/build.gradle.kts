import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "ru.stakancheck.lifestylehub"
    compileSdk = 34

    val keystorePropertiesFile = rootProject.file(".signing/signing.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    defaultConfig {
        applicationId = "ru.stakancheck.lifestylehub"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAliasRelease"] as String
            keyPassword = keystoreProperties["keyPasswordRelease"] as String
            storeFile = rootProject.file(".signing/keystore.jks")
            storePassword = keystoreProperties["storePassword"] as String
        }

        getByName("debug") {
            keyAlias = keystoreProperties["keyAliasDebug"] as String
            keyPassword = keystoreProperties["keyPasswordDebug"] as String
            storeFile = rootProject.file(".signing/keystore.jks")
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Default dependencies (by Android Studio)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Android UI
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // Material 3
    implementation(libs.androidx.material3)
    testApi(libs.junit)
    // Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // Tooling
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Custom dependencies
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Ktor
    implementation(libs.ktor.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    // Navigation
    implementation(libs.androidx.navigation.compose)
}