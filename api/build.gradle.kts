plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "An kotlin multiplaform abstraction for copying things to the clipboard"

kotlin {
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js { library() }
    if (Targeting.WASM) wasmJs { library() }
    val osxTargets = if (Targeting.OSX) osxTargets() else listOf()
//    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
//    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.koncurrent.later.core)
            }
        }

        val commonTest by getting {
            dependencies {
                api(libs.kommander.core)
            }
        }
    }
}