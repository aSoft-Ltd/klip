@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package klip

import koncurrent.Later
import kotlinx.JsExport

interface Clipboard {
    fun data(): Later<ClipData>

    fun set(data: ClipData): Later<Unit>

    fun setText(content: String): Later<Unit>
}