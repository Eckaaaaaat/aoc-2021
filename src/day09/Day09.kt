fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 15)
    println(part1(input))

    check(part2(testInput) == 1134)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val bordered = input.borderize()
    return input.indices.sumOf { row ->
        val filter = input[row].indices.filter { column ->
            input[row][column] < minOf(
                bordered[row][column + 1],
                bordered[row + 1][column],
                bordered[row + 1][column + 2],
                bordered[row + 2][column + 1]
            )
        }
        filter.sumOf { input[row][it] - '0' + 1 }
    }
}

private fun part2(input: List<String>): Int {
    val marker = Array(input.size) { BooleanArray(input[it].length) }
    fun count(row: Int, column: Int): Int {
        if (row !in input.indices || column !in input[row].indices || input[row][column] == '9' || marker[row][column])
            return 0
        else {
            marker[row][column] = true
            return 1 + count(row - 1, column) + count(row, column - 1) + count(row, column + 1) + count(row + 1, column)
        }
    }

    val sizes = mutableListOf<Int>()
    for (row in input.indices)
        for (column in input[row].indices)
            sizes += count(row, column)

    return sizes.sorted().takeLast(3).reduce { acc, i -> acc * i }
}

private fun List<String>.borderize(): List<String> {
    val newLine = ":".repeat(get(0).length + 2)
    return listOf(newLine) + map { ":$it:" } + newLine
}