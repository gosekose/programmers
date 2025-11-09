class Programmers147354 {

    fun solution(data: Array<IntArray>, col: Int, rB: Int, rE: Int): Int {
        var answer: Int = 0
        data.sortWith(compareBy({it[col-1]}, {-it[0]})) // sort

        answer = data[rB-1].sumBy { it % rB }

        for (i in rB..rE-1) {
            val sI = data[i].sumBy { it % (i+1) }
            answer = answer.xor(sI)
        }

        return answer
    }
}
// 문제 조심할 것
// sb ~ se 사이의 값 더하기