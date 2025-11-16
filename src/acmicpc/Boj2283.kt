package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val MAX = 1_000_000
    val br = BufferedReader(InputStreamReader(System.`in`))
    var st = StringTokenizer(br.readLine())

    val N = st.nextToken().toInt()
    val K = st.nextToken().toLong()

    val diff = LongArray(MAX + 1)

    repeat(N) {
        st = StringTokenizer(br.readLine())
        val a = st.nextToken().toInt()
        val b = st.nextToken().toInt()

        /* [a, b) 범위 */
        diff[a]++
        if (b <= MAX) {
            diff[b]--
        }
    }

    for (i in 1..MAX) {
        diff[i] += diff[i - 1]
    }

    /* prefix[i]는 0... i-1까지의 누적합 */
    val prefix = LongArray(MAX + 1)
    for (i in 0 until MAX) {
        prefix[i + 1] = prefix[i] + diff[i]
    }

    if (prefix[MAX] < K) {
        println("0 0")
        return
    }

    var left = 0
    var ansA = -1
    var ansB = -1

    for (right in 1..MAX) {
        /* 총합이 K보다 큰 경우 누적합 줄이기 */
        while(left < right && prefix[right] - prefix[left] > K) {
            left++
        }
        if (left < right && prefix[right] - prefix[left] == K) {
            ansA = left
            ansB = right
            break
        }
    }

    val sb = StringBuilder()
    if (ansA == -1) {
        sb.append("0 0")
    } else {
        sb.append(ansA).append(" ").append(ansB)
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}