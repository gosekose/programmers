package pro_161988;

import java.util.*;

class Solution {
    public long solution(int[] sequence) {
        
        long[] firstPlus = new long[sequence.length + 1];
        long[] firstMinus = new long[sequence.length + 1];
        
        int now = 1;
        long fPlusSum = 0, fMinusSum = 0; 
        
        for (int i = 0; i < sequence.length; i++) {
            fPlusSum += sequence[i] * now;
            firstPlus[i + 1] = fPlusSum;
            
            now *= -1;
          
            fMinusSum += sequence[i] * now;
            firstMinus[i + 1] = fMinusSum;
        }
        
        long result = Long.MIN_VALUE;
        
        result = twoPointer(sequence, firstPlus, result);
        result = twoPointer(sequence, firstMinus, result);
                
        return result;
    }
    
    private long twoPointer(int[] sequence, long[] sum, long result) {
        
        int left = 0, right = 1;
        long s = 0;
        
        while(right <= sequence.length) {
            s = sum[right] - sum[left];
            if (s >= 0) {
                result = Math.max(result, s);
                right++;
            }   
            
            else {
                left = right++;
            }
        }

        return result;
    }
    
}