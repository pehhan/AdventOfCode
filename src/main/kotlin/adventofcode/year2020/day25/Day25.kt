package adventofcode.year2020.day25

object Task1 {

    fun findEncryptionKey(input: String): Long {
        val cardPublicKey = input.lines().first().toLong()
        val doorPublicKey = input.lines().last().toLong()

        return findEncryptionKey(cardPublicKey, findLoopSize(doorPublicKey))
    }

    private fun findLoopSize(publicKey: Long): Int {
        var loopSize = 0
        var value = 1L
        val subjectNumber = 7

        do {
            value *= subjectNumber
            value %= 20201227
            loopSize++
        } while (value != publicKey)

        return loopSize
    }

    private fun findEncryptionKey(publicKey: Long, loopSize: Int): Long {
        var value = 1L

        repeat(loopSize) {
            value *= publicKey
            value %= 20201227
        }

        return value
    }
}