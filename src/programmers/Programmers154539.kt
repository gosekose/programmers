import java.util.*

class Programmers154539 {
    private val stack = Stack<P>()
    private lateinit var answer: IntArray

    fun solution(numbers: IntArray): IntArray {

        answer = IntArray(numbers.size) { -1 }

        for (i in 0..numbers.lastIndex) {
            val p = P(numbers[i], i)
            if (stack.isEmpty()) stack.push(p)
            else {
                while (stack.isNotEmpty() && stack.peek().v < p.v) {
                    val head = stack.pop()
                    answer[head.idx] = p.v
                }
                stack.push(p)
            }

        }
        return answer
    }

    data class P(
        val v: Int,
        val idx: Int,
    )
}