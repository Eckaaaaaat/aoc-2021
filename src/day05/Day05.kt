fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 820)
    println(part1(input))

    check(part2(testInput) == -1)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    return seatsIDs(input).maxOrNull()!!
}

private fun part2(input: List<String>): Int {
    return seatsIDs(input)
        .toSortedSet()
        .windowed(2)
        .firstOrNull { (a, b) -> b > 8 && b == a + 2 }
        ?.first()
        ?.let { it + 1 } ?: -1
}

private fun seatsIDs(input: List<String>) = input.map {
    it
        .replace("F", "0").replace("B", "1")
        .replace("L", "0").replace("R", "1")
        .toInt(2)
}

