package klip

import klip.internal.AbstractClipboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection


actual class SystemClipboard : AbstractClipboard(), Clipboard {
    private val native by lazy { Toolkit.getDefaultToolkit().systemClipboard }

    actual override suspend fun entry(): ClipEntry? {
        val data = withContext(Dispatchers.IO) {
            native.getData(DataFlavor.stringFlavor)
        } as? String? ?: return null
        return PlainText(data)
    }

    actual override suspend fun set(entry: ClipEntry) {
        if (entry !is PlainTextClipEntry) error("Only TextClipEntry is supported by this Clipboard at the moment")
        val text = entry.content
        val stringSelection = StringSelection(text)
        native.setContents(stringSelection, null)
    }
}