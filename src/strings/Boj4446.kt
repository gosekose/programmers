package strings

import java.io.*

val vowel = listOf('a', 'i', 'y', 'e', 'o', 'u')
val consonant = listOf(
    'b', 'k', 'x', 'z', 'n', 'h', 'd', 'c',
    'w', 'g', 'p', 'v', 'j', 'q', 't', 's', 'r', 'l', 'm', 'f'
)

const val EOF = "EOF"

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {

    val map = buildEncryptionMap()

    generateSequence { readlnOrNull() ?: EOF }
        .takeWhile { it != EOF}
        .forEach {
            val encryptedText = it.encryptByRot13(map)
            println(encryptedText)
        }
}

private fun buildEncryptionMap(): Map<Char, Char> {
    return (vowel.mapIndexedToRotation(3) + consonant.mapIndexedToRotation(10)).toMap()
}

private fun List<Char>.mapIndexedToRotation(shift: Int): List<Pair<Char, Char>> {
    return this.flatMapIndexed { idx, char ->
        listOf(
            char to this[(idx + shift) % this.size],
            char.uppercaseChar() to this[(idx + shift) % this.size].uppercaseChar()
        )
    }
}

fun String.encryptByRot13(map: Map<Char, Char>): String {
    return this.map { map[it] ?: it }.joinToString("")
}