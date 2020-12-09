package adventofcode.year2020.day9

import java.lang.IllegalArgumentException
import java.util.*
import javax.xml.bind.DatatypeConverter
import kotlin.run

class LimitedQueue<T>(private val max: Int) : LinkedList<T>() {

    override fun add(element: T): Boolean {
        if (size + 1 > max) {
            remove()
        }

        return super.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        if (elements.size > max) return super.addAll(elements.drop(elements.size - max))
        return super.addAll(elements)
    }

    operator fun plus(other: T): LimitedQueue<T> {
        add(other)
        return this
    }

    operator fun plusAssign(other: LimitedQueue<T>) {
        addAll(other)
    }
}

object Task1 {
    fun findInvalidNumber(input: String, preamble: Int): Long {
        val numbers = input.lines().map { it.toLong() }

        val initialQueue = LimitedQueue<Long>(preamble)
        initialQueue += numbers.take(preamble)

        return run outer@{
            numbers
                .drop(preamble)
                .fold(initialQueue) { queue, number ->
                    if (isSumOfTwoNumbersInQueue(queue, number)) {
                        queue + number
                    } else {
                        return@outer queue + number
                    }
                }
        }.last
    }

    private fun isSumOfTwoNumbersInQueue(queue: LimitedQueue<Long>, number: Long): Boolean {
        val list = queue.toList()
        for (i in list.indices) {
            for (j in i + 1 until list.size) {
                if (list[i] + list[j] == number) return true
            }
        }

        return false
    }
}

object Task2 {
    fun findEncryptionWeakness(input: String, preamble: Int): Long {
        val numbers = input.lines().map { it.toLong() }
        val invalidNumber = Task1.findInvalidNumber(input, preamble)

        for (i in numbers.indices) {
            val result = numbers.drop(i).fold(listOf<Long>()) { list, number ->
                if (list.sum() == invalidNumber) {
                    list
                } else {
                    list + number
                }
            }

            if (result.sum() == invalidNumber) return (result.minOrNull() ?: 0) + (result.maxOrNull() ?: 0)
        }

        throw IllegalArgumentException("Could not find encryption weakness.")
    }
}