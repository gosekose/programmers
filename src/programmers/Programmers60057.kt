import kotlin.math.*

class Programmers60057 {
    private var answer = Int.MAX_VALUE
    fun solution(s: String): Int {

        for (len in 1..s.length) { // 문자열로 자를 길이
            var first = s.slice(0..len - 1) // 슬라이스로 잘린 첫번째
            var cnt = 1
            var total = 0
            var idx = len - 1 // 현재 인덱스

            while (idx <= s.lastIndex) {
                if (idx + len <= s.lastIndex) { // 다음 반복까지 충분히 크면
                    val next = s.slice(idx + 1..idx + len) // 길이만큼 늘려준 인덱스
                    if (next == first) {
                        // 문자열이 동일하다면
                        cnt++
                    } else {
                        if (cnt != 0) { // cnt가 0이 아님
                            total += min(cnt.toString().length + first.length, cnt * (first.length))
                        } // 이전에 잘린 것 ++ 해주기

                        cnt = 1 // cnt 초기화
                        first = next
                    }
                    idx += len // len만큼 인덱스 증가
                } else { // 다음 반복까지 충분히 크지 않음
                    total += (s.lastIndex - idx) // 현재 인덱스에서 마지막 인덱스까지 빼기
                    break
                }
            }

            if (cnt != 0) { // cnt가 0이 아님
                total += min(cnt.toString().length + first.length, cnt * (first.length))
            }
            answer = min(answer, total)
        }

        return answer
    }
}
// 원칙 앞에서부터 자르기 가능!