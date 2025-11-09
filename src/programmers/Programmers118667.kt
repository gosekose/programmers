class Programmers118667 {
    fun solution(queue1: IntArray, queue2: IntArray): Int {
        var answer = 0
        val queue11 = ArrayDeque<Long>()
        val queue22 = ArrayDeque<Long>()

        for (i in queue1.indices) {
            queue11.add(queue1[i].toLong())
            queue22.add(queue2[i].toLong())
        }

        var queue1Sum = queue1.sum().toLong()
        var queue2Sum = queue2.sum().toLong()

        while (answer <= queue1.size * 4) {
            if (queue1Sum == queue2Sum) return answer

            answer++

            if (queue1Sum > queue2Sum) {
                val temp = queue11.removeFirst()
                queue22.add(temp)
                queue2Sum += temp
                queue1Sum -= temp
            } else {
                val temp = queue22.removeFirst()
                queue11.add(temp)
                queue1Sum += temp
                queue2Sum -= temp
            }
        }

        return -1
    }
}