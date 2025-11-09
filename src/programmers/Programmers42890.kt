class Programmers42890 {
    val container = ArrayList<String>() // 유니크한 후보키
    val candidate = ArrayList<Int>() // 후보키
    var answer = 0

    private lateinit var relation: Array<Array<String>>

    fun solution(input: Array<Array<String>>): Int {
        relation = input

        for (i in 1 .. relation[0].size) {
            findCandidate(0, 0, i)
        }
        return answer
    }

    fun findCandidate(start: Int, cnt: Int, target: Int) {
        if (cnt == target) {
            unique(candidate) // 필요한 개수를 찾았을 때, 중복성 검사
        } else {
            for (i in start until relation[0].size) {
                candidate.add(i)
                findCandidate(i+1, cnt+1, target)
                candidate.removeAt(candidate.lastIndex) // 마지막 인덱스 백트래킹
            }
        }
    }

    fun unique(candidate: ArrayList<Int>) {
        var temp = ""
        candidate.forEach { temp += it } // 0 ~ 8

        container.forEach {
            var cnt = 0
            it.forEach {
                if (temp.contains(it)) cnt++
            }

            if (cnt == it.length) return // 중복되는게 있으면 조기 종료
        }

        val set = mutableSetOf<ArrayList<String>>() // set으로 중복 체크
        for (row in relation.indices) { // row의 인덱스
            val tempB = arrayListOf<String>()

            candidate.forEach { c ->
                tempB.add(relation[row][c])
            }
            set.add(tempB) // 순서를 지키므로 set에 정렬된 리스트가 들어감 -> 중복 체크 가능
        }

        if (set.size == relation.size) {
            answer++
            var tempC = ""
            candidate.forEach { tempC += it }
            container.add(tempC) // 컨테이너에 후보키 넣어줌
        }
    }
}
// 답지 보고 해결
// 1. 유니크한 후보키 ArrayList 선언
// 2. 후보키를 담을 ArrayList<Int>() 선언
// 3. findCandidate로 백트래킹하여 순열 찾음
// 4. 내부에 unique하여 찾은 후보키를 대상으로 중복성 검사를 시행
// 5. 만약 컨테이너에 있는 키로 찾은 것에 후보키가 존재하는지 개수로 체크하여 조기리턴
// 5-1 후보키가 1 인데, temp에 13이 있고, 그 개수가 모두 동일하다면 종료
// 6.mutableSetOf<ArrayList<String>>으로 키에 대한 중복성 검사 시행
// 7. 후보키를 순회하여 tempB에 값을 넣음
// 8. 후보키에 대한 값을 set에 넣어서 중복성 검사 체크
// 9. 만약 유니크함이 증명되면, answer++ 해주고, container에 후보키 넣어줌