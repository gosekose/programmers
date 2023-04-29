package pro_118667;

import java.util.*;

class Solution {
    
    public int solution(int[] arr1, int[] arr2) {
        
        long arr1Sum = 0L; // 합을 위한 long 타입 초기화
        long arr2Sum = 0L;
        
        Queue<Integer> arr1Queue = new ArrayDeque<>();
        Queue<Integer> arr2Queue = new ArrayDeque<>();
        
        for (int i : arr1) {
            arr1Sum += i;
            arr1Queue.add(i);
        }
        for (int i : arr2) {
            arr2Sum += i;
            arr2Queue.add(i);
        }
        
        if (arr1Sum == arr2Sum) return 0; // 이미 합이 한 번에 같다면 종료
        
        EqualSum es = new EqualSum(arr1Sum, arr2Sum, arr1Queue, arr2Queue);
        
        return es.find();
    }

}

class EqualSum {
    
    int totalJob; // 최소 수행 과정
    int limit; // 무한 루프 제한 조건
    QueueSize big; // 합이 큰 Queue를 저장하려는 참조 QueueSize 인스턴스
    QueueSize small; // 합이 작은 Queue를 저장하려는 참조 인스턴스
    
    class QueueSize {
        long sum; // 합 저장
        Queue<Integer> queue; // 큐 참조
    
        QueueSize(long sum, Queue<Integer> queue) {
            this.sum = sum; 
            this.queue = queue;
        }  
    }
    
    EqualSum(long arr1Sum, long arr2Sum, Queue<Integer> arr1Queue, Queue<Integer> arr2Queue) {            
        if (arr1Sum >= arr2Sum) { // arr1Sum이 더 크면 big에 arr1Queue, small = arr2Queue
            big = new QueueSize(arr1Sum, arr1Queue);
            small = new QueueSize(arr2Sum, arr2Queue);         
            
        } else { // arr2Sum이 더 크다면 big에 arr2Queue 설정, small = arr1Queue
            big = new QueueSize(arr2Sum, arr2Queue);
            small = new QueueSize(arr1Sum, arr1Queue);
        }
        
        // 참조 #1
        limit = big.queue.size() * 3; // 한계치 설정을 위해 limit에 큐 크기 * 3 
    }
    
    void swap() { // 만약 queue에 있는 sum 크기가 달라진다면 swap으로 queue 참조 바꾸기
        QueueSize tmp = null; // swap을 위한 QueueSize tmp 생성
        if (big.sum < small.sum) {
            tmp = small;
            small = big;
            big = tmp;
        }
    }
    
    int find() {
        
        // 참조 #2
        while(!big.queue.isEmpty() && !small.queue.isEmpty()) { // 큐에서 더 이상 뺄 수 없는 경우 종료

            int pop = big.queue.poll(); // 합이 더 큰 queue에서 빼기
            small.queue.add(pop); // 작은 큐에 더하기
            
            big.sum -= pop; // 빅 큐 합 업데이트
            small.sum += pop; // 스몰 큐 합 업데이트
            totalJob++; // totalJob 업데이트
            
            if (big.sum == small.sum) return totalJob; // 두 큐의 합이 같다면 종료    
            if (big.sum < small.sum) swap(); // 만약 스몰 큐의 합이 더 크면 스왑
            
            if (totalJob > limit) break; // 큐 교체의 한계보다 더 크다면 무한 루프 해제
        }
        
        return -1; // 두 큐의 합을 같게 만들 수 없음
    }
}