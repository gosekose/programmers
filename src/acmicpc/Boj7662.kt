package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val res = StringBuilder()

    val N = br.readLine().toInt()
    for(t in 0 until N) {
        val T = br.readLine().toInt()
        val tree = TreeMap<Int, Int>()

        for (i in 0 until T) {
            val str = StringTokenizer(br.readLine())

            val command = str.nextToken()
            val num = str.nextToken().toInt()

            when (command) {
                "I" -> {
                    tree[num] = (tree[num] ?: 0) + 1
                }

                "D" -> {
                    if (tree.isEmpty()) {
                        continue
                    }

                    /* 최소값 삭제 */
                    if (num == -1) {
                        if (tree[tree.firstKey()]!! > 1) {
                            tree[tree.firstKey()] = tree[tree.firstKey()]!! - 1
                        } else {
                            tree.remove(tree.firstKey())
                        }
                    } else if (num == 1) {
                        if (tree[tree.lastKey()]!! > 1) {
                            tree[tree.lastKey()] = tree[tree.lastKey()]!! - 1
                        } else {
                            tree.remove(tree.lastKey())
                        }
                    }
                }
            }
        }

        if (tree.isEmpty()) {
            res.appendLine("EMPTY")
            continue
        }

        res.append(tree.lastKey()).append(" ").append(tree.firstKey()).append("\n")
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    bw.write(res.toString())
    bw.flush()
}