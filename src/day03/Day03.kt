fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 7)
    println(part1(input))

    check(part2(testInput) == 336L)
    println(part2(input))
}

private class Slope(val x: Int, val y: Int)

private fun part1(input: List<String>, slope: Slope = Slope(3,1)): Int {
    val len = input[0].length
    return generateSequence(0 to 0) { (x, y) -> x + slope.x to y + slope.y }
        .takeWhile { it.second < input.size }
        .count { (x, y) -> input[y][x % len] == '#' }
}

private fun part2(input: List<String>): Long {
    val slopes = listOf(
        Slope(1,1),
        Slope(3,1),
        Slope(5,1),
        Slope(7,1),
        Slope(1,2))

    return slopes.map { part1(input, it).toLong() }.reduce (Long::times)
}
