package klip.internal

import klip.TextClipData

@PublishedApi
internal data class TextClipDataImpl(override val content: String) : TextClipData {
    override val asText = content
}