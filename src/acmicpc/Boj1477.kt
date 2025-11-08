package acmicpc

import kotlin.math.min

fun main(args: Array<String>) {
    // 휴게소 개수, 더 지으려는 휴게소, 고속도로 길이
    val (N, M, L) = readln().split(" ").map {it.toInt()}

    // 현재 휴게소 위치
    val rest = mutableListOf<Int>()

    // 현재 휴게소의 위치 추가
    rest.add(0)
    if (N > 0) {
        val str = readln().split(" ")
        for (i in 0 until N) {
            rest.add(str[i].toInt())
        }
    }
    rest.add(L)
    rest.sort()

    val restAreas = mutableListOf<Int>()

    var pre = rest[0]
    for (i in 1 until rest.size) {
        val cur = rest[i]
        restAreas.add(cur - pre)
        pre = cur
    }

    var left = 1
    var right = L
    var answer = L

    while(left <= right) {
        val mid = (left + right) / 2

        var grap = 0 // 판별식 처리 (mid를 최소 구간으로 두었을 떄 필요한 M개수)
        for (restArea in restAreas) {
            grap += ((restArea - 1) / mid)
            if (grap > M) break
        }

        if (grap <= M) {
            answer = min(answer, mid)
            right = mid - 1
        } else { //
            left = mid + 1
        }
    }

    println(answer)
}