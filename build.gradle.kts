val ideStarterVersion = "252.28539.54"
plugins {
    kotlin("jvm") version "2.3.21-RC2"
}

group = "sdet.task"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven(url = "https://cache-redirector.jetbrains.com/intellij-dependencies")
    maven(url = "https://www.jetbrains.com/intellij-repository/releases")
    maven(url = "https://www.jetbrains.com/intellij-repository/snapshots")
    maven(url = "https://download.jetbrains.com/teamcity-repository")
    maven(url = "https://cache-redirector.jetbrains.com/packages.jetbrains.team/maven/p/grazi/grazie-platform-public")
}

dependencies {
    testImplementation("com.jetbrains.intellij.tools:ide-starter-squashed:$ideStarterVersion")
    testImplementation("com.jetbrains.intellij.tools:ide-starter-junit5:$ideStarterVersion")
    testImplementation("com.jetbrains.intellij.tools:ide-starter-driver:$ideStarterVersion")

    testImplementation("com.jetbrains.intellij.driver:driver-client:$ideStarterVersion")
    testImplementation("com.jetbrains.intellij.driver:driver-sdk:$ideStarterVersion")
    testImplementation("com.jetbrains.intellij.driver:driver-model:$ideStarterVersion")

    testImplementation("com.jetbrains.intellij.tools:ide-performance-testing-commands:$ideStarterVersion")

    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.kodein.di:kodein-di-jvm:7.20.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.10.1")
}

tasks.test {
    useJUnitPlatform()

    javaLauncher.set(
        javaToolchains.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    )

    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
    }
}

kotlin {
    jvmToolchain(25)
}