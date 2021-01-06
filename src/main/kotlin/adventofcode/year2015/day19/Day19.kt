package adventofcode.year2015.day19

typealias Molecule = String
typealias Replacement = String
typealias ReplacementMap = Map<Molecule, List<Replacement>>

object Task1 {
    fun numberOfDistinctMolecules(input: String): Int {
        val (replacements, molecule) = input.split("\n\n")
        val replacementMap = toReplacementMap(replacements.lines())

        return numberOfDistinctMolecules(replacementMap, molecule)
    }

    private fun toReplacementMap(lines: List<String>): ReplacementMap {
        val map = mutableMapOf<Molecule, List<Replacement>>()

        for (line in lines) {
            val (molecule, replacement) = line.split(" => ")
            val replacementList = map[molecule]
            if (replacementList != null) {
                map[molecule] = replacementList + replacement
            } else {
                map[molecule] = listOf(replacement)
            }
        }

        return map
    }

    private fun numberOfDistinctMolecules(map: ReplacementMap, molecule: Molecule): Int {
        val result = mutableSetOf<Molecule>()
        var i = 0

        while (i < molecule.length) {
            // Since a molecule can be either of length 1 or 2 we need to test both.
            val molecule1 = molecule[i].toString()
            val replacements1 = map[molecule1]
            if (replacements1 != null) {
                result += replacements(molecule, replacements1, i, molecule1)
                i++
            } else if (i + 1 < molecule.length) {
              val molecule2 = molecule[i].toString() + molecule[i + 1].toString()
              val replacements2 = map[molecule2]
              if (replacements2 != null) {
                  result += replacements(molecule, replacements2, i, molecule2)
                  i += 2
              } else {
                  i++
              }
            } else {
                i++
            }
        }

        return result.size
    }

    private fun replacements(molecule: Molecule, replacements: List<Replacement>, index: Int, moleculeToReplace: Molecule): Set<Molecule> {
        val result = mutableSetOf<Molecule>()

        for (replacement in replacements) {
            result += molecule.substring(0, index) + replacement + molecule.substring(index + moleculeToReplace.length, molecule.length)
        }

        return result
    }
}