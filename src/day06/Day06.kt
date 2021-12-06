fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 5934L)
    println(part1(input))

    check(part2(testInput) == 26984457539L)
    println(part2(input))
}

private fun part1(input: List<String>): Long {
    return countPopulation(input, 80)
}

private fun part2(input: List<String>): Long {
    return countPopulation(input, 256)
}

private fun countPopulation(input: List<String>, days: Int): Long {
    val state = LongArray(9) { age -> input[0].count { it == '0' + age }.toLong() }
    repeat(days) {
        val born = state[0]
        System.arraycopy(state, 1, state, 0, 8)
        state[8] = born
        state[6] += born
    }

    return state.sum()
}
