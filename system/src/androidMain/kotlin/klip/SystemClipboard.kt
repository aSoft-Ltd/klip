package klip

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import klip.internal.AbstractClipboard

actual class SystemClipboard(private val context: Context) : AbstractClipboard(), Clipboard {
    private val native by lazy { context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }

    actual override suspend fun entry(): ClipEntry? {
        val clip = native
        if (clip.hasPrimaryClip() && clip.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) == true) {
            val item = clip.primaryClip?.getItemAt(0) ?: return null
            return PlainText(item.text.toString())
        }
        return null
    }

    actual override suspend fun set(entry: ClipEntry) {
        if (entry !is PlainTextClipEntry) error("Only TextClipEntry is supported by this Clipboard at the moment")
        val clip = ClipData.newPlainText(entry.content, entry.content)
        native.setPrimaryClip(clip)
    }
}