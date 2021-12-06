import java.lang.IllegalArgumentException

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 150L)
    println(part1(input))

    check(part2(testInput) == 900L)
    println(part2(input))
}

private fun part1(input: List<String>): Long {
    val grouped = input
        .groupBy { it.split(" ")[0] }
        .mapValues { (_, values) ->
            values.map { it
                .split(" ")[1].toLong() }
                .sum() }
    return grouped["forward"]!! * (grouped["down"]!! - grouped["up"]!!)
}

private fun part2(input: List<String>): Long {
    data class Pos(val a: Long, val h: Long, val d: Long)

    val pos = input.fold(Pos(0, 0, 0)) { acc, command ->
        val (type, value) = command.split(" ").let { it[0] to it[1].toLong() }
        when(type) {
            "up" -> acc.copy(acc.a - value)
            "down" -> acc.copy( acc.a + value)
            "forward" -> acc.copy(h = acc.h + value, d = acc.d + acc.a * value)
            else -> throw IllegalArgumentException()
        }
    }

    return pos.h * pos.d
}
