import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 5)
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    var field = Array(1000) { IntArray(1000) }
    input.forEach { line ->
        val (x1, y1, x2, y2) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(line)!!.groupValues.drop(1).map {it.toInt() }
        if (x1 == x2)
            (minOf(y1, y2) .. maxOf(y1, y2)).forEach { field[it][x1]++ }
        else if (y1 == y2)
            (minOf(x1, x2) .. maxOf(x1, x2)).forEach { field[y1][it]++ }
    }

    return field.map { row -> row.count { it > 1 } }.sum()
}

private fun part2(input: List<String>): Int {
    var field = Array(1000) { IntArray(1000) }
    input.forEach { line ->
        val (x1, y1, x2, y2) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(line)!!.groupValues.drop(1).map {it.toInt() }
        val dx = (x2 - x1).sign
        val dy = (y2 - y1).sign
        val steps = maxOf((x2 - x1).absoluteValue, (y2 - y1).absoluteValue)
        for (s in 0..steps) {
            field[y1 + s * dy][x1 + s * dx]++
        }
    }

    return field.map { row -> row.count { it > 1 } }.sum()
}

