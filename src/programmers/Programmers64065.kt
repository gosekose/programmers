class Programmers64065 {
    val answer = mutableListOf<Int>()
    fun solution(s: String): IntArray {

        val set = mutableSetOf<Int>()
        val arr = mutableListOf<MutableList<Int>>() // 개수로 정렬 예정

        var number = ""
        for (i in 1..s.lastIndex - 1) { // 양끝 제거
            if (s[i] == '{') {
                arr.add(mutableListOf<Int>()) // 새로운 리스트 추가
            } else if (s[i] == '}' || s[i] == ',') {
                if (number != "") {
                    arr[arr.lastIndex].add(number.toInt()) // 마지막 리스트에 값 추가
                    number = "" // 초기화
                }
            } else {
                number += s[i] // s 업데이트
            }
        }

        arr.sortBy { it.size } // 리스트의 사이즈로 정렬

        for (ar in arr) {
            for (j in ar) {
                if (!set.contains(j)) { // 키가 없다면
                    set.add(j)
                    answer.add(j) // 정답에 추가
                }
            }
        }

        return answer.toIntArray()
    }
}
// 중복 요소 가능
// 순서 다르면 다른 튜플
// 중복되는 원소가 없는 튜플
// 집합은 순서 상관 x
// 배열의 순서는 상관이 없는건가? --> 상관 있음
// a1: 1개
// a1, a2: 2개 (내부 순서는 바뀌되 들어가는 값은 정해짐)

// set에 키가 없으면 list에 add