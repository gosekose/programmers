class Programmers148653 {
    fun solution(storey: Int): Int {
        var answer: Int = 0

        var v = storey

        while(v > 0) {
            val remainder = v % 10

            if (remainder > 5) {
                answer += (10 - remainder)
                v += (10 - remainder)
            }

            else if (remainder < 5) {
                answer += remainder
            }

            else {
                if (((v) / 10) % 10 > 4) {
                    v += (10 - remainder)
                }
                answer += remainder
            }

            v /= 10
        }

        return answer
    }
}
// 모듈러 연산
// 현재 자리 수 -> 6 ~ 9: 10으로 만들기
// 현재 자리 수 -> 0 ~ 4: 0으로 만들기
// 현재 자리 수 -> 5:
//         다음 자리수가 5 이상이면 10으로 만들기
//                   4 이하면 0에 가깝게 만들기
// 상황에 따라 v를 조정해서 올려준 후 모듈러 연산 실행