class Programmers178870 {

    /**
     * https://school.programmers.co.kr/learn/courses/30/lessons/178870
     */
    fun solution(sequence: IntArray, k: Int): IntArray {
        var answer: IntArray = intArrayOf(0, Int.MAX_VALUE)
        var left = 0
        var right = 0
        var sum = sequence[right]

        while(left < sequence.size) {
            if (sum < k) {
                if (right == sequence.size - 1) break
                sum += sequence[++right]
            } else {
                if (sum == k) {
                    if (answer[1] - answer[0] > right - left) {
                        answer[0] = left
                        answer[1] = right
                    }
                }
                sum -= sequence[left++]
            }
        }

        return answer
    }
}