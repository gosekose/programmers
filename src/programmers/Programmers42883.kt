import java.util.*

class Programmers42883 {
    private val stack = Stack<Int>()

    fun solution(number: String, k: Int): String {
        var t = k

        for (n in number) {
            val num = n.toString().toInt()

            if (stack.isEmpty()) {
                stack.push(num)
                continue
            }

            if (t > 0) { // 제거해야할 개수가 아직 있는 경우
                while (stack.isNotEmpty() && stack.peek() < num) {
                    stack.pop() // 스택에서 빼기
                    t -= 1

                    if (t <= 0) break // 빼야 할 개수가 없다면 종료
                }
            }
            stack.push(num)
        }
        
        
        while (t > 0 && stack.isNotEmpty()) {
            t -= 1
            stack.pop()
        }

        return stack.joinToString("")
    }
}
// k개를 선택해야함, k를 소비할 수 있는 개수 중에서 가장 큰수가 맨 앞으로 오도록 설정
// 스택 선언
// stack이 빈 경우 push
// k > 0 인 경우, 스택의 마지막 값이 현재보다 작다면 빼기, k 개수 줄임 (반복 작업)
// 스택이 비었거나, k < 0 이면 멈춤
// 스택에 추가 , 마지막 제거 해야할 개수가 남아있다면, 남은 개수만큼 스택에서 빼기
