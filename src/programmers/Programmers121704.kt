import java.util.*

class Programmers121704 {
    private val stack = Stack<Int>()
    private val queue: Queue<Int> = LinkedList()

    fun solution(order: IntArray): Int {
        var answer: Int = 0

        for (i in 1..order.size) {
            queue.add(i) // 선입 선출
        }

        while (queue.isNotEmpty()) { // 큐가 비지 않을 때까지 반복
            if (order[answer] == queue.peek()) {
                answer++
                queue.poll()
            } else if (stack.isNotEmpty() && stack.peek() == order[answer]) {
                stack.pop()
                answer++
            } else {
                val que = queue.poll()
                stack.add(que)
            }
        }

        while (stack.isNotEmpty()) { // 스택이 비지 않을 때 까지 반복
            if (order[answer] == stack.peek()) {
                answer++
                stack.pop()
            } else break
        }

        return answer
    }
}