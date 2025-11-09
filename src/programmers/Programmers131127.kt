class Programmers131127 {

    private val map = mutableMapOf<String, Int>()
    private var answer: Int = 0

    fun solution(want: Array<String>, number: IntArray, discount: Array<String>): Int {
        for (i in 0..9) {
            val name = discount[i]
            map[name] = map.getOrDefault(name, 0) + 1
        } // 10개 default

        if (isPossible(want, number)) answer++

        var left = 0
        var right = 9 // 슬라이싱
        while(right < discount.lastIndex) {
            val leftName = discount[left++]
            map[leftName] = map.getOrDefault(leftName, 0) - 1

            val rightName = discount[++right]
            map[rightName] = map.getOrDefault(rightName, 0) + 1

            if (isPossible(want, number)) answer++
        }

        return answer
    }

    private fun isPossible(want: Array<String>, number: IntArray): Boolean {
        for (i in 0..want.lastIndex) {
            val name = want[i]
            val mustCnt = number[i]

            if (mustCnt != map.getOrDefault(name, 0)) return false
        }
        return true
    }
}
// 10일 , 한가지 할인, 하루에 하나
// 10일 단위 슬라이싱 (sum -> map out, in)
// answer: 총 일수 (모두 가능), 불가는 0
// 1. 1 ~ 10일까지 일단 탐색 후 map에 넣기 가능 여부 체크
// 2. for 문을 돌면서 left 인덱스에 있는 discount 항목 --, right 항목++  o(n)
// 3. 각 for문에서 원하는 항목이 모두 있는지 확인 o(1)
// 리턴