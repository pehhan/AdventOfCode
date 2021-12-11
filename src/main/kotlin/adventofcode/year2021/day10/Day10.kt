package adventofcode.year2021.day10

import java.lang.IllegalArgumentException
import kotlin.collections.ArrayDeque

object Task1 {
    fun syntaxErrorScore(lines: List<String>): Int {
        return lines
            .map { it.toList() }
            .sumOf { corruptionScore(it) }
    }
}

object Task2 {
    fun middleCompletionScore(lines: List<String>): Long {
        val scores = lines
            .asSequence()
            .map { it.toList() }
            .filter { corruptionScore(it) == 0 }
            .map { incompletePart(it) }
            .map { completePart(it) }
            .map {
                it.fold(0L) { totalScore, char ->
                    totalScore * 5 + score(char)
                }
            }
            .sorted()
            .toList()

        return scores[scores.size / 2]
    }

    private fun incompletePart(chars: List<Char>): ArrayDeque<Char> {
        val deque = ArrayDeque<Char>()

        for (char in chars) {
            when (char) {
                '(', '[', '{', '<' -> deque.addFirst(char)
                ')', ']', '}', '>' -> {
                    if (matches(deque.first(), char)) {
                        deque.removeFirst()
                    }
                }
            }
        }

        return deque
    }

    private fun completePart(deque: ArrayDeque<Char>): List<Char> {
        val list = mutableListOf<Char>()

        while (deque.isNotEmpty()) {
            val open = deque.removeFirst()
            list += close(open)
        }

        return list
    }

    private fun close(char: Char): Char {
        return when (char) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            '<' -> '>'
            else -> throw IllegalArgumentException("Unknown tag: $char")
        }
    }

    private fun score(char: Char): Int {
        return when (char) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> throw IllegalArgumentException("Unknown tag: $char")
        }
    }
}

private fun corruptionScore(chars: List<Char>): Int {
    val deque = ArrayDeque<Char>()

    for (char in chars) {
        when (char) {
            '(', '[', '{', '<' -> deque.addFirst(char)
            ')', ']', '}', '>' -> {
                if (matches(deque.first(), char)) {
                    deque.removeFirst()
                } else {
                    return corruptionScore(char)
                }
            }
        }
    }

    return 0
}

private fun matches(open: Char, close: Char): Boolean {
    return when (open) {
        '(' -> close == ')'
        '[' -> close == ']'
        '{' -> close == '}'
        '<' -> close == '>'
        else -> false
    }
}

private fun corruptionScore(char: Char): Int {
    return when (char) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw IllegalArgumentException("Unknown tag: $char")
    }
}