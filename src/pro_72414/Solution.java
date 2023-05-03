package pro_72414;

import static java.lang.Integer.parseInt;

class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        
        if (play_time.equals(adv_time)) return "00:00:00";

        int[] allTime = new int[getSecondsTime(play_time) + 1]; // 00:00:00 ~ 00:00:02은 2개의 배열 공간 필요
        
        for (String log : logs) {
            String[] times = log.split("-");
            String sTime = times[0];
            String eTime = times[1];
            
            int start = getSecondsTime(sTime);
            int end = getSecondsTime(eTime);
            
            allTime[start] += 1; // 누적합을 위한 시작 지점 
            allTime[end] -= 1; // 누적합을 위한 마지막 지점에 -1  // 00:00:00 ~ 00:00:02 [1, 1, 0] (00:00 ~ 00:01, 00:00)
        }
        
        for (int i = 1; i < allTime.length; i++) {
            allTime[i] = allTime[i - 1] + allTime[i]; // 이전항에 누적합으로 더함 (00:00:00 ~ 00:00:10)
        }     
        
        //[1, 1, 0, 2, 2, 2, 1, 1, 0, 1, 0]
        int advTime = getSecondsTime(adv_time); // 00:00:00 ~ 00:00:02 (2초)
        long sum = 0;
        
        for (int i = 0; i < advTime; i++) { // advTime이 2초 하면 
            sum += allTime[i];  // ex) 0초부터 ~ 2초까지 누적합을 계산 [1, 1, 0] -> 2
        }
                
        int idx = advTime; // 시작점을 advTime 으로 설정 (2)
        long maxSum = sum; // 가장 큰 토탈 시간
        int startPoint = 0; // 가장 큰 토탈 시간에 맞는 포인트 
        int startCnt = 0; // 누적합에서 포인터를 이동시킬 cnt
        
        // idx = 3, 3초까지의 누적합 0 ~ 1초까지의 누적합 
        for (int i = idx; i < allTime.length; i++) {
            
            sum = sum + allTime[i] - allTime[startCnt]; // 100초까지 누적합에서 101초까지의 누적합 - 0초 (0 ~ 1초)
            startCnt++;
            
            if (maxSum < sum) {
                maxSum = sum;
                startPoint = startCnt; // 시작점은 누적합에 포함되기 시작한 (= 누적합에서 빠진 마지막 인덱스 + 1)   
            }
        }
        
        return getTime(startPoint);
    }
    
    private int getSecondsTime(String times) {
        String[] time = times.split(":");
        return parseInt(time[0]) * 60 * 60 + parseInt(time[1]) * 60 + parseInt(time[2]);
    }
    
    private String getTime(int point) {
        int hour = point / 3600;
        int minute = (point % 3600) / 60;
        int seconds = (point % 3600) % 60;
            
        return getStringTime(hour) + ":" + getStringTime(minute) + ":" + getStringTime(seconds);
    }
    
    private String getStringTime(int time) {
        String result = "";
        if (time < 10) result = "0" + String.valueOf(time);
        else result = String.valueOf(time);
        return result;
    }
}

class Test {

    public static void main(String[] args) {

        int[] point = {4, 8};
        int[] arr = new int[10];

        for (int i = point[0]; i <= point[1]; i++) {
            arr[i] += 1;
        }

        int sum = 0;

        for (int i : arr) {
            sum += i;
        }

        System.out.println("sum = " + sum);


        int[] arr2 = new int[10];

        arr2[point[0]] += 1;
        arr2[point[1] + 1] -= 1;

        int sum2 = 0;

        for (int i = 1; i < arr2.length; i++) {
            arr2[i] = arr2[i] + arr2[i - 1];
        }

        for (int i = 0; i < arr2.length; i++) {
            sum2 += arr2[i];
        }

        System.out.println("sum2 = " + sum2);



    }
}



//
// -----
// -------
// --------
//

// 구간별 time을 배열로 저장
// 구간 길이로 각 내부의 합을 구한 후 ======== 한칸씩 옮기며 99 * 60 * 60 + 59 * 60 + 59  약 36만
// logs 30만 * 36만 62억
// 구간별로 플레이타임을 나눈다면 ?
// 출발시간 별로 구간을나눔
// 1 - 21
// 누적합 개념
// 1 0 1  0 0 0  -1 0 0  0  0
// 1 1 2  2 2 2   1 1 1  1  0

// 누적합 --> 시작 1  마지막 인덱스 +1 에 -1 만약 끝이라면 x
