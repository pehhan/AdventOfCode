package adventofcode.year2015.day7

import adventofcode.year2015.day7.Instruction.*
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

typealias Wire = String

@ExperimentalUnsignedTypes
sealed class Instruction {
    data class ProvideSignalValue(val value: UShort, val to: Wire) : Instruction()
    data class ProvideSignalWire(val from: Wire, val to: Wire) : Instruction()
    data class AndWire(val from1: Wire, val from2: Wire, val to: Wire) : Instruction()
    data class AndValue(val from: Wire, val value: UShort, val to: Wire) : Instruction()
    data class OrWire(val from1: Wire, val from2: Wire, val to: Wire) : Instruction()
    data class OrValue(val from: Wire, val value: UShort, val to: Wire) : Instruction()
    data class LeftShift(val from: Wire, val value: Int, val to: Wire) : Instruction()
    data class RightShift(val from: Wire, val value: Int, val to: Wire) : Instruction()
    data class Not(val from: Wire, val to: Wire): Instruction()
}

@ExperimentalUnsignedTypes
private fun toInstruction(line: String): Instruction {
    val parts = line.split(" -> ")
    return when {
        line.contains("AND") -> {
            val wires = parts[0].split(" AND ")
            if (wires[0].isUShort() || wires[1].isUShort()) {
                val value = if (wires[0].isUShort()) wires[0].toUShort() else wires[1].toUShort()
                val wire = if (wires[0].isUShort()) wires[1] else wires[0]

                AndValue(wire, value, parts[1])
            } else {
                AndWire(wires[0], wires[1], parts[1])
            }
        }
        line.contains("OR") -> {
            val wires = parts[0].split(" OR ")
            if (wires[0].isUShort() || wires[1].isUShort()) {
                val value = if (wires[0].isUShort()) wires[0].toUShort() else wires[1].toUShort()
                val wire = if (wires[0].isUShort()) wires[1] else wires[0]

                OrValue(wire, value, parts[1])
            } else {
                OrWire(wires[0], wires[1], parts[1])
            }
        }
        line.contains("LSHIFT") -> LeftShift(parts[0].split(" LSHIFT ")[0], parts[0].split(" LSHIFT ")[1].toInt(), parts[1])
        line.contains("RSHIFT") -> RightShift(parts[0].split(" RSHIFT ")[0], parts[0].split(" RSHIFT ")[1].toInt(), parts[1])
        line.contains("NOT") -> Not(parts[0].substringAfter("NOT "), parts[1])
        else -> {
            if (parts[0].isUShort()) {
                ProvideSignalValue(parts[0].toUShort(), parts[1])
            } else {
                ProvideSignalWire(parts[0], parts[1])
            }
        }
    }
}

@ExperimentalUnsignedTypes
object Task1 {
    fun signalToWireA(input: String): UShort {
        val instructions = input.lines().map { toInstruction(it) }
        return signalToWireA(instructions)
    }
}

@ExperimentalUnsignedTypes
object Task2 {
    fun signalToWireA(input: String): UShort {
        val instructions = input.lines().map { toInstruction(it) }.toMutableList()
        val originalValueToA = signalToWireA(instructions)

        val instruction = instructions.find { it is ProvideSignalValue && it.to == "b" }
        if (instruction != null) {
            instructions.remove(instruction)
            instructions.add(ProvideSignalValue(originalValueToA, "b"))
        } else {
            throw IllegalArgumentException("Could not find b")
        }

        return signalToWireA(instructions)
    }
}

@ExperimentalUnsignedTypes
private fun signalToWireA(instructions: List<Instruction>): UShort {
    val mutableInstructions = instructions.toMutableList()
    val wires = mutableMapOf<Wire, UShort>()

    while (mutableInstructions.isNotEmpty()) {
        val iterator = mutableInstructions.iterator()
        while (iterator.hasNext()) {
            when (val instruction = iterator.next()) {
                is ProvideSignalValue -> {
                    wires[instruction.to] = instruction.value
                    iterator.remove()
                }
                is ProvideSignalWire -> {
                    val from = wires[instruction.from]
                    if (from != null) {
                        wires[instruction.to] = from
                        iterator.remove()
                    }
                }
                is AndWire -> {
                    val from1 = wires[instruction.from1]
                    val from2 = wires[instruction.from2]
                    if (from1 != null && from2 != null) {
                        wires[instruction.to] = from1 and from2
                        iterator.remove()
                    }
                }
                is AndValue -> {
                    val from = wires[instruction.from]
                    if (from != null) {
                        wires[instruction.to] = from and instruction.value
                        iterator.remove()
                    }
                }
                is OrWire -> {
                    val from1 = wires[instruction.from1]
                    val from2 = wires[instruction.from2]
                    if (from1 != null && from2 != null) {
                        wires[instruction.to] = from1 or from2
                        iterator.remove()
                    }
                }
                is OrValue -> {
                    val from = wires[instruction.from]
                    if (from != null) {
                        wires[instruction.to] = from or instruction.value
                        iterator.remove()
                    }
                }
                is LeftShift -> {
                    val from = wires[instruction.from]
                    if (from != null) {
                        wires[instruction.to] = from shl instruction.value
                        iterator.remove()
                    }
                }
                is RightShift -> {
                    val from = wires[instruction.from]
                    if (from != null) {
                        wires[instruction.to] = from shr instruction.value
                        iterator.remove()
                    }
                }
                is Not -> {
                    val from = wires[instruction.from]
                    if (from != null) {
                        wires[instruction.to] = from.inv()
                        iterator.remove()
                    }
                }
            }
        }
    }

    return wires["a"] ?: 0.toUShort()
}

@ExperimentalUnsignedTypes
private fun String.isUShort(): Boolean {
    return try {
        toUShort()
        true
    } catch (exception: NumberFormatException) {
        false
    }
}

@ExperimentalUnsignedTypes
private infix fun UShort.shl(value: Int): UShort {
    return (toInt() shl value).toUShort()
}

@ExperimentalUnsignedTypes
private infix fun UShort.shr(value: Int): UShort {
    return (toInt() shr value).toUShort()
}