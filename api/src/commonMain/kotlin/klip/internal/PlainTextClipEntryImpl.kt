package klip.internal

import klip.PlainTextClipEntry

@PublishedApi
internal data class PlainTextClipEntryImpl(override val content: String) : PlainTextClipEntry {
    override val asText = content
}