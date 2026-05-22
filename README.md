# klip

[![Maven Central](https://img.shields.io/maven-central/v/tz.co.asoft/klip-api.svg)](https://search.maven.org/search?q=g:tz.co.asoft%20AND%20klip)

A multiplatform abstraction for dealing with clipboards.

## Features
- Unified API for Clipboard operations.
- Support for System Clipboard across multiple platforms.
- In-memory `LocalClipboard` for testing or internal app usage.
- Suspendable API for non-blocking operations.

## Supported Platforms

| Module      | JVM | Android | iOS | macOS | watchOS/tvOS | Linux | Windows | JS/Wasm |
|:------------|:---:|:-------:|:---:|:-----:|:------------:|:-----:|:-------:|:-------:|
| klip-api    |  ✅  |    ✅    |  ✅  |   ✅   |      ✅       |   ✅   |    ✅    |    ✅    |
| klip-local  |  ✅  |    ✅    |  ✅  |   ✅   |      ✅       |   ✅   |    ✅    |    ✅    |
| klip-system |  ✅  |    ✅    |  ✅  |   ✅   |      ✅       |   ✅   |    ✅    |    ✅    |

## Setup

Add the dependency to your `build.gradle.kts` file:

### Multiplatform
```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("tz.co.asoft:klip-system:<version>")
            }
        }
    }
}
```

### Single Platform (e.g., JVM)
```kotlin
dependencies {
    implementation("tz.co.asoft:klip-system:<version>")
}
```

## Usage

### System Clipboard
The `SystemClipboard` class provides access to the platform's native clipboard.

```kotlin
val clipboard = SystemClipboard()

// Setting plain text
clipboard.set("Hello from Klip!")

// Getting current entry
val entry = clipboard.entry()
if (entry is PlainTextClipEntry) {
    println(entry.content)
}
```

### Local Clipboard
Useful for testing or when you need a clipboard scoped only to your application.

```kotlin
val clipboard = LocalClipboard()
clipboard.set("Secret data")
```

### Advanced Usage with ClipEntry
You can use `ClipEntry` for more structured data (currently primarily supports PlainText).

```kotlin
val entry = PlainText("Structured Text")
clipboard.set(entry)
```

## ClipEntry

Klip has been implemented with with extensibility in mind, and supports the following ClipEntry types: 


✅ PlainText (Current available)

❌ File/Blob (To be implemented)

❌ Image (To be implemented)

❌ Url (To be implemented)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributors

- [andylamax](https://github.com/andylamax)