plugins {
    kotlin("multiplatform") version "1.9.0"
    `maven-publish`
}

group = "me.mark"
version = "0.0.2"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser()
        nodejs()
    }
    mingwX64("windows")
    linuxX64("linux")

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.2.1")
                implementation(kotlin("test"))
            }
        }
    }
}
