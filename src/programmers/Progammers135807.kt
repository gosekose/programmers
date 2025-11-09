import kotlin.math.*

class Progammers135807 {
    fun solution(arrayA: IntArray, arrayB: IntArray): Int {
        var answer: Int = 0

        var gcdA = arrayA[0]
        for (i in 1..arrayA.lastIndex) {
            gcdA = gcd(gcdA, arrayA[i])
        }

        var gcdB = arrayB[0]
        for (i in 1..arrayB.lastIndex) {
            gcdB = gcd(gcdB, arrayB[i])
        }

        var aPossible = true
        for (i in 0..arrayB.lastIndex) {
            if(arrayB[i] % gcdA == 0) {
                aPossible = false
                break
            }
        }

        var bPossible = true
        for (i in 0..arrayA.lastIndex) {
            if(arrayA[i] % gcdB == 0) {
                bPossible = false
                break
            }
        }

        if (aPossible && bPossible) {
            return max(gcdA, gcdB)
        } else if (aPossible) {
            return gcdA
        } else if (bPossible) {
            return gcdB
        }

        return 0
    }



    private fun gcd(a: Int, b: Int): Int { // 유클리드 호제법
        if (b == 0) return a
        return gcd(b, a % b)
    }
}

// 가장 큰 양의 정수
// 조건: 철수 모든 카드 나눔 가능 / 영희 카드 모두 불가  // 영희 카드 모두 가능 / 철수 카드 모두 불가

// A 배열의 최대 공약수 a
// B 배열의 최대 공약수 b

// a -> b에 속하는 것 있는지 파악
// b -> a에 속하는 것 있는지 파악

// 둘 다 존재한다면 더 큰 수, 둘 다 불가하면 0

// 최대 공약수로 나눠지는 수가 있다면, 최대공약수의 약수로 나눌 때도 나눠진다
// 100 을 10으로 나눌 떄 나눠진다면, 10의 약수로도 나눌 수 있음
