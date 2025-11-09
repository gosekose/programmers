package strings

import java.io.*
import java.util.*
fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val palindromeCheckStr = readlnOrNull()!!
    println(if (palindromeCheckStr.isPalindrome()) 1 else 0)
}

fun String.isPalindrome(): Boolean {
    val stack = Stack<Char>()
    val isOdd = this.length % 2 != 0

    val midIndex = this.length / 2 - 1
    for (i in 0..midIndex) {
        stack.add(this[i])
    }

    val startIdx = if (isOdd) midIndex + 2 else midIndex + 1
    for (i in startIdx..this.lastIndex) {
        val char = stack.pop()
        if (char != this[i]) return false
    }

    return true
}