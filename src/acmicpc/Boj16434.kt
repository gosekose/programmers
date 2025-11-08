package acmicpc

const val MAX = 1_000_000_000_000_000_000L * 9L // 최대 체력

fun main(args: Array<String>) {
    val (N, initAtk) = readln().split(" ").map {it.toInt()}

    val rooms = mutableListOf<Info>()
    var answer = MAX

    repeat(N) {
        val (t, a, h) = readln().split(" ").map {it.toLong()}

        rooms.add(Info(t, a, h));
    }

    var left = 1L
    var right = MAX

    while (left <= right) {
        var mid = (left + right) / 2

        // mid는 시작 hp
        var hp = mid
        var atk = initAtk.toLong()

        var isPossible = true
        for (room in rooms) {
            if (room.t == 1L) {

                var monsterHp = room.h
                var monsterAtk = room.a

                // 몬스터의 피를 0 이하로 만드는 최소 공격 횟수
                var minAtk = (monsterHp / atk) + if (monsterHp % atk == 0L)  0L else 1L

                // 몬스터로부터 받는 공격 횟수
                var minDeal = if (minAtk > 0)  minAtk - 1 else 0
                if (hp <= minDeal * monsterAtk) {
                    isPossible = false
                    break
                } else {
                    hp -= (minDeal * monsterAtk)
                }
            } else {
                hp += room.h
                hp = Math.min(mid, hp)
                atk += room.a
            }
        }

        // hp가 모자른 경우 더 늘리기
        if (!isPossible) {
            left = mid + 1
        } else {
            answer = Math.min(answer, mid)
            right = mid - 1
        }
    }

    println(answer)
}

data class Info(
    val t: Long,
    val a: Long,
    val h: Long
)

// 999999000001
// 999999000001
// 1000000000001L
