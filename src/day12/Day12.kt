fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 226)
    println(part1(input))

    check(part2(testInput) == 3509)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val nodes = input.flatMap { it.split("-") }.toSet().associateWith { Node(it) }
    input.map { it.split("-") }.forEach { (a, b) ->
        nodes[a]!!.neighbours += nodes[b]!!
        nodes[b]!!.neighbours += nodes[a]!!
    }

    val start = nodes["start"]!!
    val stop = nodes["end"]!!

    fun Node.count(visited: Set<Node>): Int {
        return when {
            this == stop -> 1
            else -> neighbours.filterNot { it.small && it in visited }.sumOf { it.count(visited + it) }
        }
    }

    return start.count(setOf(start))
}

private fun part2(input: List<String>): Int {
    val nodes = input.flatMap { it.split("-") }.toSet().associateWith { Node(it) }
    input.map { it.split("-") }.forEach { (a, b) ->
        nodes[a]!!.neighbours += nodes[b]!!
        nodes[b]!!.neighbours += nodes[a]!!
    }

    val start = nodes["start"]!!
    val stop = nodes["end"]!!

    fun Node.count(visited: List<Node>, revisited : Boolean): Int {
        return when {
            this == stop -> 1
            else -> neighbours.sumOf {
                when {
                    it == start -> 0
                    !it.small -> it.count(visited + it, revisited)
                    !revisited -> it.count(visited + it, it in visited)
                    it !in visited -> it.count(visited + it, true)
                    else -> 0
                }
            }
        }
    }

    return start.count(listOf(start), false)
}

private class Node(val name: String) {
    val small = name[0].isLowerCase()
    val neighbours = LinkedHashSet<Node>()

    override fun toString() = name
}