pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "kommander", "kollections", "kevlar", "koncurrent",
    "kase"
).forEach { includeBuild("../$it") }

rootProject.name = "klip"

// submodules
includeSubs("klip", ".", "api", "local", "browser")
