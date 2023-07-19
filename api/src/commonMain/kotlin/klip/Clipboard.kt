package klip

import koncurrent.Later

interface Clipboard {
    fun data(): Later<ClipData>

    fun set(data: ClipData): Later<Unit>

    fun setText(content: String): Later<Unit>
}