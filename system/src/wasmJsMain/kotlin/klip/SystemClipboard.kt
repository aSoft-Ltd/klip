package klip

import klip.internal.AbstractClipboard
import koncurrent.Later
import koncurrent.later.asLater
import kotlinx.browser.window

actual class SystemClipboard : AbstractClipboard(), Clipboard {

    val native by lazy { window.navigator.clipboard }

    actual override fun data(): Later<ClipData> = native.readText().asLater().then { TextClipData(it.toString()) }

    actual override fun set(data: ClipData) = native.writeText(data.asText).then { data.asText.toJsString() }.asLater().then { Unit }
}