package acmicpc

fun main(args: Array<String>) {
    var (n, r, c) = readln().split(" ").map { it.toInt() }

    var ans = 0L
    while (n > 0) {
        val half = 1 shl (n - 1)
        val block = half * half

        val bottom = if (r >= half) 1 else 0
        val right  = if (c >= half) 1 else 0
        val quad   = bottom * 2 + right

        ans += block.toLong() * quad

        if (bottom == 1) r -= half
        if (right  == 1) c -= half
        n--
    }

    println(ans)
}

// 10 11
// 00 01


// 10 11 12 13
// 00 01 02 03

//