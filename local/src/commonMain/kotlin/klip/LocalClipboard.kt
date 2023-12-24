package klip

import klip.internal.AbstractClipboard
import koncurrent.FailedLater
import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch

class LocalClipboard : AbstractClipboard() {
    private var content: ClipData? = null
    override fun data() = when(val c = content) {
        null -> FailedLater("No data on the clipboard")
        else -> Later(c)
    }

    override fun set(data: ClipData) : Later<Unit> {
        content = data
        return Later(Unit)
    }
}