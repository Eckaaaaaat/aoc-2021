import java.lang.Exception

fun main() {
    val testInput = readTestInput()
    val input = readInput()

    check(part1(testInput) == 2)
    println(part1(input))

    check(part2(testInput) == 2)
    println(part2(input))
}

private fun part1(input: List<String>, validator: (Map<String, String>) -> Boolean = { true }): Int {
    val required = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
    val joined = input.fold(mutableListOf("")) { acc, line ->
        if (line.isBlank())
            acc.also { acc += "" }
        else
            acc.also { acc[acc.lastIndex] += " $line" }
    }
    val count = joined.map { pass ->
        "${pass.trim()} cid:0"
            .split(" ")
            .map { it.split(":", limit = 2) }
            .map { (k,v) -> k to v }
            .toMap()
    }.count { it.keys == required && validator(it.toSortedMap()) }
    return count
}

private fun part2(input: List<String>): Int {
    return part1(input) { map ->
        val tests = listOf(
            { map["byr"]!!.let { it.length == 4 && it.toInt() in 1920..2002 } },
            { map["ecl"] in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
            { map["eyr"]!!.let { it.length == 4 && it.toInt() in 2020..2030 } },
            { map["hcl"]!!.matches("#[0-9a-f]{6}".toRegex()) },
            { map["hgt"]!!.let { (it.endsWith("cm") && it.dropLast(2).toInt() in 150..193) || (it.endsWith("in") && it.dropLast(2).toInt() in 59..76) } },
            { map["iyr"]!!.let { it.length == 4 && it.toInt() in 2010..2020 } },
            { map["pid"]!!.matches("[0-9]{9}".toRegex()) }
        )

        try {
            tests.all { it() }
        } catch (e: Exception) {
            false
        }
    }
}
