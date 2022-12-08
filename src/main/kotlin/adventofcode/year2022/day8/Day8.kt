package adventofcode.year2022.day8

typealias Height = Int
typealias TreeLine = List<Height>
typealias HeightMap = List<TreeLine>
typealias ScenicScore = Int

object Task1 {
    fun visibleTrees(input: String): Int {
        return input.toHeightMap().visible()
    }
}

object Task2 {
    fun highestScenicScore(input: String): ScenicScore {
        return input.toHeightMap().scenicScore()
    }
}

fun String.toHeightMap(): HeightMap {
    return this
        .lines()
        .map { line -> line.toCharArray().map { char -> char.digitToInt() } }
        .toList()
}

fun HeightMap.height(x: Int, y: Int): Height {
    return this[y][x]
}

fun HeightMap.visible(): Int {
    return flatMapIndexed { y, list -> List(list.size) { x -> x to y } }
        .count { visibleInAnyDirection(it.first, it.second) }
}

fun HeightMap.visibleInAnyDirection(x: Int, y: Int): Boolean {
    val height = height(x, y)
    return treesLeftOf(x, y).visible(height) || treesRightOf(x, y).visible(height) ||
            treesAbove(x, y).visible(height) || treesBelow(x, y).visible(height)
}

fun HeightMap.treesLeftOf(x: Int, y: Int): TreeLine {
    return this[y].subList(0, x)
}

fun HeightMap.treesRightOf(x: Int, y: Int): TreeLine {
    return this[y].subList(x + 1, this[y].size)
}

fun HeightMap.treesAbove(x: Int, y: Int): TreeLine {
    return filterIndexed { index, _ -> index < y }
        .flatMap { list -> list.filterIndexed { index, _ -> index == x } }
}

fun HeightMap.treesBelow(x: Int, y: Int): TreeLine {
    return filterIndexed { index, _ -> index > y }
        .flatMap { list -> list.filterIndexed { index, _ -> index == x } }
}

fun TreeLine.visible(height: Height): Boolean {
    return all { it < height }
}

fun HeightMap.scenicScore(): ScenicScore {
    return flatMapIndexed { y, list -> List(list.size) { x -> x to y } }
        .maxOf { scenicScore(it.first, it.second) }
}

fun HeightMap.scenicScore(x: Int, y: Int): ScenicScore {
    return scenicScoreLeft(x, y) * scenicScoreRight(x, y) * scenicScoreUp(x, y) * scenicScoreDown(x, y)
}

fun HeightMap.scenicScoreLeft(x: Int, y: Int): ScenicScore {
    return treesLeftOf(x, y).reversed().scenicScore(height(x, y))
}

fun HeightMap.scenicScoreRight(x: Int, y: Int): ScenicScore {
    return treesRightOf(x, y).scenicScore(height(x, y))
}

fun HeightMap.scenicScoreUp(x: Int, y: Int): ScenicScore {
    return treesAbove(x, y).reversed().scenicScore(height(x, y))
}

fun HeightMap.scenicScoreDown(x: Int, y: Int): ScenicScore {
    return treesBelow(x, y).scenicScore(height(x, y))
}

fun TreeLine.scenicScore(height: Height): ScenicScore {
    val index = indexOfFirst { it >= height }
    return if (index >= 0) index + 1 else size
}