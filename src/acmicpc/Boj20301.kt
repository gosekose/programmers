package acmicpc

fun main(args: Array<String>) {
    val (N, K, M) = readln().split(" ").map {it.toInt()}

    val queue = ArrayDeque<Int>()

    for (i in 1..N) {
        queue.add(i); // 해당 번호 입력
    }

    var dir = true // 1: 정방향, 0: 역방향
    var sum = 0 // 현재 방향에서 지워진 개수

    val answer = mutableListOf<Int>()

    while(queue.isNotEmpty()) {
        if (dir) { // 정방향
            var t = K
            while(queue.isNotEmpty()) {
                if (--t <= 0) break

                var cur = queue.removeFirst()
                queue.addLast(cur)
            }

            var cur = queue.removeFirst()
            answer.add(cur)
            sum++
        } else { // 역방향
            var t = K
            while(queue.isNotEmpty()) {
                if (--t <= 0) break

                var cur = queue.removeLast()
                queue.addFirst(cur)
            }

            var cur = queue.removeLast()
            answer.add(cur)
            sum++
        }

        if (sum % M == 0) {
            sum = 0
            dir = !dir
        }
    }

    for (ans in answer) {
        println(ans)
    }
}