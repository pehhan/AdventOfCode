package adventofcode

import java.nio.charset.StandardCharsets

object FileReader {

    fun getResource(path: String): String {
        return FileReader::class.java.classLoader?.getResourceAsStream(path)?.readBytes()?.toString(StandardCharsets.UTF_8) ?: ""
    }
}