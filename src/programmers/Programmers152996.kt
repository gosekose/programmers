class Programmers152996 {

    fun solution(weights: IntArray): Long {
        var answer: Long = 0

        val sameSet = mutableSetOf<Int>()
        val map = mutableMapOf<Int, Long>()
        val values = mutableListOf<Int>()

        for (weight in weights) {
            map[weight] = map.getOrDefault(weight, 0L) + 1L
            if (map[weight] == 1L) values.add(weight)
            else sameSet.add(weight)
        }

        values.sort() // 오름차순 정렬

        for (i in 0..values.lastIndex) {
            for (j in i+1..values.lastIndex) {
                if (i==j) continue

                if (
                    values[i]*3 == values[j]*2 ||
                    values[i]*4 == values[j]*2 ||
                    values[i]*4 == values[j]*3
                ) {
                    val iCnt = map[values[i]] ?: 1L
                    val jCnt = map[values[j]] ?: 1L

                    answer += (iCnt * jCnt)
                }
            }
        }

        sameSet.forEach { key -> // 조합
            val v = map[key]!!
            answer += ((v * (v - 1)) / 2).toLong()
        }

        return answer
    }
}
// 같은 무게는 균형
// 180, 180이 두개가 있다면, (180, 270), (180, 270) 두개로 쳐야 하나? <-- 문제 명시 안됨
// 테스트 결과 --> 조합 케이스 포함
// 비교 대상이 큰 경우  (2, 3), (2, 4), (3, 4)
// 100, 180, 270, 360
// 1. set, map 선언
// 2. 2중 for -> map에 있는 value 간 곱 + answer
// 3. 조합 케이스 추가
// 4. 모두 Long