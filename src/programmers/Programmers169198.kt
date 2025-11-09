import kotlin.math.*

class Programmers169198 {
    fun solution(m: Int, n: Int, startX: Int, startY: Int, balls: Array<IntArray>): IntArray {
        var answer = mutableListOf<Int>()
        val start = Loc(startX, startY)
        val range = Loc(m, n)

        balls.forEach { ballArray ->
            var min = Int.MAX_VALUE
            val ball = Loc(ballArray[0], ballArray[1])

            if (startX == ball.x) {  // X 축이 동일한 경우
                if (startY > ball.y) { // 시작점이 더 큰 경우 D는 불가
                    min = getMinDistance(listOf(Axis.U, Axis.L, Axis.R), start, ball, range)
                } else { // 타겟 y가 더 큼 -> U 불가
                    min = getMinDistance(listOf(Axis.D, Axis.L, Axis.R), start, ball, range)
                }
            } else if (startY == ball.y) { // y축이 동일한 경우
                if (startX > ball.x) { // 기준의 X가 더 큼 -> left X
                    min = getMinDistance(listOf(Axis.U, Axis.D, Axis.R), start, ball, range)
                } else { // right 불가
                    min = getMinDistance(listOf(Axis.U, Axis.D, Axis.L), start, ball, range)
                }
            } else { // 전부 가능
                min = getMinDistance(listOf(Axis.U, Axis.D, Axis.L, Axis.R), start, ball, range)
            }

            answer.add(min)
        }

        return answer.toIntArray()
    }

    private fun getMinDistance(axises: List<Axis>, start: Loc, loc: Loc, range: Loc): Int {
        var min = Int.MAX_VALUE

        axises.forEach { axis ->
            val point = Loc(loc.x, loc.y)

            when(axis) {
                Axis.U -> point.y += (range.y - point.y) * 2
                Axis.D -> point.y -= point.y * 2
                Axis.L -> point.x -= point.x * 2
                Axis.R -> point.x += (range.x - point.x) * 2
            }
            min = Math.min(min, getDistance(start, point))
        }

        return min
    }

    private fun getDistance(start: Loc, end: Loc): Int {
        return (end.x - start.x) * (end.x - start.x) + (end.y - start.y) * (end.y - start.y)
    }

    enum class Axis {
        U,
        D,
        L,
        R
    }
    data class Loc(
        var x: Int,
        var y: Int,
    )
}