package klip

import klip.internal.AbstractClipboard
import koncurrent.Later
import koncurrent.TODOLater

actual class SystemClipboard : AbstractClipboard(), Clipboard {
    actual override fun data(): Later<ClipData> = TODOLater()
    actual override fun set(data: ClipData): Later<Unit> = TODOLater()
}