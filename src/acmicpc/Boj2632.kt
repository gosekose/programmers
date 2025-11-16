package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val k = br.readLine().toInt() /* 피자 크기 2000000 이하 자연수 */
    var str = StringTokenizer(br.readLine())

    val m = str.nextToken().toInt()
    val n = str.nextToken().toInt()

    /* 원형큐 */
    val mArr = IntArray(m * 2)
    val nArr = IntArray(n * 2)

    for (i in 0 until m) {
        val num = br.readLine().toInt()
        mArr[i] = num
    }

    for (i in 0 until n) {
        val num = br.readLine().toInt()
        nArr[i] = num
    }

    for (i in m until m * 2) {
        mArr[i] = mArr[i - m]
    }

    for (i in n until n * 2) {
        nArr[i] = nArr[i - n]
    }

    /* A피자의 연속된 조각 크기를 저장할 맵 */
    val aMap = mutableMapOf<Int, Int>()

    /* 시작 피자 위치 */
    for (start in 0 until m) {
        /* 처음 시작에만 전체 선택 경우 추가 */
        val end = if (start == 0) start + m else start + m - 1
        var size = 0
        for (idx in start until end) {
            size += mArr[idx]

            /* 연속되는 피자의 사이즈 맵 추가 */
            aMap[size] = (aMap[size] ?: 0) + 1
        }
    }

    /* b피자의 연속된 조각 크기를 저장할 맵 */
    val bMap = mutableMapOf<Int, Int>()

    /* 시작 피자 위치 */
    for (start in 0 until n) {
        /* 처음 시작에만 전체 선택 경우 추가 */
        val end = if (start == 0) start + n else start + n - 1
        var size = 0
        for (idx in start until end) {
            size += nArr[idx]

            /* 연속되는 피자의 사이즈 맵 추가 */
            bMap[size] = (bMap[size] ?: 0) + 1
        }
    }

    var ans = 0L

    /* A 맵에서 B를 맞추기 */
    for ((size, cnt) in aMap.entries) {
        val target = k - size /* B에서 골라야 하는 피자 크기 */

        if (target < 0) continue

        /* B에서 선택 안하는 케이스 */
        if (target == 0) {
            ans += cnt.toLong()
            continue
        }

        if (bMap.containsKey(target)) {
            ans += (cnt.toLong() * bMap[target]!!.toLong())
        }
    }

    /* B에서 K사이즈 선택이 모두 가능한 경우 */
    if (bMap.containsKey(k)) {
        ans += bMap[k]!!.toLong()
    }

    val sb = StringBuilder()
    sb.append(ans)
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}