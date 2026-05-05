// build.gradle.kts
plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.0"
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.0")
    implementation("io.ktor:ktor-server-netty:2.3.0")
    implementation("io.ktor:ktor-server-html-builder:2.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.9.1")
    implementation("ch.qos.logback:logback-classic:1.4.5")
}
