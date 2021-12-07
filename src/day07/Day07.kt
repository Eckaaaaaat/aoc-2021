import kotlin.math.absoluteValue

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 37)
    println(part1(input))

    check(part2(testInput) == 168)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val (data, range) = input.decode()
    return range.minOf { pos -> data.sumOf { (it - pos).absoluteValue } }
}

private fun part2(input: List<String>): Int {
    val (data, range) = input.decode()
    val fuel = IntArray(range.last + 1) { it * (it + 1) / 2}
    return range.minOf { pos -> data.sumOf { fuel[(it - pos).absoluteValue] } }
}

private fun List<String>.decode() : Pair<List<Int>, IntRange> {
    val data = get(0).split(",").map { it.toInt() }
    return data to data.minOrNull()!!..data.maxOrNull()!!
}
