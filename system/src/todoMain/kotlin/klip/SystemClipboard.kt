package klip

import klip.internal.AbstractClipboard

actual class SystemClipboard : AbstractClipboard(), Clipboard {
    actual override suspend fun entry(): ClipEntry? {
        TODO()
    }

    actual override suspend fun set(entry: ClipEntry) {

    }
}