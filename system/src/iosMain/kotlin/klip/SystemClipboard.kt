package klip

import klip.internal.AbstractClipboard
import platform.UIKit.UIPasteboard


actual class SystemClipboard : AbstractClipboard(), Clipboard {
    private val native by lazy { UIPasteboard.generalPasteboard }

    actual override suspend fun entry(): ClipEntry? {
        val data = native.string ?: return null
        return PlainText(data)
    }

    actual override suspend fun set(entry: ClipEntry) {
        if (entry !is PlainTextClipEntry) error("Only TextClipEntry is supported by this Clipboard at the moment")
        native.string = entry.content
    }
}