package adventofcode.year2020.day20

import adventofcode.FileReader
import org.junit.Test

class Day20Test {

    @Test
    fun testDay20Task1() {
        val input = FileReader.getResource("year2020/day20.txt")
        println("2020 Day 20 Task 1: ${Task1.result(input)}")
    }

    @Test
    fun `construct Tile from lines`() {
        val data = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val tile = Tile.from(data)

        assert(tile.id == 42L)
        assert(tile.rows.size == 3)
        assert(tile.rows[0] == "123")
        assert(tile.rows[1] == "456")
        assert(tile.rows[2] == "789")
    }

    @Test
    fun `flip Tile horizontal`() {
        val data = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val tile = Tile.from(data).flipHorizontal()

        assert(tile.id == 42L)
        assert(tile.rows.size == 3)
        assert(tile.rows[0] == "321")
        assert(tile.rows[1] == "654")
        assert(tile.rows[2] == "987")
    }

    @Test
    fun `flip Tile vertical`() {
        val data = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val tile = Tile.from(data).flipVertical()

        assert(tile.id == 42L)
        assert(tile.rows.size == 3)
        assert(tile.rows[0] == "789")
        assert(tile.rows[1] == "456")
        assert(tile.rows[2] == "123")
    }

    @Test
    fun `rotate Tile`() {
        val data = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        var tile = Tile.from(data).rotate()

        assert(tile.id == 42L)
        assert(tile.rows.size == 3)
        assert(tile.rows[0] == "741")
        assert(tile.rows[1] == "852")
        assert(tile.rows[2] == "963")

        tile = tile.rotate()

        assert(tile.id == 42L)
        assert(tile.rows.size == 3)
        assert(tile.rows[0] == "987")
        assert(tile.rows[1] == "654")
        assert(tile.rows[2] == "321")

        tile = tile.rotate()

        assert(tile.id == 42L)
        assert(tile.rows.size == 3)
        assert(tile.rows[0] == "369")
        assert(tile.rows[1] == "258")
        assert(tile.rows[2] == "147")

        tile = tile.rotate()

        assert(tile.id == 42L)
        assert(tile.rows.size == 3)
        assert(tile.rows[0] == "123")
        assert(tile.rows[1] == "456")
        assert(tile.rows[2] == "789")
    }

    @Test
    fun `get edges from Tile`() {
        val data = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val tile = Tile.from(data)
        val edges = tile.edges()

        assert(edges.size == 4)
        assert(edges[0] == "123")
        assert(edges[1] == "369")
        assert(edges[2] == "789")
        assert(edges[3] == "147")
    }

    @Test
    fun `edge matches same Tile`() {
        val data1 = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val data2 = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val tile1 = Tile.from(data1)
        val tile2 = Tile.from(data2)

        assert(tile1.edgeMatches(tile2))
    }

    @Test
    fun `edge matches other Tile with same edge rotated`() {
        val data1 = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val data2 = """
            Tile 42:
            111
            111
            123
        """.trimIndent().lines()

        val data3 = """
            Tile 42:
            111
            211
            311
        """.trimIndent().lines()

        val data4 = """
            Tile 42:
            111
            112
            113
        """.trimIndent().lines()

        val data5 = """
            Tile 42:
            123
            111
            111
        """.trimIndent().lines()

        val tile1 = Tile.from(data1)
        val tile2 = Tile.from(data2)
        val tile3 = Tile.from(data3)
        val tile4 = Tile.from(data4)
        val tile5 = Tile.from(data5)

        assert(tile1.edgeMatches(tile2))
        assert(tile1.edgeMatches(tile3))
        assert(tile1.edgeMatches(tile4))
        assert(tile1.edgeMatches(tile5))
    }

    @Test
    fun `edge matches other Tile with same edge flipped`() {
        val data1 = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val data2 = """
            Tile 42:
            321
            111
            111
        """.trimIndent().lines()

        val data3 = """
            Tile 42:
            311
            211
            111
        """.trimIndent().lines()

        val data4 = """
            Tile 42:
            113
            112
            111
        """.trimIndent().lines()

        val data5 = """
            Tile 42:
            321
            111
            111
        """.trimIndent().lines()

        val tile1 = Tile.from(data1)
        val tile2 = Tile.from(data2)
        val tile3 = Tile.from(data3)
        val tile4 = Tile.from(data4)
        val tile5 = Tile.from(data5)

        assert(tile1.edgeMatches(tile2))
        assert(tile1.edgeMatches(tile3))
        assert(tile1.edgeMatches(tile4))
        assert(tile1.edgeMatches(tile5))
    }

