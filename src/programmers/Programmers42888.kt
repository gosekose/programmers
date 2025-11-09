class Programmers42888 {

    private val map = mutableMapOf<String, String>()
    private val answer = mutableListOf<String>()
    fun solution(record: Array<String>): Array<String> {

        for (r in record) {
            if (r[0] == 'E') {
                val (command, id, name) = r.split(" ")
                map[id] = name
            }

            if (r[0] == 'C') {
                val (command, id, name) = r.split(" ")
                map[id] = name // 갱신
            }
        }

        for (r in record) {
            if (r[0] == 'E') {
                val (command, id, name) = r.split(" ")
                answer.add("${map[id]}님이 ${getMessage(command)}")
            } else if (r[0] == 'L') {
                val (command, id) = r.split(" ")
                answer.add("${map[id]}님이 ${getMessage(command)}")
            }
        }

        return answer.toTypedArray()
    }

    private fun getMessage(command: String): String {
        return when(command) {
            "Enter" -> "들어왔습니다."
            "Leave" -> "나갔습니다."
            else -> ""
        }
    }
}
// 채팅방을 나간 후 새로운 닉네임, 닉네임 변경
// 기존방에 출력된 메세지 닉네임 '전부' 변경
// 채팅방에 들어오고 나거가나, 닉네임 변경한 기록이 담긴 문자열 배열
// '최종적으로 방을 개설한 사람이 보게 되는 메세지'
// 유저 id -> pk
// Enter, Leave, Change


// record
// 큐 or 스택 or map -> map
// for 빅오 -> Map 갱신
// for 돌면서 문구 출력
// o(n)
// 20분.