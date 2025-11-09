class Programmers150368 {
    private val rate = arrayOf(10, 20, 30, 40)
    private var n = 0
    private lateinit var users: Array<IntArray>
    private lateinit var emoticons: IntArray
    private var answer = IntArray(2)

    fun solution(usersInput: Array<IntArray>, emoticonsInput: IntArray): IntArray {
        users = usersInput
        emoticons = emoticonsInput
        n = emoticons.size

        permutate(0, 0, mutableListOf<Int>())

        return answer
    }

    private fun permutate(start: Int, target: Int, rates: MutableList<Int>) {
        if (target == n) {
            getResult(rates)
        }

        for (i in start..n) {
            for (j in 0..3) { // 비율
                rates.add(rate[j])
                permutate(i + 1, target + 1, rates)
                rates.removeAt(rates.lastIndex) // 백트래킹
            }
        }
    }

    private fun getResult(rates: MutableList<Int>) {
        // 먼저 각 유저가 이모티콘을 탐색하면서 구독 + 결제 처리
        val temp = IntArray(2)

        for (user in users) {
            var userSum = 0
            for (e in 0..emoticons.lastIndex) {
                if (user[0] <= rates[e]) {
                    userSum += ((emoticons[e] * (100 - rates[e])) / 100.0).toInt()
                }
            }


            if (userSum >= user[1]) {
                temp[0] += 1
            } else temp[1] += userSum
        }

        if (answer[0] < temp[0]) {
            answer = temp
        } else if (answer[0] == temp[0] && answer[1] < temp[1]) {
            answer = temp
        }
    }
}