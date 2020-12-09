package adventofcode.year2015.day4

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

object Task1 {
    fun findLowestPossibleNumber(secret: String): Long {
        return findLowestPossibleNumber(secret, 5)
    }
}

object Task2 {
    fun findLowestPossibleNumber(secret: String): Long {
        return findLowestPossibleNumber(secret, 6)
    }
}

private fun findLowestPossibleNumber(secret: String, numberOfZeroes: Int): Long {
    val messageDigest = MessageDigest.getInstance("MD5")

    return generateSequence(0L) { it + 1 }
        .takeWhile {
            val key = secret + it.toString()
            messageDigest.update(key.toByteArray())
            val hash = DatatypeConverter.printHexBinary(messageDigest.digest())

            !hash.startsWith("0".repeat(numberOfZeroes))
        }
        .last() + 1
}