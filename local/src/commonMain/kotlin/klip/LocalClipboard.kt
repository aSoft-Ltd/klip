package klip

import klip.internal.AbstractClipboard

class LocalClipboard : AbstractClipboard() {
    private var content: ClipEntry? = null
    override suspend fun entry() = content

    override suspend fun set(entry: ClipEntry) {
        content = entry
    }
}