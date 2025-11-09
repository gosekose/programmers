class Programmers132265 {
    fun solution(topping: IntArray): Int {
        var answer: Int = 0

        val fSet = mutableSetOf<Int>()
        val fArr = IntArray(topping.size)

        for (i in 0..topping.lastIndex) {
            val key = topping[i]
            if (!fSet.contains(key)) fSet.add(key)
            fArr[i] = fSet.size
        } // 전방

        val bSet = mutableSetOf<Int>()
        val bArr = IntArray(topping.size)

        for (i in topping.lastIndex downTo 0) {
            val key = topping[i]
            if (!bSet.contains(key)) bSet.add(key)
            bArr[i] = bSet.size
        } // 후방

        for (p in 0..topping.lastIndex - 1) { // 포인터
            if (fArr[p] == bArr[p+1]) answer++
        }

        return answer
    }
}
// 롤케이크 크기 x, 토핑개수 x, 동일한 가지수 0
// 공평하게 자르는 개수
// 1, 2, 1, 3, 1, 4, 1, 2
// 전방 순회, 역순 순회
// 전방 순회 가지수 리스트: 1, 2, 2, 3, 3, 4, 4, 4
// 역순 순회 가지수 리스트: 4, 4, 4, 4, 3, 3, 2, 1
// p 포인터 (왼쪽은 해당 인덱스, 오른쪽은 다음항) (p: 0, lastIndex - 1)
// 둘 다 배열로 넣기 (역순 인덱스)