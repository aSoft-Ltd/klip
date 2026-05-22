package klip

import klip.internal.AbstractClipboard

expect class SystemClipboard : AbstractClipboard, Clipboard {
    override suspend fun set(entry: ClipEntry)
    override suspend fun entry(): ClipEntry?
}