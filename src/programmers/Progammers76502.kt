import java.util.*

class Progammers76502 {
    private val stack = Stack<Char>()

    fun solution(s: String): Int {
        var answer: Int = 0

        val m = s.length

        for (i in 0..s.lastIndex) {
            val r = i // 왼쪽으로 회전하려는 수 == 시작할 인덱스
            for (j in r..s.lastIndex + r) {
                val v = s[j % m]
                if (stack.isEmpty()) {
                    stack.push(v)
                } else {
                    var peek = stack.peek()
                    if (peek == '[' || peek == '(' || peek == '{') {
                        if (peek == '[' && v == ']') {
                            stack.pop()
                        } else if (peek == '(' && v == ')') {
                            stack.pop()
                        } else if (peek == '{' && v == '}') {
                            stack.pop()
                        } else if (v == '{' || v == '(' || v == '[') {
                            stack.push(v)
                        } else {
                            break
                        }
                    }
                }
            }
            if (stack.isEmpty()) answer++
            stack.clear()
        }

        return answer
    }
}

// 회전  k % m 모듈러 연산
