fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 26)
    println(part1(input))

    check(part2(testInput) == 61229)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val (_, digits) = line.split("|").map { it.trim() }
        digits.split(" ").count { it.length !in 5..6 }
    }
}

private fun part2(input: List<String>): Int {
    val numbers = listOf(
        0b1110111,
        0b0010010,
        0b1011101,
        0b1011011,
        0b0111010,
        0b1101011,
        0b1101111,
        0b1010010,
        0b1111111,
        0b1111011
    )

    fun translate(encoded: String, permutation: List<Int>): Int {
        val recoded = encoded.map { permutation[it - 'a'] }.fold(0) { acc, i -> acc + (1 shl i) }
        return numbers.indexOf(recoded)
    }

    val permutations = createPermutations()

    return input.sumOf { line ->
        val (left, right) = line.split("|").map { it.trim() }
        val testDigits = left.split(" ")
        val permutation = permutations.first { perm ->
            testDigits.all { translate(it, perm) != -1 }
        }
        right.split(" ").map { translate(it, permutation) }.reduce { acc, d -> 10 * acc + d }
    }
}

private fun createPermutations(): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    fun step(permutation: List<Int>, remaining: List<Int>) {
        if (remaining.isEmpty())
            result += permutation
        else for (i in remaining.indices)
            step(permutation + remaining[i], remaining - remaining[i])
    }

    step(emptyList(), (0..6).toList())
    return result
}