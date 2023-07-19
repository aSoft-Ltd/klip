package klip.internal

import klip.Clipboard
import klip.TextClipData

abstract class AbstractClipboard : Clipboard {
    override fun setText(content: String) = set(TextClipData(content))
}