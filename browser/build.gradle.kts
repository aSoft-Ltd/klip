plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

description = "A kotlin/js implementation of the clipboard manager"

kotlin {
    if (Targeting.JS) js(IR) { library() }

    sourceSets {
        val commonMain by getting {
            dependencies {
				api(libs.klip.api)
            }
        }

        val commonTest by getting {
            dependencies {
                api(libs.kommander.core)
            }
        }
    }
}