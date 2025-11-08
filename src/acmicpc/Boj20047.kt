package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val n = br.readLine().trim().toInt()
    val sourceLine = br.readLine().trim()   // S
    val targetLine = br.readLine().trim()   // T
    val st = StringTokenizer(br.readLine())
    val pickL = st.nextToken().toInt()      // i (왼쪽)
    val pickR = st.nextToken().toInt()      // j (오른쪽, i<j)

    // 동전 개수(특히 'o')는 그대로이므로 빠른 컷
    if (sourceLine.count { it == 'o' } != targetLine.count { it == 'o' }) {
        println("NO")
        return
    }

    val a = sourceLine[pickL]   // 첫 번째로 삽입해야 하는 문자
    val b = sourceLine[pickR]   // 두 번째로 삽입해야 하는 문자 (a 다음)

    // 선택한 두 칸을 뺀 나머지 줄(상대순서 유지)
    val rest = StringBuilder(n - 2)
    for (idx in 0 until n) {
        if (idx == pickL || idx == pickR) continue
        rest.append(sourceLine[idx])
    }
    val remain = rest.toString()
    val m = remain.length  // n-2

    var dp0 = 0
    var dp1 = -1
    var dp2 = -1

    for (k in 0 until n) {
        val c = targetLine[k]

        var ndp0 = dp0
        if (dp0 >= 0 && dp0 < m && remain[dp0] == c) ndp0 = dp0 + 1

        var ndp1 = dp1
        if (c == a && dp0 >= 0) ndp1 = maxOf(ndp1, dp0)                   // a 삽입 사용(0→1)
        if (dp1 >= 0 && dp1 < m && remain[dp1] == c) ndp1 = maxOf(ndp1, dp1 + 1)

        var ndp2 = dp2
        if (c == b && dp1 >= 0) ndp2 = maxOf(ndp2, dp1)                   // b 삽입 사용(1→2)
        if (dp2 >= 0 && dp2 < m && remain[dp2] == c) ndp2 = maxOf(ndp2, dp2 + 1)

        dp0 = ndp0
        dp1 = ndp1
        dp2 = ndp2
    }

    if (dp2 == m) println("YES") else println("NO")
}