plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin multiplatform implementation of the clipboard api that just copy everything in memory"

configureAndroid("src/androidMain") {
    namespace = "tz.co.asoft.klip"
    defaultConfig {
        minSdk = 8
    }
}

kotlin {
    if (Targeting.ANDROID) androidTarget { library() }
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
                api(projects.klipApi)
            }
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(kotlinx.browser)
            }
        }

        val todoMain by creating {
            dependsOn(commonMain)
        }

        (osxTargets + linuxTargets).forEach {
            val main by it.compilations.getting {}
            main.defaultSourceSet {
                dependsOn(todoMain)
            }
        }

        val commonTest by getting {
            dependencies {
                api(libs.kommander.core)
            }
        }
    }
}