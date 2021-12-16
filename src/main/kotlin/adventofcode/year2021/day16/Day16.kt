package adventofcode.year2021.day16

import adventofcode.year2021.day16.PacketType.Literal
import java.lang.IllegalArgumentException

enum class PacketType(val value: Int) {
    Sum(0), Product(1), Minimum(2), Maximum(3), Literal(4), GreaterThan(5), LessThan(6), Equal(7);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

object Task1 {

    fun sumOfVersionNumbers(input: String): Int {
        return sumOfBinaryVersionNumbers(input.hexToBinary())
    }

    private fun sumOfBinaryVersionNumbers(binary: String): Int {
        val version = binary.take(3).toInt(2)
        val packetType = PacketType.valueOf(binary.drop(3).take(3).toInt(2))

        var sum = version

        if (packetType == Literal) {
            // Literal value
            var remaining = binary.drop(6)
            while (remaining.first() == '1') {
                remaining = remaining.drop(5)
            }

            // Now we have the '0' part of the literal value left
            remaining = remaining.drop(5)

            if (remaining.isNotEmpty() && !remaining.all { it == '0' }) {
                // We have more packets
                sum += sumOfBinaryVersionNumbers(remaining)
            }
        } else {
            // Operator
            val lengthTypeId = binary.drop(6).take(1).toInt(2)

            sum += if (lengthTypeId == 0) {
                sumOfBinaryVersionNumbers(binary.drop(3 + 3 +1 + 15))
            } else {
                sumOfBinaryVersionNumbers(binary.drop(3 + 3 + 1 + 11))
            }
        }

        return sum
    }
}

private fun String.hexToBinary(): String {
    return this.map { when (it) {
        '0' -> "0000"
        '1' -> "0001"
        '2' -> "0010"
        '3' -> "0011"
        '4' -> "0100"
        '5' -> "0101"
        '6' -> "0110"
        '7' -> "0111"
        '8' -> "1000"
        '9' -> "1001"
        'A' -> "1010"
        'B' -> "1011"
        'C' -> "1100"
        'D' -> "1101"
        'E' -> "1110"
        'F' -> "1111"
        else -> throw IllegalArgumentException("Not a valid hex string: $this")
    } }.joinToString("")
}