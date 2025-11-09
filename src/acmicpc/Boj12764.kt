package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

data class Node12764(
    val start: Int,
    val end: Int,
    val num: Int
)

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val N = br.readLine().toInt()

    val pq = PriorityQueue<Node12764>() {a, b -> if (a.end == b.end) a.start - b.start else a.end - b.end }
    val peoples = mutableListOf<Node12764>()

    val cntMap = mutableMapOf<Int, Int>()
    var size = 1

    for (i in 0 until N) {
        val str = StringTokenizer(br.readLine())
        val start = str.nextToken().toInt()
        val end   = str.nextToken().toInt()

        peoples.add(Node12764(start, end, 0))
    }

    peoples.sortWith { a, b -> if (a.start == b.start) a.end - b.end else a.start - b.start }
    val pqe = PriorityQueue<Int>() {a, b -> a - b}// 현재 빈자리 큐

    for (people in peoples) {
        val (start, end, num) = people

        /* 종료시간보다 시작 시간이 더 큰 경우 큐에서 빼고 세팅할 수 있음*/
        var minNum = pq.size + 1

        if (pq.isEmpty()) {
            pq.add(Node12764(start, end, 1))
        } else {
            var check = false
            while(pq.isNotEmpty()) {
                if (pq.peek().end <= start) {
                    val node = pq.poll()
                    pqe.add(node.num)
                } else {
                    // 비어있는 큐가 없으므로
                    if (pqe.isEmpty()) {
                        pq.add(Node12764(start, end, minNum))
                    } else {
                        val eNum = pqe.poll()
                        pq.add(Node12764(start, end, eNum))
                        minNum = eNum
                    }
                    check = true
                    break
                }
            }

            if(!check) {
                if (pqe.isEmpty()) {
                    pq.add(Node12764(start, end, minNum))
                } else {
                    val eNum = pqe.poll()
                    pq.add(Node12764(start, end, eNum))
                    minNum = eNum
                }
            }
        }

        // 해당 자리에 대한 값 증가시키기
        if (cntMap.containsKey(minNum)) {
            cntMap[minNum] = cntMap[minNum]!! + 1
        } else{
            cntMap[minNum] = 1
        }

        size = max(size, pq.size) // 현재시간까지 가장 최대 자리수로 세팅
    }

    val sb = StringBuilder()

    sb.append(size).append("\n")

    for (i in 1 .. size) {
        sb.append(cntMap.getOrDefault(i, 0))

        if (i != size) {
            sb.append(" ")
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}