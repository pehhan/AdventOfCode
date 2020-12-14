package adventofcode.year2020.day14

typealias Memory = MutableMap<Long, Long>

object Task1 {
    fun sum(input: String): Long {
        val memory = mutableMapOf<Long, Long>()
        var mask = ""

        for (line in input.lines()) {
            if (line.contains("mask")) {
                mask = line.substringAfter("= ")
            } else {
                val address = line.substring(line.indexOf('[') + 1, line.indexOf(']')).toLong()
                val value = line.substringAfter("= ").toLong()

                memory[address] = applyMask(mask, value)
            }
        }

        return memory.toList().sumOf { it.second }
    }

    private fun applyMask(mask: String, value: Long): Long {
        val binaryValue = value.toBinaryFromDecimal()

        return mask.foldIndexed("") { index, str, char ->
            str + when (char) {
                'X' -> binaryValue[index]
                else -> char
            }
        }.toDecimalFromBinary()
    }
}

object Task2 {
    fun sum(input: String): Long {
        val memory = mutableMapOf<Long, Long>()
        var mask = ""

        for (line in input.lines()) {
            if (line.contains("mask")) {
                mask = line.substringAfter("= ")
            } else {
                val address = line.substring(line.indexOf('[') + 1, line.indexOf(']')).toLong()
                val value = line.substringAfter("= ").toLong()

                applyMask(memory, mask, address, value)
            }
        }

        return memory.toList().sumOf { it.second }
    }

    private fun applyMask(memory: Memory, mask: String, address: Long, value: Long) {
        val binaryAddress = address.toBinaryFromDecimal()

        val result = mask.foldIndexed("") { index, str, char ->
            str + when (char) {
                '0' -> binaryAddress[index]
                else -> char
            }
        }

        updateMemory(memory, result, value)
    }

    private fun updateMemory(memory: Memory, mask: String, value: Long) {
        if (mask.contains("X")) {
            updateMemory(memory, mask.replaceFirst('X', '0'), value)
            updateMemory(memory, mask.replaceFirst('X', '1'), value)
        } else {
            memory[mask.toDecimalFromBinary()] = value
        }
    }
}

fun Long.toBinaryFromDecimal(bits: Int = 36): String {
    return java.lang.Long.toBinaryString(this).padStart(bits, '0')
}

fun String.toDecimalFromBinary(): Long {
    return java.lang.Long.parseLong(this, 2)
}
