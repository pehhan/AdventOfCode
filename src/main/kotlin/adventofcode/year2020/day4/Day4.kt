package adventofcode.year2020.day4

import java.lang.NumberFormatException

object Task1 {
    fun numberOfValidPassports(input: String): Int {
        return input
            .split("\n\n")
            .map { it.split("\\s".toRegex()) }
            .map { list -> list.map { it.split(":")[0] to it.split(":")[1] }.toMap() }
            .count { it.containsKeys("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid") }
    }
}

object Task2 {
    fun numberOfValidPassports(input: String): Int {
        return input
            .split("\n\n")
            .map { it.split("\\s".toRegex()) }
            .map { list -> list.map { it.split(":")[0] to it.split(":")[1] }.toMap() }
            .count { isPassportValid(it) }
    }

    private fun isPassportValid(map: Map<String, String>): Boolean {
        return isBirthYearValid(map) && isIssueYearValid(map) && isExpirationYearValid(map) && isHeightValid(map) && isHairColorValid(map) && isEyeColorValid(map) && isPassportIdValid(map)
    }

    private fun isBirthYearValid(map: Map<String, String>): Boolean {
        return isYearValid(map, "byr", 1920..2002)
    }

    private fun isIssueYearValid(map: Map<String, String>): Boolean {
        return isYearValid(map, "iyr", 2010..2020)
    }

    private fun isExpirationYearValid(map: Map<String, String>): Boolean {
        return isYearValid(map, "eyr", 2020..2030)
    }

    private fun isYearValid(map: Map<String, String>, key: String, range: IntRange): Boolean {
        val year = map[key] ?: return false
        return year.length == 4 && year.isInt() && year.toInt() in range
    }

    private fun isHeightValid(map: Map<String, String>): Boolean {
        val height = map["hgt"] ?: return false
        return when {
            height.endsWith("cm") -> isHeightValid(height.substringBefore("cm"), 150..193)
            height.endsWith("in") -> isHeightValid(height.substringBefore("in"), 59..76)
            else -> false
        }
    }

    private fun isHeightValid(height: String, range: IntRange): Boolean {
        return height.isInt() && height.toInt() in range
    }

    private fun isHairColorValid(map: Map<String, String>): Boolean {
        val hairColor = map["hcl"] ?: return false
        return hairColor.matches("#[0-9a-f]{6}".toRegex())
    }

    private fun isEyeColorValid(map: Map<String, String>): Boolean {
        val eyeColor = map["ecl"] ?: return false
        return eyeColor in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    private fun isPassportIdValid(map: Map<String, String>): Boolean {
        val passportId = map["pid"] ?: return false
        return passportId.isInt() && passportId.length == 9
    }
}

fun <K, V> Map<K, V>.containsKeys(vararg keys: K): Boolean {
    return keys.all { containsKey(it) }
}

fun String.isInt(): Boolean {
    return toIntOrNull() != null
}
