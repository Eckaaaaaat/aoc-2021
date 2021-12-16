import java.util.*
import kotlin.math.absoluteValue

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 40)
    println(part1(input))

    check(part2(testInput) == 315)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val positions = input.mapIndexed { y, line -> line.mapIndexed { x, c -> Position(x, y, c - '0') } }
    return findPath(positions)
}

private fun part2(input: List<String>): Int {
    val positions =
        (0..4).flatMap { offsetY ->
            input.mapIndexed { y, line ->
                (0..4).flatMap { offsetX ->
                    line.mapIndexed { x, c -> Position(x + offsetX * line.length, y + offsetY * input.size, (c - '1' + offsetX + offsetY) % 9 + 1) }
                }
            }
        }

    return findPath(positions)
}

private fun findPath(positions: List<List<Position>>): Int {
    val start = positions[0][0]
    val stop = positions.last().last()
    start.distance = 0

    val openList = PriorityQueue<Position>(compareBy { it.key })
    val closedList = mutableSetOf<Position>()

    start.key = 0
    openList += start
    while (openList.isNotEmpty()) {
        val current = openList.poll()
        if (current === stop)
            return current.distance
        closedList += current

        fun Position.checkNeighbour(dx: Int, dy: Int) {
            if (y + dy in positions.indices && x + dx in positions[y + dy].indices) {
                val other = positions[y + dy][x + dx]
                if (other in closedList)
                    return
                val tentative_dist = distance + other.value
                if (other in openList && tentative_dist >= other.distance)
                    return
                other.distance = minOf(other.distance, distance + other.value)
                if (other in openList)
                    openList -= other
                other.key = tentative_dist + (stop.x - other.x).absoluteValue + (stop.y - other.y).absoluteValue
                openList += other

            }
        }
        current.checkNeighbour(0, -1)
        current.checkNeighbour(0, 1)
        current.checkNeighbour(-1, 0)
        current.checkNeighbour(1, 0)
    }

    return -1
}


private class Position(val x: Int, val y: Int, val value: Int) {
    var distance = Int.MAX_VALUE
    var key = 0;
    override fun toString(): String {
        return "$x,$y - $value"
    }
}