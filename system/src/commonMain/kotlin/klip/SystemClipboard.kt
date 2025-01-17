package klip

import klip.internal.AbstractClipboard
import koncurrent.Later

expect class SystemClipboard : AbstractClipboard, Clipboard {
    override fun set(data: ClipData): Later<Unit>
    override fun data(): Later<ClipData>
}