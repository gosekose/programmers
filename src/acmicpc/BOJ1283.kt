package acmicpc

fun main(args: Array<String>) {
    val N = readLine()!!.toInt()

    val strings = mutableListOf<String>()
    repeat(N) {
        strings.add(readln())
    }

    val charKeySet = mutableSetOf<Char>()
    val result = mutableListOf<String>()

    for (str in strings) {
        // 문자열 단어로 토큰화
        val words = str.split(" ")

        val sb = StringBuilder()
        var flag = false

        for (k in words.indices) {
            val word = words[k]
            val ch = word[0]

            val upperCh = ch.uppercaseChar()
            if (!charKeySet.contains(upperCh) && !flag) {
                charKeySet.add(upperCh)
                sb.append('[').append(ch).append(']')
                flag = true
                for (idx in 1 until word.length) {
                    val wordCh = word[idx]
                    sb.append(wordCh)
                }
            } else {
                for (idx in word.indices) {
                    val wordCh = word[idx]
                    sb.append(wordCh)
                }
            }

            if (k != words.size - 1) {
                sb.append(" ")
            }
        }

        if (flag) {
            result.add(sb.toString())
            continue
        }

        sb.clear()
        var fch =  false
        for (idx in str.indices) {
            val curCh = str[idx]
            val upperCurCh = curCh.uppercaseChar()

            if (upperCurCh != ' ' && upperCurCh !in charKeySet && !fch) {
                charKeySet.add(upperCurCh)
                sb.append('[').append(curCh).append(']')
                fch = true
            } else {
                sb.append(curCh)
            }
        }

        result.add(sb.toString())
    }

    val bw = System.out.bufferedWriter()
    for (r in result) {
        bw.appendLine(r)
    }

    bw.flush()
}