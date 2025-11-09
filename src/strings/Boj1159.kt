package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val firstNameCountMap = mutableMapOf<Char, Int>()

    repeat(readlnOrNull()?.toInt() ?: 0) {
        val firstName = readlnOrNull()?.first() ?: throw IllegalArgumentException()
        firstNameCountMap[firstName] = firstNameCountMap.getOrDefault(firstName, 0) + 1
    }

    val result = firstNameCountMap.filter { it.value >= 5 }.keys.sorted()

    when {
        result.isEmpty() -> println("PREDAJA")
        else -> println(result.joinToString(""))
    }
}