class Programmers134239 {
    fun solution(k: Int, ranges: Array<IntArray>): DoubleArray {
        val answer = mutableListOf<Double>()

        val locs = mutableListOf<Loc>()

        var varK = k
        var n = 0

        while (varK > 1) {

            val loc = Loc(n, varK)
            locs.add(loc)

            if (varK % 2 == 0) {
                varK /= 2
            } else {
                varK *= 3
                varK++
            }
            n++

            if (varK == 1) {
                locs.add(Loc(n, 1))
                break
            }

        } // 최종 n은 x = n - b의 정적분 대상

        val areas = mutableListOf<Double>() // 0 ~ 1 (n, n-1)의 너비
        var first = locs[0]
        for (i in 1 .. locs.lastIndex) {
            val l = first.y + locs[i].y // 빗변
            areas.add(l * 1 * 0.5) // 빗변 * 높이 * 0.5
            first = locs[i] // 다음항 업데이트
        } // 0 ~ n (개구간에 대한 면적 리스트로 존재)

        for (i in 0..ranges.lastIndex) {
            val range = ranges[i]
            val a = range[0]
            val b = n + range[1]

            if (a > b) {
                answer.add(-1.0)
                continue
            } // 정적분 범위 아님

            var sum = 0.0
            for (j in a until b) {
                sum += areas[j]
            }
            answer.add(sum)
        }

        return answer.toDoubleArray()
    }

    data class Loc(
        val x: Int,
        val y: Int,
    )
}
// 짝: /2
// 홀: *3 + 1
// 1보다 크면 1번 작업 반복