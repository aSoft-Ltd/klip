import klip.SystemClipboard
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class SystemClipboardTest {

    val clipboard = SystemClipboard()

    @Test
    fun should_be_able_to_add_to_clipboard() = runTest {
        clipboard.set("test me too")
    }

    @Test
    fun should_be_able_to_read_from_clipboard() = runTest {
        val data = clipboard.entry()?.asText
        println(data)
    }
}