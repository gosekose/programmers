class Programmers172927 {
    fun solution(picks: IntArray, minerals: Array<String>): Int {
        var answer: Int = 0
        
        val picksCnt = picks.sum()
        val tireds = Array(picksCnt) { IntArray(3) }
        
        val lastIdx = Math.min(picksCnt * 5 - 1, minerals.lastIndex)
        
        for (i in 0..lastIdx) {
            val row = i / 5
            val col = getCol(minerals[i])
            
            tireds[row][col]++
        }
        
        tireds.sortWith(compareBy( {-it[0]}, {-it[1]}, {-it[2]} ))
        
        val pickList = mutableListOf<Int>()
        picks.forEachIndexed { idx, pick ->
            repeat(pick) {
                pickList.add(idx)
            }
        }
        
        pickList.forEachIndexed { idx, pick ->
            answer += tireds[idx][0] * getTired(pick, "diamond")
            answer += tireds[idx][1] * getTired(pick, "iron")
            answer += tireds[idx][2] * getTired(pick, "stone")
        }
        
        
        return answer
    }
    
    private fun getCol(mineral: String): Int {
        return when(mineral) {
            "diamond" -> 0
            "iron" -> 1
            else -> 2
        }
    }
    
    private fun getTired(pick: Int, mineral: String): Int {
        return when(pick) {
            0 -> 1
            1 -> when(mineral) {
                "diamond" -> 5
                else -> 1
            }
            else -> when(mineral) {
                "diamond" -> 25
                "iron" -> 5
                else -> 1
            }
        }
    }
}
