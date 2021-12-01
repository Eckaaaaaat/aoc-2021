fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 7)
    println(part1(input))

    check(part2(testInput) == 5)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    return input
        .map { it.toInt() }
        .windowed(2)
        .count { (a, b) -> a < b }
}

private fun part2(input: List<String>): Int {
    return input
        .map { it.toInt() }
        .windowed(3)
        .map { it.sum() }
        .windowed(2)
        .count { (a, b) -> a < b }
}

