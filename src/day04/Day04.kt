fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 4512)
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val boards = createBoards(input)

    input[0].split(",").forEach {
        val number = it.toInt()
        for (board in boards) {
            if (board.mark(number))
                return board.sumOfUnmarked() * number
        }
    }
    return 0
}

private fun part2(input: List<String>): Int {
    val boards = createBoards(input).toMutableList()
    var lastScore = 0

    input[0].split(",").forEach {
        val number = it.toInt()
        boards.removeIf { board ->
            if (board.mark(number)) {
                lastScore = board.sumOfUnmarked() * number
                true
            } else {
                false
            }
        }
    }

    return lastScore
}

private fun createBoards(input: List<String>): List<Board> {
    return input.drop(2).windowed(6, 6, true) { field ->
        val split = field.take(5).joinToString(" ").trim().split("\\s+".toRegex())
        val numbers = split.map { it.toInt() }
        Board(numbers)
    }
}

private class Board(val numbers: List<Int>) {
    companion object {
        private val bingos = listOf(
            0b00000_00000_00000_00000_11111,
            0b00000_00000_00000_11111_00000,
            0b00000_00000_11111_00000_00000,
            0b00000_11111_00000_00000_00000,
            0b11111_00000_00000_00000_00000,
            0b00001_00001_00001_00001_00001,
            0b00010_00010_00010_00010_00010,
            0b00100_00100_00100_00100_00100,
            0b01000_01000_01000_01000_01000,
            0b10000_10000_10000_10000_10000
        )
    }

    private var marked = 0

    fun mark(number: Int) : Boolean {
        val index = numbers.indexOf(number)
        if (index >= 0) {
            marked = marked or (1 shl index)
        }
        return isBingo()
    }

    fun isBingo(): Boolean = bingos.any { marked and it == it }

    fun sumOfUnmarked() : Int {
        return numbers.withIndex()
            .filter { (index, number) -> marked and (1 shl index) == 0 }
            .sumOf { it.value }
    }
}