package acmicpc
import java.io.BufferedInputStream
import java.lang.StringBuilder

private class FastScanner {
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
        var sign = 1
        if (c == '-'.code) { sign = -1; c = readByte() }
        var res = 0
        while (c > 32) {
            res = res * 10 + (c - '0'.code)
            c = readByte()
        }
        return res * sign
    }
}

fun main() {
    val fs = FastScanner()
    val N = fs.nextInt()
    val L = fs.nextInt()

    // 배열로 구현한 덱 (원형 X, head<=tail 단조 증가로만 사용)
    val idx = IntArray(N)   // 덱에 담긴 인덱스
    val valArr = IntArray(N) // 덱에 담긴 값
    var head = 0
    var tail = 0

    val out = StringBuilder(N * 3)

    for (i in 0 until N) {
        val cur = fs.nextInt()

        // 뒤에서 값이 cur보다 큰 것들 제거 -> 단조 증가 유지
        while (tail > head && valArr[tail - 1] > cur) {
            tail--
        }
        idx[tail] = i
        valArr[tail] = cur
        tail++

        // 창에서 벗어난 인덱스( i - L ) 제거
        val left = i - L + 1
        while (tail > head && idx[head] < left) {
            head++
        }

        // 덱의 맨 앞이 현재 창의 최소
        out.append(valArr[head])
        if (i != N - 1) out.append(' ')
    }

    print(out.toString())
}