    @Test
    fun `edge does not matches other Tile with different edge`() {
        val data1 = """
            Tile 42:
            123
            456
            789
        """.trimIndent().lines()

        val data2 = """
            Tile 42:
            111
            111
            111
        """.trimIndent().lines()

        val data3 = """
            Tile 42:
            313
            121
            313
        """.trimIndent().lines()

        val data4 = """
            Tile 42:
            113
            112
            113
        """.trimIndent().lines()

        val data5 = """
            Tile 42:
            331
            111
            111
        """.trimIndent().lines()

        val tile1 = Tile.from(data1)
        val tile2 = Tile.from(data2)
        val tile3 = Tile.from(data3)
        val tile4 = Tile.from(data4)
        val tile5 = Tile.from(data5)

        assert(!tile1.edgeMatches(tile2))
        assert(!tile1.edgeMatches(tile3))
        assert(!tile1.edgeMatches(tile4))
        assert(!tile1.edgeMatches(tile5))
    }

    @Test
    fun `corners for Tile calculated correctly`() {
        val data1 = """
            Tile 2311:
            ..##.#..#.
            ##..#.....
            #...##..#.
            ####.#...#
            ##.##.###.
            ##...#.###
            .#.#.#..##
            ..#....#..
            ###...#.#.
            ..###..###
        """.trimIndent().lines()

        val data2 = """
            Tile 1951:
            #.##...##.
            #.####...#
            .....#..##
            #...######
            .##.#....#
            .###.#####
            ###.##.##.
            .###....#.
            ..#.#..#.#
            #...##.#..
        """.trimIndent().lines()

        val data3 = """
            Tile 1171:
            ####...##.
            #..##.#..#
            ##.#..#.#.
            .###.####.
            ..###.####
            .##....##.
            .#...####.
            #.##.####.
            ####..#...
            .....##...
        """.trimIndent().lines()

        val data4 = """
            Tile 1427:
            ###.##.#..
            .#..#.##..
            .#.##.#..#
            #.#.#.##.#
            ....#...##
            ...##..##.
            ...#.#####
            .#.####.#.
            ..#..###.#
            ..##.#..#.
        """.trimIndent().lines()

        val data5 = """
            Tile 1489:
            ##.#.#....
            ..##...#..
            .##..##...
            ..#...#...
            #####...#.
            #..#.#.#.#
            ...#.#.#..
            ##.#...##.
            ..##.##.##
            ###.##.#..
        """.trimIndent().lines()

        val data6 = """
            Tile 2473:
            #....####.
            #..#.##...
            #.##..#...
            ######.#.#
            .#...#.#.#
            .#########
            .###.#..#.
            ########.#
            ##...##.#.
            ..###.#.#.
        """.trimIndent().lines()

        val data7 = """
            Tile 2971:
            ..#.#....#
            #...###...
            #.#.###...
            ##.##..#..
            .#####..##
            .#..####.#
            #..#.#..#.
            ..####.###
            ..#.#.###.
            ...#.#.#.#
        """.trimIndent().lines()

        val data8 = """
            Tile 2729:
            ...#.#.#.#
            ####.#....
            ..#.#.....
            ....#..#.#
            .##..##.#.
            .#.####...
            ####.#.#..
            ##.####...
            ##..#.##..
            #.##...##.
        """.trimIndent().lines()

        val data9 = """
            Tile 3079:
            #.#.#####.
            .#..######
            ..#.......
            ######....
            ####.#..#.
            .#...#.##.
            #.#####.##
            ..#.###...
            ..#.......
            ..#.###...
        """.trimIndent().lines()

        val tile1 = Tile.from(data1)
        val tile2 = Tile.from(data2)
        val tile3 = Tile.from(data3)
        val tile4 = Tile.from(data4)
        val tile5 = Tile.from(data5)
        val tile6 = Tile.from(data6)
        val tile7 = Tile.from(data7)
        val tile8 = Tile.from(data8)
        val tile9 = Tile.from(data9)

        val image = Image(listOf(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9))

        val corners = image.corners()

        assert(corners.size == 4)
        assert(corners[0] == tile2)
        assert(corners[1] == tile9)
        assert(corners[2] == tile3)
        assert(corners[3] == tile7)
    }
}