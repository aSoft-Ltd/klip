@file:Suppress("NOTHING_TO_INLINE")

package klip

import klip.internal.TextClipDataImpl

inline fun TextClipData(value: String): TextClipData = TextClipDataImpl(value)