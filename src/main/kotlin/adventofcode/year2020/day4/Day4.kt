package adventofcode.year2020.day4

typealias Passport = Map<String, String>

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

    private fun isPassportValid(passport: Passport): Boolean {
        return isBirthYearValid(passport) && isIssueYearValid(passport) && isExpirationYearValid(passport) && isHeightValid(passport) && isHairColorValid(passport) && isEyeColorValid(passport) && isPassportIdValid(passport)
    }

    private fun isBirthYearValid(passport: Passport): Boolean {
        return isYearValid(passport, "byr", 1920..2002)
    }

    private fun isIssueYearValid(passport: Passport): Boolean {
        return isYearValid(passport, "iyr", 2010..2020)
    }

    private fun isExpirationYearValid(passport: Passport): Boolean {
        return isYearValid(passport, "eyr", 2020..2030)
    }

    private fun isYearValid(passport: Passport, key: String, range: IntRange): Boolean {
        val year = passport[key] ?: return false
        return year.length == 4 && year.isInt() && year.toInt() in range
    }

    private fun isHeightValid(passport: Passport): Boolean {
        val height = passport["hgt"] ?: return false
        return when {
            height.endsWith("cm") -> isHeightValid(height.substringBefore("cm"), 150..193)
            height.endsWith("in") -> isHeightValid(height.substringBefore("in"), 59..76)
            else -> false
        }
    }

    private fun isHeightValid(height: String, range: IntRange): Boolean {
        return height.isInt() && height.toInt() in range
    }

    private fun isHairColorValid(passport: Passport): Boolean {
        val hairColor = passport["hcl"] ?: return false
        return hairColor.matches("#[0-9a-f]{6}".toRegex())
    }

    private fun isEyeColorValid(passport: Passport): Boolean {
        val eyeColor = passport["ecl"] ?: return false
        return eyeColor in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    private fun isPassportIdValid(passport: Passport): Boolean {
        val passportId = passport["pid"] ?: return false
        return passportId.isInt() && passportId.length == 9
    }
}

fun <K, V> Map<K, V>.containsKeys(vararg keys: K): Boolean {
    return keys.all { containsKey(it) }
}

fun String.isInt(): Boolean {
    return toIntOrNull() != null
}
