package klip.internal

import klip.Clipboard
import klip.PlainText

abstract class AbstractClipboard : Clipboard {
    override suspend fun set(text: String) = set(PlainText(text))
}