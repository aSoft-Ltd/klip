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
        minSdk = 11
    }
}

kotlin {
    if (Targeting.ANDROID) androidTarget { library() }
    if (Targeting.JVM) jvm { library() }
    if (Targeting.JS) js { library() }
    if (Targeting.WASM) wasmJs { library() }
    val osxTargets = if (Targeting.OSX) (iosTargets() + macOsTargets()) else listOf()
//    val ndkTargets = if (Targeting.NDK) ndkTargets() else listOf()
    val linuxTargets = if (Targeting.LINUX) linuxTargets() else listOf()
    val mingwTargets = if (Targeting.MINGW) mingwTargets() else listOf()

    applyDefaultHierarchyTemplate()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.klipApi)
                implementation(kotlinx.coroutines.core)
            }
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(kotlinx.browser)
            }
        }

        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kommander.core)
            implementation(libs.kommander.coroutines)
            implementation(kotlinx.coroutines.test)
        }
    }
}