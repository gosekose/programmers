package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val possiblePasswords = List(readlnOrNull()!!.toInt()) {readlnOrNull()!!}

    var result = Password(0, 'a')
    val passwordMap = mutableSetOf<String>()

    for (password in possiblePasswords) {
        val reversedPassword = password.reversed()

        if (password.isPalindromeV2() || passwordMap.contains(password) || passwordMap.contains(reversedPassword)) {
            result = Password(password.length, password[password.length / 2])
            break
        }

        passwordMap.add(password)
    }

    println(result.toString())
}

data class Password(
    val length: Int,
    val midChar: Char,
) {
    override fun toString(): String {
        return "$length $midChar"
    }
}

fun String.isPalindromeV2(): Boolean {
    val midIndex = this.length / 2 - 1
    for (i in 0..midIndex) {
        if (this[i] != this[this.lastIndex - i]) return false
    }

    return true
}