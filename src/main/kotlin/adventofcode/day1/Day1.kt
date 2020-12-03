package adventofcode.day1

object Task1 {
    fun findValue(lines: List<Int>): Int {
        for ((indexA, a) in lines.withIndex()) {
            for ((indexB, b) in lines.withIndex()) {
                if (indexA != indexB && a + b == 2020) {
                    return a * b
                }
            }
        }

        return 0
    }
}

object Task2 {
    fun findValue(lines: List<Int>): Int {
        for ((indexA, a) in lines.withIndex()) {
            for ((indexB, b) in lines.withIndex()) {
                for ((indexC, c) in lines.withIndex()) {
                    if (indexA != indexB && indexB != indexC && a + b + c == 2020) {
                        return a * b * c
                    }
                }
            }
        }

        return 0
    }
}


