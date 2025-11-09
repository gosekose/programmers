package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val n = readlnOrNull()?.toInt() ?: 0
    val bookSoldCountMap = mutableMapOf<String, Int>()
    var bestSeller = BestSeller("", 0)

    repeat(n) {
        val bookName = readlnOrNull() ?: ""
        val currentCount = bookSoldCountMap.getOrDefault(bookName, 0) + 1
        bookSoldCountMap[bookName] = currentCount

        bestSeller = bestSeller.getUpdateBestSeller(bookName, currentCount)
    }

    println(bestSeller.bookName)
}

data class BestSeller(
    val bookName: String,
    val soldCount: Int,
) {
    fun getUpdateBestSeller(newBookName: String, newSoldCount: Int): BestSeller {
        return when {
            soldCount < newSoldCount -> BestSeller(newBookName, newSoldCount)
            soldCount == newSoldCount && newBookName < bookName -> BestSeller(newBookName, newSoldCount)
            else -> this
        }
    }
}