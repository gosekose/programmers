package strings

import java.io.*

const val PI = "pi"
const val KA = "ka"
const val CHU = "chu"
const val YES = "YES"
const val NO = "NO"

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val word = readlnOrNull()!!

    var index = 0
    while (index <= word.lastIndex) {
        when {
            word.startsWith(PI, index) || word.startsWith(KA, index) -> index += 2
            word.startsWith(CHU, index) -> index += 3
            else -> {
                println(NO)
                return
            }
        }
    }

    println(YES)
}