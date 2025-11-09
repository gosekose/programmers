import java.util.Stack

class Programmers176962 {
    /**
     * https://school.programmers.co.kr/learn/courses/30/lessons/176962
     */
    fun solution(plans: Array<Array<String>>): Array<String> {
        val answer = mutableListOf<String>()
        val stack = Stack<Array<String>>()

        plans.sortBy { it[1] } // 과제 시간 정렬

        for (i in 0 until plans.lastIndex) { // 첫번째부터 마지막 전까지
            val current = plans[i]
            val next = plans[i + 1]

            val startTime = getTime(current[1])
            val needTime = current[2].toInt()
            val nextTime = getTime(next[1])

            if (startTime + needTime == nextTime) { // 정확히 끝난다면
                answer.add(current[0])
            } else if (startTime + needTime < nextTime) { // 시간이 남으면
                answer.add(current[0])

                var spareTime = nextTime - startTime - needTime // 남은 시간

                while (spareTime > 0 && stack.isNotEmpty()) { // 남은 시간이 있고 스택이 비어있지 않으면
                    val job = stack.pop() // 남은 작업 꺼내고

                    val jobSpareTime = job[2].toInt()

                    if (jobSpareTime <= spareTime) { // 남은 작업의 작업 시간이 여분 시간보다 작으면
                        answer.add(job[0])
                        spareTime -= jobSpareTime
                    } else { // 여분이 없으면
                        job[2] = (jobSpareTime - spareTime).toString() // 여분 시간 재설정
                        stack.add(job) // 스택에 다시 넣기
                        break
                    }
                }
            } else { // 시간이 부족하면 일단 다쓰고 업데이트
                current[2] = (needTime - (nextTime - startTime)).toString()
                stack.add(current) // 재설정한 남은 잔여 job 추가
            }
        }

        val current = plans[plans.lastIndex]
        answer.add(current[0])

        // while
        while (stack.isNotEmpty()) {
            val job = stack.pop()
            answer.add(job[0])
        }

        return answer.toTypedArray()
    }

    private fun getTime(time: String): Int {
        val times = time.split(":")
        return times[0].toInt() * 60 + times[1].toInt()
    }
}