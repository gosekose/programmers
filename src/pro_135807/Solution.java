package pro_135807;

class Solution {
    public int solution(int[] arrayA, int[] arrayB) {
        
        int gcdA = findGCD(arrayA); // gcdA 찾기
        int gcdB = findGCD(arrayB); // gcdB 찾기
        
        if (gcdA == gcdB) return 0; // 두 최대 공약수가 같다면 조건 만족할 수 없음 (ex: 8 , 8) 
        
        boolean isGCDA = isGCD(gcdA, arrayB); // gcdA로 arrayB를 모든 원소를 나눌 수 있는지
        boolean isGCDB = isGCD(gcdB, arrayA); // gcdB로 arrayB를 모든 원소를 나눌 수 있는지
        
        if (!isGCDA && !isGCDB) return gcdA <= gcdB ? gcdB : gcdA; // 둘 다 나눌 수 없다면 더 큰 수 선택
        else if (!isGCDA) return gcdA; 
        else if (!isGCDB) return gcdB;
       
        return 0; // 두 최대 공약수로 나눠지는 원소가 하나라도 있다면 만족 X
    }
    
    
    int findGCD(int[] arr) { // 각 배열의 원소를 getGCD 재귀를 호출하여 최대 공약수 판단
        int gcd = arr[0];  
        for (int num : arr) gcd = getGCD(gcd, num);
        return gcd;
    }
    
    int getGCD(int x, int y) { // 유클리드 호재법
        if (y == 0) return x;  
        return getGCD(y, x % y); 
        // x = 8, y = 4
        // 4, 0 (8 % 4)
        // y == 0, return 4
    }
    
    boolean isGCD(int gcd, int[] arr) { // arr 배열에 gcd가 나눌 수 있는 수가 있는지 파악
        for (int num : arr) if (num % gcd == 0) return true; 
        return false;
    }

}
