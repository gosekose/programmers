class Programmers92342 {
    private lateinit var apeach: IntArray

    private var diff = 0

    private val arr = IntArray(11)

    fun solution(n: Int, info: IntArray): IntArray {
        apeach = info

        for (i in 0..info.lastIndex) { // info의 마지막 인덱스 까지 순회
            combi(
                start = 0,
                cnt = 0,
                remains = n,
                array = IntArray(11),
            )
        }

        return if (arr.sum() == 0) return IntArray(1) { -1 } else arr
    }

    private fun combi(start: Int, cnt: Int, remains: Int, array: IntArray) {
        if (cnt == 11 || remains == 0) {
            getResult(array = array, remains = remains)
            return
        }

        for (i in start until 11) {
            val t = apeach[i]
            if (remains > t) {
                array[i] = t + 1
                combi(i + 1, cnt + 1, remains - (t + 1), array)
                array[i] = 0 // 초기화
            } else {
                array[i] = 0
                combi(i + 1, cnt + 1, remains, array)
            }
        }
    }

    private fun getResult(array: IntArray, remains: Int) {
        var lionSum = 0
        var apeachSum = 0

        for (i in 0 until 11) {
            if (apeach[i] >= array[i]) {
                if (apeach[i] != 0) apeachSum += (10 - i)
            } else lionSum += (10 - i)
        }

        if (lionSum > apeachSum && (lionSum - apeachSum) > diff) {
            for (i in 0 until 11) {
                arr[i] = array[i]
                if (i == 10) arr[i] = array[i] + remains // 가장 마지막 인덱스에 추가해주기
            }

            diff = lionSum - apeachSum
        } else if (lionSum > apeachSum && (lionSum - apeachSum) == diff) {
            var isPossible = false
            for (i in 10 downTo 0) {
                if (arr[i] < array[i]) {
                    isPossible = true
                    break
                } else if (arr[i] == array[i]) continue
                else break
            }

            if (isPossible) {
                for (i in 0 until 11) {
                    arr[i] = array[i]
                    if (i == 10) arr[i] = array[i] + remains // 가장 마지막 인덱스에 추가해주기
                }
            }
        }
    }
}