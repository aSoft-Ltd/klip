package klip

import android.content.Context
import klip.internal.AbstractClipboard
import koncurrent.Later
import koncurrent.TODOLater

actual class SystemClipboard(private val context: Context) : AbstractClipboard(), Clipboard {
    actual override fun data(): Later<ClipData> = TODOLater()
    actual override fun set(data: ClipData): Later<Unit> = TODOLater()
}