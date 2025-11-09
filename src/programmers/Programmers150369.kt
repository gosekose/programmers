class Programmers150369 {
    fun solution(cap: Int, n: Int, deliveries: IntArray, pickups: IntArray): Long {
        var answer: Long = 0

        var delivery = 0
        var pickUp = 0

        for (i in n - 1 downTo 0) {
            delivery += deliveries[i]
            pickUp += pickups[i]

            while (delivery > 0 || pickUp > 0) {
                delivery -= cap
                pickUp -= cap

                answer += (i + 1) * 2
            }
        }
        return answer
    }
}