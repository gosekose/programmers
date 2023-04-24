package pro_42860;

class Solution {
    public int solution(String name) {
        int answer = 0;
        int length = name.length();
        
        int sequenceA;
        int move = length - 1; // move = 5
        
        //AABABA
        // "BBBBAAAAAAAB" 12 - 0
        // i = 0;
        // sA = 1;
        // sA = 2; 
        // i = 0 * 2 + 6 - 2 = 4
        // (6 - 2)  * 2 + 0 = 8
        for (int i = 0; i < name.length(); i++) {
            answer += Math.min(name.charAt(i) - 'A', 'Z' - name.charAt(i) + 1);
            sequenceA = i + 1;
            
            while (sequenceA < length && name.charAt(sequenceA) == 'A') {
                sequenceA++;
            }
            
            move = Math.min(move, i * 2 + length - sequenceA); 
            move = Math.min(move, (length - sequenceA) * 2 + i);  
            // (length - sequenceA) * 2 -> 뒤로 먼저 갔다가 다시 원점으로 복귀 "BBBBAAAAAAAB" -> BBBB 까지 i --> 5
        }
        
        return answer + move;
    }
}