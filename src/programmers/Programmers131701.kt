class Programmers131701 {

    fun solution(elements: IntArray): Int {
        val sumSet = mutableSetOf<Int>()

        for (i in 1..elements.size) { // i는 길이
            var sum = 0
            for (j in 0..i-1) { // i길이까지의 인덱스의 합은 첫번째 값
                sum += elements[j]
            }

            var first = 0 // 시작 인덱스
            var last = i-1 // 합의 가장 마지막 인덱스

            while(true) { // 슬라이싱 시작
                sumSet.add(sum)
                sum -= elements[first++] // 시작점은 마지막 인덱스를 넘지 않음
                sum += elements[++last % elements.size] // 마지막 인덱스를 넘을 수 있으므로 모듈러 연산

                if (first > elements.lastIndex) { // 시작 인덱스가 마지막에 오면
                    break
                }
            }
        }

        return sumSet.size
    }
}