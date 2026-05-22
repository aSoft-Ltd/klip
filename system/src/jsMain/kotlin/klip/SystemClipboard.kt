package klip

import klip.internal.AbstractClipboard
import kotlinx.browser.window
import kotlinx.coroutines.await

actual class SystemClipboard : AbstractClipboard(), Clipboard {
    val native by lazy { window.navigator.clipboard }
    actual override suspend fun entry(): ClipEntry? {
        val text = native.readText().await()
        return PlainText(text)
    }
    actual override suspend fun set(entry: ClipEntry) {
        if (entry !is PlainTextClipEntry) error("Only TextClipEntry is supported by this Clipboard at the moment")
        native.writeText(entry.content)
    }
}