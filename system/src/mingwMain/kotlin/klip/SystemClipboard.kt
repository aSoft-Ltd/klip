@file:OptIn(ExperimentalForeignApi::class)

package klip

import klip.internal.AbstractClipboard
import kotlinx.cinterop.CValues
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.toKStringFromUtf16
import kotlinx.cinterop.wcstr
import platform.windows.CF_UNICODETEXT
import platform.windows.CloseClipboard
import platform.windows.EmptyClipboard
import platform.windows.GMEM_MOVEABLE
import platform.windows.GetClipboardData
import platform.windows.GlobalAlloc
import platform.windows.GlobalFree
import platform.windows.GlobalLock
import platform.windows.GlobalUnlock
import platform.windows.IsClipboardFormatAvailable
import platform.windows.OpenClipboard
import platform.windows.SetClipboardData
import platform.windows.USHORTVar


actual class SystemClipboard : AbstractClipboard(), Clipboard {

    actual override suspend fun entry(): ClipEntry? {
        // 1. Open the system clipboard
        if (OpenClipboard(null) == 0) {
            return null
        }

        try {
            // 2. Check if the clipboard contains text format data
            if (IsClipboardFormatAvailable(CF_UNICODETEXT.toUInt()) == 0) {
                return null // No text data available to extract
            }

            // 3. Retrieve the global memory handle to the clipboard data
            val hGlobal = GetClipboardData(CF_UNICODETEXT.toUInt()) ?: return null

            // 4. Lock the global memory block to secure a stable pointer address
            val pGlobal = GlobalLock(hGlobal) ?: return null

            try {
                // Cast the pointer to a UTF-16 pointer (equivalent to wchar_t*)
                val wideCharPointer = pGlobal.reinterpret<USHORTVar>()

                // 5. Safely convert the null-terminated UTF-16 C-string into a Kotlin String
                return PlainText(wideCharPointer.toKStringFromUtf16())
            } finally {
                // Always unlock the global memory block when finished reading
                GlobalUnlock(hGlobal)
            }
        } finally {
            // 6. Always close the clipboard to unlock it for other processes
            CloseClipboard()
        }
    }

    actual override suspend fun set(entry: ClipEntry) {
        if (entry !is PlainTextClipEntry) error("Only TextClipEntry is supported by this Clipboard at the moment")
        if (OpenClipboard(null) == 0) return

        try {
            // 2. Clear the current clipboard contents and claim ownership
            if (EmptyClipboard() == 0) return

            // 3. Convert Kotlin String to a native null-terminated UTF-16 wide string
            // .wcstr produces a CValues<USHORTVar> container type
            val wideText: CValues<USHORTVar> = entry.content.wcstr

            // Calculate size: wide characters require 2 bytes each
            val sizeInBytes = (wideText.size * sizeOf<USHORTVar>()).toLong()

            // 4. Allocate global moveable memory block required by the clipboard
            val hGlobal = GlobalAlloc(GMEM_MOVEABLE.toUInt(), sizeInBytes.convert()) ?: return

            // 5. Lock the global memory block to get a stable target address pointer
            val pGlobal = GlobalLock(hGlobal)
            if (pGlobal != null) {
                // Place wide character values into the locked global memory segment
                wideText.place(pGlobal.reinterpret())

                // Unlock the memory block so Windows can pass it to other apps
                GlobalUnlock(hGlobal)
            } else {
                GlobalFree(hGlobal)
                return
            }

            // 6. Place the data onto the clipboard using the Unicode text format flag
            if (SetClipboardData(CF_UNICODETEXT.toUInt(), hGlobal) == null) {
                GlobalFree(hGlobal) // Free memory if the system rejected the clipboard data
                return
            }
            return
        } finally {
            // 7. Always close the clipboard to unlock it for other applications
            CloseClipboard()
        }
    }
}