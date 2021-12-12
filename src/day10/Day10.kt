import java.lang.IllegalArgumentException

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 26397)
    println(part1(input))

    check(part2(testInput) == 288957L)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val remaining = generateSequence(line) { it.replace("""\(\)|\[\]|\{\}|<>""".toRegex(), "")}
            .windowed(2)
            .first { (a, b) -> a == b }
            .let { it.first().replace("""\(|\[|\{|<""".toRegex(), "") }

        when(remaining.firstOrNull()) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        } as Int
    }
}

private fun part2(input: List<String>): Long {
    val scores  = input.map { line ->
        val remaining = generateSequence(line) { it.replace("""\(\)|\[\]|\{\}|<>""".toRegex(), "")}
            .windowed(2)
            .first { (a, b) -> a == b }
            .let { it.first() }
        if ( remaining.replace("""\(|\[|\{|<""".toRegex(), "").isEmpty() ) {
            remaining.foldRight(0L) { char, acc ->
                when (char) {
                    '(' -> 5 * acc + 1
                    '[' -> 5 * acc + 2
                    '{' -> 5 * acc + 3
                    '<' -> 5 * acc + 4
                    else -> throw IllegalArgumentException()
                }
            }
        } else {
            0L
        }
    }
        .sorted()
        .dropWhile { it == 0L }
    return scores[scores.size / 2]
}
