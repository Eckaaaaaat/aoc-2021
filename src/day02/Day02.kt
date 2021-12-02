fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 2)
    println(part1(input))

    check(part2(testInput) == 1)
    println(part2(input))
}

private fun part1(input: List<String>): Int {
    val regex = """(\d+)-(\d+) (.): (.*)""".toRegex()

    fun String.isValid(): Boolean {
        return regex.find(this)!!.let { match ->
            val (_, from, to, letter, password) = match.groupValues
            password.count { it == letter[0] } in from.toInt()..to.toInt()
        }
    }

    return input.count { it.isValid() }
}

private fun part2(input: List<String>): Int {
    val regex = """(\d+)-(\d+) (.): (.*)""".toRegex()

    fun String.isValid(): Boolean {
        return regex.find(this)!!.let { match ->
            val (_, first, second, letter, password) = match.groupValues
            (password[first.toInt() - 1] == letter[0]) xor (password[second.toInt() - 1] == letter[0])
        }
    }

    return input.count { it.isValid() }
}
