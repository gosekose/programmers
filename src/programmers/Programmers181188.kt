class Programmers181188 {
    /**
     * https://school.programmers.co.kr/learn/courses/30/lessons/181188
     */
    fun solution(targets: Array<IntArray>): Int {
        var answer: Int = 0
        var end = 0

        targets.sortBy { it[1] }

        targets.forEach { target ->
            if (target[0] >= end) {
                end = target[1]
                answer++
            }
        }

        return answer
    }
}