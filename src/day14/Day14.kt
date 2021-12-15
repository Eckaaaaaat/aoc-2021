import java.util.concurrent.ConcurrentSkipListMap

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 1588L)
    println(part1(input))

    check(part2(testInput) == 2188189693529L)
    println(part2(input))
}

private fun part1(input: List<String>): Long {
    return compute(input, 10)
}

private fun part2(input: List<String>): Long {
    return compute(input, 40)
}

private fun compute(input: List<String>, iterations: Int): Long {
    val mapping = input.drop(2).associate { it.take(2) to it.last() }
    val cache = ConcurrentSkipListMap<String, Map<Char, Long>>()
    val counts: Map<Char, Long> = input[0]
        .windowed(2, 1, true)
        .fold(emptyMap()) { acc, pair ->
            if (pair.length == 2)
                acc mergeWith process(pair, iterations, mapping, cache) andIncrement pair[0]
            else
                acc andIncrement pair[0]
        }

    return counts.values.maxOrNull()!! - counts.values.minOrNull()!!
}

private fun process(
    pair: String,
    count: Int,
    mapping: Map<String, Char>,
    cache: MutableMap<String, Map<Char, Long>>
): Map<Char, Long> {
    return cache.computeIfAbsent("$pair:$count") {
        if (count == 0)
            emptyMap()
        else {
            val insert = mapping[pair]!!
            val left = process("${pair[0]}$insert", count - 1, mapping, cache)
            val right = process("$insert${pair[1]}", count - 1, mapping, cache)
            left mergeWith right andIncrement insert
        }
    }
}

private infix fun Map<Char, Long>.mergeWith(other: Map<Char, Long>): Map<Char, Long> {
    return (keys + other.keys).toSet().associateWith { (this[it] ?: 0) + (other[it] ?: 0) }
}

private infix fun Map<Char, Long>.andIncrement(c: Char) = this mergeWith mapOf(c to 1L)