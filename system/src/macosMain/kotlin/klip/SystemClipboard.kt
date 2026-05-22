package klip

import klip.internal.AbstractClipboard
import platform.AppKit.NSPasteboard
import platform.AppKit.NSPasteboardTypeString


actual class SystemClipboard : AbstractClipboard(), Clipboard {
    private val native by lazy { NSPasteboard.generalPasteboard }

    actual override suspend fun entry(): ClipEntry? {
        val data = native.stringForType(NSPasteboardTypeString) ?: return null
        return PlainText(data)
    }

    actual override suspend fun set(entry: ClipEntry) {
        if (entry !is PlainTextClipEntry) error("Only TextClipEntry is supported by this Clipboard at the moment")
        native.clearContents()
        native.setString(entry.content, NSPasteboardTypeString)
    }
}