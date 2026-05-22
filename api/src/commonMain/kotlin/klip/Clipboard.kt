@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package klip

import kotlinx.JsExport
import kotlinx.JsName

interface Clipboard {
    suspend fun entry(): ClipEntry?

    suspend fun set(entry: ClipEntry)

    @JsName("setPlainText")
    suspend fun set(text: String)
}