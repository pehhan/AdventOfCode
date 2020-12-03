package adventofcode.year2020.day2

object Task1 {
    fun numberOfValidPasswords(lines: List<String>): Int {
        return lines.map { it.split(" ") }.count { isPasswordValid(it[2], it[1][0], it[0].split("-")[0].toInt(), it[0].split("-")[1].toInt()) }
    }

    private fun isPasswordValid(password: String, character: Char, minTimes: Int, maxTimes: Int): Boolean {
        return password.count { it == character } in minTimes..maxTimes
    }
}

object Task2 {
    fun numberOfValidPasswords(lines: List<String>): Int {
        return lines.map { it.split(" ") }.count { isPasswordValid(it[2], it[1][0], it[0].split("-")[0].toInt() - 1, it[0].split("-")[1].toInt() - 1) }
    }

    private fun isPasswordValid(password: String, character: Char, position1: Int, position2: Int): Boolean {
        return (password[position1] == character) xor (password[position2] == character)
    }
}