/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Ktor
    implementation(libs.ktor.core)
    api(libs.ktor.client.okhttp)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.contentNegotiation)
    testImplementation(libs.ktor.client.mock)

    // Serialization JSON
    api(libs.kotlinx.serialization.json)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
}