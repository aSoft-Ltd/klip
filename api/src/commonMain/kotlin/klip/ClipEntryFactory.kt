@file:Suppress("NOTHING_TO_INLINE")

package klip

import klip.internal.PlainTextClipEntryImpl

inline fun PlainText(value: String): PlainTextClipEntry = PlainTextClipEntryImpl(value)