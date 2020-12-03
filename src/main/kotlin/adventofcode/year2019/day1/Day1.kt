package adventofcode.year2019.day1

object Task1 {
    fun fuelRequired(lines: List<Int>): Int {
        return lines.map { (it / 3) - 2 }.sum()
    }
}

object Task2 {
    fun fuelRequired(lines: List<Int>): Int {
        var totalFuelRequirement = 0

        for (line in lines) {
            var fuelRequirement = (line / 3) - 2

            while (fuelRequirement > 0) {
                totalFuelRequirement += fuelRequirement
                fuelRequirement = (fuelRequirement / 3) - 2
            }
        }

        return totalFuelRequirement
    }
}