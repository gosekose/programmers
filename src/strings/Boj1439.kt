package strings

import java.io.*
import java.util.StringTokenizer
import kotlin.math.min

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val strs = readlnOrNull() ?: ""
    val zeroTokens = StringTokenizer(strs, "0")
    val oneTokens = StringTokenizer(strs, "1")

    println(min(zeroTokens.countTokens(), oneTokens.countTokens()))
}