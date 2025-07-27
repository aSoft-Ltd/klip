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
    if (Targeting.OSX) osxTargets() else listOf()
//    if (Targeting.NDK) ndkTargets() else listOf()
    if (Targeting.LINUX) linuxTargets() else listOf()
//    if (Targeting.MINGW) mingwTargets() else listOf()

    sourceSets {
        commonMain.dependencies {
            api(libs.koncurrent.later.core)
        }
    }
}