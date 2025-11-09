import java.util.*

class Programmers60058 {
    fun solution(p: String): String {
        if (p == "") return "" // 조기 리턴
        return recur(p)
    }

    private fun recur(p: String): String { // 리턴 값은 올바른 문자열이여야 함
        var u = ""
        var v = ""
        var balanceU = ""
        var isFirst = false
        var uL = 0
        var uR = 0

        val stack = Stack<Char>()

        for (s in p) {
            if (stack.isEmpty()) stack.push(s)
            if (stack.isNotEmpty()) {
                if (stack.peek() == '(' && s == ')') {
                    stack.pop()
                } else stack.push(s)
            }
        }

        if (stack.isEmpty()) return p

        stack.clear()

        for (s in p) {
            if (stack.isEmpty()) {
                if (u != "") break // 올바른 문자열
                stack.push(s)
            }
            else {
                if (stack.peek() == '(' && s == ')') {
                    stack.pop()
                } else stack.push(s)
            }

            if (s == '(') uL++
            else uR++

            u += s // 문자열 추가
            if (uL != 0 && uL == uR && !isFirst) {
                balanceU = u
                isFirst = true
            }
        }

        if (stack.isEmpty()) {
            val tempV = p.slice(u.length .. p.lastIndex)
            for (t in tempV) { // 올바른 문자열 작업일 경우
                if (stack.isNotEmpty()) {
                    if (stack.peek() == '(' && t == ')') {
                        stack.pop()
                    }
                }
                v += t // 문자열 추가
                stack.push(t)
            }

            if (stack.isEmpty()) {
                // v도 완성된 문자열이라면 리턴
                return u + v // 문자열 이어 붙임
            }
            return u + recur(v)
        } else { // 올바르지 않은 문자열인 경우
            u = balanceU
            val tempV = p.slice(u.length .. p.lastIndex)
            var temp = "("
            temp += recur(tempV) // v에 대한재귀 결과
            temp += ")"
            for (i in 1 .. u.lastIndex-1) {
                temp += if (u[i] == '(') ')' else '('
            }
            return temp
        }
    }
}
// () 개수가 같다면 균형잡긴 괄호 문자열
// () 괄호의 짝도 맞음 올바른 괄호 문자열
// w -> u, v
// u: (())
// v: )(
// v: 1단계부터 시작
// 균형잡힌 문자열이 될떄까지 반복
// u가 올바른 괄호 문자열이 아니라면,
// 빈문자열에 첫번째 문자로 ( 를 붙임