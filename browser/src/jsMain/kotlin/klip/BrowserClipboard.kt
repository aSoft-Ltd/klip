package klip

import klip.internal.AbstractClipboard
import koncurrent.later.asLater
import kotlinx.browser.window

class BrowserClipboard : AbstractClipboard() {

    val clipboard by lazy { window.navigator.clipboard }

    override fun data() = clipboard.readText().then { TextClipData(it) }.asLater()

    override fun set(data: ClipData) = clipboard.writeText(data.asText).asLater()
}