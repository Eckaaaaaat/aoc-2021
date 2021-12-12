fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 1656)
    println(part1(input))

    check(part2(testInput) == 195)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val octopuses = input.map { line -> line.map { it - '0' }.toIntArray() }.toTypedArray()
    return (1..100).sumOf { step(octopuses) }
}

private fun part2(input: List<String>): Int {
    val octopuses = input.map { line -> line.map { it - '0' }.toIntArray() }.toTypedArray()
    return (1..Int.MAX_VALUE).first { step(octopuses) == 100 }
}

private fun step(octopuses: Array<IntArray>): Int {
    val flashRange = 10 until 1000
    var flashCount = 0
    octopuses.mutate { it + 1 }
    while (octopuses.any { line -> line.any { it in flashRange } }) {
        iterate2D(10, 10) { x, y ->
            if (octopuses[y][x] in flashRange) {
                flashCount++
                iterate2D(3, 3) { x1, y1 -> octopuses.increase(x + x1 - 1, y + y1 - 1) }
                octopuses[y][x] = flashRange.endInclusive + 1
            }
        }
    }
    octopuses.mutate { if (it > 9) 0 else it }
    return flashCount
}

private fun Array<IntArray>.mutate(op: (Int) -> Int) {
    forEach { for (i in it.indices) it[i] = op(it[i]) }
}

private fun Array<IntArray>.increase(x: Int, y: Int) {
    if (y in indices && x in this[y].indices)
        this[y][x]++
}

private fun iterate2D(width: Int, height: Int, op: (Int, Int) -> Unit) {
    (0 until width * height).forEach { op(it % width, it / width) }
}