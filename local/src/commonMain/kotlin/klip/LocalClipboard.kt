package klip

import klip.internal.AbstractClipboard
import koncurrent.FailedLater
import koncurrent.Later

class LocalClipboard : AbstractClipboard() {
    private var content: ClipData? = null
    override fun data() = when(val c = content) {
        null -> FailedLater("No data on the clipboard")
        else -> Later(c)
    }

    override fun set(data: ClipData) {
        content = data
    }
}