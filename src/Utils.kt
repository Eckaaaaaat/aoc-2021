import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInput() = File("src/${dayFolder()}/input.txt").readLines()
fun readTestInput() = File("src/${dayFolder()}/test_input.txt").readLines()

private fun dayFolder() = Thread.currentThread().stackTrace[3].className.split(".")[0].lowercase().dropLast(2)
