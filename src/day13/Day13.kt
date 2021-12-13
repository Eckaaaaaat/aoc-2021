fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 17)
    println(part1(input))

    println(part2(input))
}

private fun part1(input: List<String>): Int {
    return foldPaper(input, 1).dots.size
}

private fun part2(input: List<String>) {
    val paper = foldPaper(input)
    val grid = Array(paper.dots.maxOf { it.second } + 1) { CharArray( paper.dots.maxOf { it.first } + 1) {' '} }
    paper.dots.forEach { (x, y) -> grid[y][x] = '\u2588' }
    grid.forEach { println(it.concatToString()) }
}

private fun foldPaper(input: List<String>, limit: Int = Int.MAX_VALUE): Paper {
    val dots = input
        .takeWhile { it.isNotBlank() }
        .map { line -> line.split(",").map { it.toInt() } }
        .map { (x, y) -> x to y }
        .toSet()

    return input.filter { it.startsWith("fold along") }
        .take(limit)
        .map { it.replace("fold along ", "") }
        .fold(Paper(dots)) { paper, instruction -> paper.fold(instruction)}
}

private class Paper(val dots: Set<Pair<Int, Int>>) {
    fun fold(instruction: String) : Paper {
        val foldValue = instruction.drop(2).toInt()
        return when(instruction[0]) {
            'x' -> foldX(foldValue)
            else ->foldY(foldValue)
        }
    }

    fun foldX(fx: Int) = fold(fx, Int.MAX_VALUE)
    fun foldY(fy: Int) = fold(Int.MAX_VALUE, fy)

    private fun fold(fx: Int, fy: Int) : Paper {
        require(fx == Int.MAX_VALUE || fy == Int.MAX_VALUE)
        return Paper(dots.map { (x, y) -> x.foldAt(fx) to y.foldAt(fy)}.toSet())
    }

    private fun Int.foldAt(at: Int) = if (this < at) this else 2 * at - this
}