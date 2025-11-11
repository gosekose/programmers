package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*


data class Node16118(
    val e: Int,
    val d: Long,
) : Comparable<Node16118> {
    override fun compareTo(o: Node16118): Int = when {
        this.d < o.d -> -1
        this.d > o.d -> 1
        else -> 0
    }
}

data class Speed16118(
    val e: Int,
    val d: Long,
    val speed: Int,
) : Comparable<Speed16118> {
    override fun compareTo(o: Speed16118): Int = when {
        this.d < o.d -> -1
        this.d > o.d -> 1
        else -> 0
    }
}

/* 코틀린 오버헤드 패스트 스캐너 */
private class FastScanner16118 {
    private val input = BufferedInputStream(System.`in`)
    private val buffer = ByteArray(1 shl 16)
    private var len = 0
    private var ptr = 0
    private fun readByte(): Int {
        if (ptr >= len) {
            len = input.read(buffer)
            ptr = 0
            if (len <= 0) return -1
        }
        return buffer[ptr++].toInt()
    }
    fun nextInt(): Int {
        var c = readByte()
        while (c <= 32) c = readByte()
        var sgn = 1
        if (c == '-'.code) { sgn = -1; c = readByte() }
        var v = 0
        while (c > 32) {
            v = v * 10 + (c - '0'.code)
            c = readByte()
        }
        return v * sgn
    }
    fun nextLong(): Long {
        var c = readByte()
        while (c <= 32) c = readByte()
        var sgn = 1
        if (c == '-'.code) { sgn = -1; c = readByte() }
        var v = 0L
        while (c > 32) {
            v = v * 10 + (c - '0'.code)
            c = readByte()
        }
        return if (sgn == 1) v else -v
    }
}

fun main(args: Array<String>) {
    val fs = FastScanner16118()
    val N = fs.nextInt()
    val M = fs.nextInt()

    /* 그래프 */
    // mutableList 대신 배열 인접리스트
    val SZ = M * 2
    val head = IntArray(N + 1) { -1 }
    val to = IntArray(SZ)
    val w = LongArray(SZ)
    val next = IntArray(SZ)
    var ep = 0
    fun add(u: Int, v: Int, ww: Long) {
        to[ep] = v
        w[ep] = ww
        next[ep] = head[u]
        head[u] = ep++
    }

    /* 양방향 간선 */
    repeat(M) {
        val a = fs.nextInt()
        val b = fs.nextInt()
        val d = fs.nextInt().toLong() * 2 // 1/2 연산을 정수로 계산하기 위함
        add(a, b, d)
        add(b, a, d)
    }

    /* 출발점에서 간선이 하나도 없으면 갱신 불가 */
    if (head[1] == -1) {
        println(0)
        return
    }

    /* 여우 이동 -> 1차원 다익스트라 */
    val foxDist = LongArray(N + 1){ Long.MAX_VALUE }
    foxDist[1] = 0 // 자기 자신 이동은 0

    val foxPq = PriorityQueue<Node16118>()
    foxPq.add(Node16118(1, 0))

    while(foxPq.isNotEmpty()) {
        val fox = foxPq.poll()
        if (fox.d != foxDist[fox.e]) continue

        /* 연결 노드 탐색 */
        var e = head[fox.e]
        while (e != -1) {
            val v = to[e]
            val nd = fox.d + w[e]
            /* 다익스트라 갱신 */
            if (nd < foxDist[v]) {
                foxDist[v] = nd
                foxPq.add(Node16118(v, nd))
            }
            e = next[e]
        }
    }

    /* 늑대 달리기 다익스트라 0: 나갈 때 2배 빠르게 달림 , 1: 나갈 때 2배 느리게 달림 */
    val wolfDist = Array(N + 1) { LongArray(2) { Long.MAX_VALUE } }
    wolfDist[1][0] = 0

    /* 먼저 시작은 2배 속도로 달림 */
    val wolfPq = PriorityQueue<Speed16118>()
    wolfPq.add(Speed16118(1, 0, 0))

    while(wolfPq.isNotEmpty()) {
        val wolf = wolfPq.poll()
        val speed = wolf.speed
        if (wolf.d != wolfDist[wolf.e][speed]) continue

        var e = head[wolf.e]
        val nextSpeed = 1 - speed
        while (e != -1) {
            val v = to[e]
            val nd = if (speed == 0) (w[e] / 2) + wolf.d else w[e] * 2 + wolf.d
            if (nd < wolfDist[v][nextSpeed]) {
                wolfDist[v][nextSpeed] = nd
                wolfPq.add(Speed16118(v, nd, nextSpeed))
            }
            e = next[e]
        }
    }

    var ans = 0
    for (i in 2 .. N) {
        val fox = foxDist[i]
        val wolf = min(wolfDist[i][0], wolfDist[i][1])

        if (fox < wolf) {
            ans++
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$ans")
    bw.flush()
}

// N, M
// 달빛 여우, 달빛 늑대
// 달빛 늑대 -> 오솔길 하나를 달빛 여우 두배 속도, 하나를 달빛 여우의 절반 속도
// 달빛 여우가 승리 조건 그루터기 몇개