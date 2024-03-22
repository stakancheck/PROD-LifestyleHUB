/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "ru.stakancheck.lifestylehub"
    compileSdk = 34

    // App signing properties
    val keystorePropertiesFile = rootProject.file(".signing/signing.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    // API keys properties
    val secretsPropertiesFile = rootProject.file("secrets.properties")
    val secretsProperties = Properties()
    secretsProperties.load(FileInputStream(secretsPropertiesFile))

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
        buildConfigField(
            "String",
            "OPEN_WEATHER_API_KEY",
            secretsProperties["OPEN_WEATHER_API_KEY"] as String
        )

        configurations.all {
            resolutionStrategy {
                force("androidx.emoji2:emoji2-views-helper:1.3.0")
                force("androidx.emoji2:emoji2:1.3.0")
            }
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose and Material 3
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Tests
    testApi(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Tooling
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Dependency injection
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.coroutines)

    // Modules
    implementation(project(":features:main-feed"))
    implementation(project(":data"))
    implementation(project(":api"))
    api(project(":uikit"))
}