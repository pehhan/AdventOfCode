package adventofcode.year2019.day2

object Task1 {
    fun valueAtPosition0(input: List<Int>): Int {
        return valueAtPosition0(input, 12, 2)
    }
}

object Task2 {
    fun findValue(input: List<Int>): Int {
        for (noun in 0..99) {
            for (verb in 0..99) {
                if (valueAtPosition0(input, noun, verb) == 19690720) {
                    return 100 * noun + verb
                }
            }
        }
        return 0
    }
}

private fun valueAtPosition0(input: List<Int>, noun: Int, verb: Int): Int {
    val program = input.toMutableList()

    program[1] = noun
    program[2] = verb

    for (i in 0 until program.size step 4) {
        when (program[i]) {
            1 -> {
                program[program[i + 3]] = program[program[i + 1]] + program[program[i + 2]]
            }
            2 -> {
                program[program[i + 3]] = program[program[i + 1]] * program[program[i + 2]]
            }
            99 -> break
        }
    }

    return program[0]
}
