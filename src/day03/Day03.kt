import kotlin.math.sign

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 198)
    println(part1(input))

    check(part2(testInput) == 230)
    println(part2(input))
}

private class Slope(val x: Int, val y: Int)

private fun part1(input: List<String>): Int {
    val binary = (0 until input[0].length).map { mostCommonBit(input, it) }
    val value = binary.reduce { acc, digit -> 2 * acc + digit }
    val mask = (1 shl input[0].length) - 1
    return value * (value xor mask)
}

private fun part2(input: List<String>): Int {
    fun filter(predicate: (digit: Int, mostCommon: Int) -> Boolean) : Int {
        val remaining = input.toMutableSet()
        var index = 0
        while (remaining.size > 1) {
            val mostCommon = mostCommonBit(remaining, index)
            remaining.removeIf { !predicate(it[index] - '0', mostCommon) }
            index++
        }
        return remaining.first().toInt(2)
    }
    val oxygen = filter { digit, mostCommon -> digit == mostCommon || (mostCommon == -1 && digit == 1) }
    val co2 = filter { digit, mostCommon -> digit == 1 - mostCommon || (mostCommon == -1 && digit == 0) }
    return oxygen * co2
}

private fun mostCommonBit(input: Collection<String>, position: Int): Int {
    val digits = input.groupBy { it[position] }
    val zeros = digits['0']?.size ?: 0
    val ones = digits['1']?.size ?: 0
    return when((ones - zeros).sign) {
        1 -> 1
        -1 -> 0
        else -> -1
    }
}

