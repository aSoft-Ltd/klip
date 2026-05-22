@file:OptIn(ExperimentalForeignApi::class)

package klip

import klip.internal.AbstractClipboard
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.free
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.plus
import kotlinx.cinterop.set
import kotlinx.cinterop.toKString
import platform.posix.FILE
import platform.posix.fgets
import platform.posix.fprintf
import platform.posix.getenv
import platform.posix.memcpy
import platform.posix.pclose
import platform.posix.popen


actual class SystemClipboard : AbstractClipboard(), Clipboard {

    actual override suspend fun entry(): ClipEntry? {
        val pipe = rpipe() ?: return null
        val length = 0

        // 4. Initialize buffer variables
        var capacity = 256
        var currentLength = 0
        // Allocate initial C memory block
        var contentBuffer: CPointer<ByteVar> = nativeHeap.allocArray(capacity)

        // Temporary stack buffer for reading chunks
        val chunkSize = 256

        try {
            // 5. Read loop using native fgets
            memScoped {
                // Allocate a temporary native array for fgets to write into
                val tempNativeBuf = allocArray<ByteVar>(chunkSize)

                while (fgets(tempNativeBuf, chunkSize, pipe) != null) {
                    val bufStr = tempNativeBuf.toKString()
                    val bufLen = bufStr.length

                    // Resize native buffer if capacity is reached
                    if (currentLength + bufLen >= capacity) {
                        capacity *= 2
                        val newBuffer = nativeHeap.allocArray<ByteVar>(capacity)
                        // Copy existing data to the expanded buffer
                        memcpy(newBuffer, contentBuffer, currentLength.convert())
                        nativeHeap.free(contentBuffer)
                        contentBuffer = newBuffer
                    }

                    // Copy new chunk into our master contentBuffer
                    val destinationOffset = contentBuffer + currentLength
                    memcpy(destinationOffset, tempNativeBuf, bufLen.convert())
                    currentLength += bufLen
                }
            }
        } finally {
            // Ensure the pipe always closes
            pclose(pipe)
        }

        // 6. Null-terminate the string and convert to Kotlin String
        contentBuffer[currentLength] = 0.toByte()
        val result = contentBuffer.toKString()

        // Free the allocated native memory block
        nativeHeap.free(contentBuffer)
        return PlainText(result)
    }

    private fun rpipe(): CPointer<FILE>? = when (getenv("XDG_SESSION_TYPE")?.toKString()) {
        "wayland" -> popen("wl-paste", "r");
        "x11" -> popen("xclip -selection clipboard -o", "r")
        else -> return null
    }

    private fun wpipe(): CPointer<FILE>? = when (getenv("XDG_SESSION_TYPE")?.toKString()) {
        "wayland" -> popen("wl-copy", "w");
        "x11" -> popen("xclip -selection clipboard", "w")
        else -> return null
    }

    actual override suspend fun set(entry: ClipEntry) {
        if (entry !is PlainTextClipEntry) error("Only TextClipEntry is supported by this Clipboard at the moment")
        val pipe = wpipe() ?: return
        fprintf(pipe, "%s\n", entry.content)
        pclose(pipe)
    }
}