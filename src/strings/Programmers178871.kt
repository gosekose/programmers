package strings

class Programmers178871 {
    fun solution(players: Array<String>, callings: Array<String>): Array<String> {
        val map = players.withIndex().associate { it.value to it.index }.toMutableMap()

        for (i in 0 .. callings.lastIndex) {
            val target = callings[i]
            val index = map[target]!!
            val prePlayerName = players[index - 1]

            players[index - 1] = target
            players[index] = prePlayerName

            map[target] = index - 1
            map[prePlayerName] = index
        }

        return players
    }
}