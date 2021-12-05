package adventofcode.year2021.day5

data class Point(val x: Int, val y: Int) {

    companion object {
        fun from(str: String): Point {
            return Point(str.substringBefore(",").toInt(), str.substringAfter(",").toInt())
        }
    }
}

data class Line(val start: Point, val end: Point) {

    fun isHorizontalOrVertical(): Boolean {
        return isHorizontal() || isVertical()
    }

    private fun isHorizontal(): Boolean {
        return start.y == end.y
    }

    private fun isVertical(): Boolean {
        return start.x == end.x
    }

    fun points(): List<Point> {
        when {
            isHorizontal() -> {
                val (startX, endX) = if (start.x < end.x) start.x to end.x else end.x to start.x
                return (startX..endX).map { Point(it, start.y) }
            }
            isVertical() -> {
                val (startY, endY) = if (start.y < end.y) start.y to end.y else end.y to start.y
                return (startY..endY).map { Point(start.x, it) }
            }
            else -> {
                // 45 degree diagonal line
                val points = mutableListOf<Point>()
                val (startX, endX) = if (start.x < end.x) start.x to end.x else end.x to start.x
                val (startY, endY) = if (start.x < end.x) start.y to end.y else end.y to start.y

                var y = startY

                for (x in startX..endX) {
                    points += Point(x, y)
                    y = if (startY < endY) y + 1 else y - 1
                }

                return points
            }
        }
    }

    companion object {
        fun from(str: String): Line {
            return Line(Point.from(str.substringBefore(" -> ")), Point.from(str.substringAfter(" -> ")))
        }
    }
}

object Task1 {
    fun numberOfPointsWhereAtLeastTwoLinesOverlap(input: List<String>): Int {
        return numberOfPointsWhereAtLeastTwoLinesOverlap(input, true)
    }
}

object Task2 {
    fun numberOfPointsWhereAtLeastTwoLinesOverlap(input: List<String>): Int {
        return numberOfPointsWhereAtLeastTwoLinesOverlap(input, false)
    }
}

private fun numberOfPointsWhereAtLeastTwoLinesOverlap(input: List<String>, countOnlyHorizontalAndVertical: Boolean): Int {
    return input
        .asSequence()
        .map { Line.from(it) }
        .filter { if (countOnlyHorizontalAndVertical) it.isHorizontalOrVertical() else true }
        .map { it.points() }
        .flatten()
        .groupingBy { it }
        .eachCount()
        .count { it.value >= 2 }
}