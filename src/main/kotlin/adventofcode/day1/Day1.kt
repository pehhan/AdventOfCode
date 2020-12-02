package adventofcode.day1

object Task1 {
    fun findValue(input: List<Int>): Int {
        for ((indexA, a) in input.withIndex()) {
            for ((indexB, b) in input.withIndex()) {
                if (indexA != indexB && a + b == 2020) {
                    return a * b
                }
            }
        }

        return 0
    }
}

object Task2 {
    fun findValue(input: List<Int>): Int {
        for ((indexA, a) in input.withIndex()) {
            for ((indexB, b) in input.withIndex()) {
                for ((indexC, c) in input.withIndex()) {
                    if (indexA != indexB && indexB != indexC && a + b + c == 2020) {
                        return a * b * c
                    }
                }
            }
        }

        return 0
    }
}


