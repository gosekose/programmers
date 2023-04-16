package pro_148653;

class Solution {

    public int solution(int storey) {
        return elevator(storey);
    }
    
    private int elevator(int floor) {
        
        if (floor <= 1) return floor;
        
        int remainder = floor % 10;
        int newFloor = floor / 10;
        
        int r1 = remainder + elevator(newFloor);
        int r2 = (10 - remainder) + elevator(newFloor + 1);
        return Math.min(r1, r2);
    }
    
}