package adventofcode.year2021.day20

import adventofcode.year2021.day20.Pixel.Dark
import adventofcode.year2021.day20.Pixel.Light

data class Position(val x: Int, val y: Int)

enum class Pixel { Light, Dark }

data class Image(private val data: Map<Position, Pixel>, private val enhancementAlgorithm: String) {

    fun enhance(step: Int): Image {
        val enhancedData = mutableMapOf<Position, Pixel>()

        val minX = data.minOf { it.key.x } - 2
        val maxX = data.maxOf { it.key.x } + 2

        val minY = data.minOf { it.key.y } - 2
        val maxY = data.maxOf { it.key.y } + 2

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                val position = Position(x, y)

                val pixel = if (enhancementAlgorithm[getEnhancementIndex(position, step)] == '#') Light else Dark
                enhancedData[position] = pixel
            }
        }

        return Image(enhancedData, enhancementAlgorithm)
    }

    fun litPixels(): Int {
        return data.count { it.value == Light }
    }

    private fun getEnhancementIndex(position: Position, step: Int): Int {
        return neighbors(position, step)
            .map { if (it == Light) '1' else '0' }
            .toCharArray()
            .concatToString()
            .toInt(2)
    }

    private fun neighbors(position: Position, step: Int): List<Pixel> {
        val x = position.x
        val y = position.y

        val defaultPixel = if (enhancementAlgorithm[0] == '.') {
            Dark
        } else {
            if (step % 2 == 0) Light else Dark
        }

        return listOf(
            data[Position(x - 1, y - 1)] ?: defaultPixel,
            data[Position(x, y - 1)] ?: defaultPixel,
            data[Position(x + 1, y - 1)] ?: defaultPixel,

            data[Position(x - 1, y)] ?: defaultPixel,
            data[Position(x, y)] ?: defaultPixel,
            data[Position(x + 1, y)] ?: defaultPixel,

            data[Position(x - 1, y + 1)] ?: defaultPixel,
            data[Position(x, y + 1)] ?: defaultPixel,
            data[Position(x + 1, y + 1)] ?: defaultPixel
        )
    }

    companion object {
        fun parse(input: String): Image {
            val (enhancementAlgorithm, inputImage) = input.split("\n\n")
            val data = mutableMapOf<Position, Pixel>()

            for ((y, line) in inputImage.lines().withIndex()) {
                for ((x, char) in line.withIndex()) {
                    data[Position(x, y)] = if (char == '#') Light else Dark
                }
            }

            return Image(data, enhancementAlgorithm)
        }
    }
}

object Task1 {
    fun numberOfLitPixels(input: String): Int {
        val image = Image.parse(input)
        return image.enhance(1).enhance(2).litPixels()
    }
}

object Task2 {
    fun numberOfLitPixels(input: String): Int {
        var image = Image.parse(input)
        repeat(50) { step ->
            image = image.enhance(step + 1)
        }

        return image.litPixels()
    }
}