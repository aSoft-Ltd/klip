@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package klip

import koncurrent.Later
import koncurrent.later.then
import koncurrent.later.andThen
import koncurrent.later.andZip
import koncurrent.later.zip
import koncurrent.later.catch
import kotlinx.JsExport

interface Clipboard {
    fun data(): Later<ClipData>

    fun set(data: ClipData): Later<Unit>

    fun setText(content: String): Later<Unit>
}