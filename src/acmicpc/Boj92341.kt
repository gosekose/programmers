package acmicpc

import kotlin.math.*

class Boj92341 {

    fun solution(fees: IntArray, records: Array<String>): IntArray {
        val (defaultTime, defaultFee, unitTime, unitFee) = fees

        val map = mutableMapOf<String, Item>()

        for (i in 0..records.lastIndex) {
            val (time, num, action) = records[i].split(" ")
            if (action == "OUT") { // 나가는 케이스
                val item = map[num]!!

                val endTime = getTime(time)
                val startTime = item.startTime
                val sumTime = endTime - startTime

                item.startTime = -1
                item.sumTime = item.sumTime + sumTime
                item.status = true

                map[num] = item
            } else { // 새로운 경우 (in)
                val startTime = getTime(time)

                if (map.containsKey(num)) { // 이미 존재할 때,
                    val item = map[num]!!

                    item.startTime = startTime
                    item.sumTime = item.sumTime
                    item.status = false

                    map[num] = item
                } else {
                    map[num] = Item(
                        startTime = startTime,
                        sumTime = 0,
                        status = false,
                    )
                }
            }
        }

        val remains = map.keys.toMutableList()
        remains.sort()

        val endTime = getTime("23:59")

        val result = IntArray(map.keys.size)


        for (i in 0..remains.lastIndex) {
            val remain = remains[i]
            val item = map[remain]!!

            val sumTime = if (!item.status) { // 끝나지 않은 경우
                val startTime = item.startTime
                item.sumTime + (endTime - startTime)
            } else item.sumTime

            val fee = if (defaultTime > sumTime) defaultFee
            else defaultFee + (ceil((sumTime - defaultTime).toDouble() / unitTime.toDouble()) * unitFee).toInt()

            result[i] = fee
        }
        return result
    }

    private fun getTime(time: String): Int {
        val (h, m) = time.split(":").map { it.toInt() }
        return h * 60 + m
    }

    data class Item(
        var startTime: Int,
        var sumTime: Int,
        var status: Boolean,
    )
}