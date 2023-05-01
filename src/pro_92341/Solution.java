package pro_92341;

import java.util.*;
import static java.lang.Integer.parseInt;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        Parking park = new Parking(fees); // 인스턴스 생성
        return park.apply(records);
    }   
}

class Parking {
    static int basicTime; // 기본 제공 시간
    static int basicFee; // 기본 요금
    static int unit; // 단위 시간
    static int unitFee; // 단위 요금
    static final int LAST_TIME = 1439; // 1380 + 59 = 1439 (23:59)
    
    Map<String, ParkFee> map = new HashMap<>(); // 차량 번호 기록 map
    
    class ParkFee {
        int inTime; // 입차
        int usedTime; // 누적 사용 시간

        ParkFee(int inTime) {
            this.inTime = inTime;
        }
    }
    
    Parking(int[] fees) { // 초기화
        basicTime = fees[0];
        basicFee = fees[1];
        unit = fees[2];
        unitFee = fees[3];
    }
    
    void add(String carNumber, int inTime) { // time: 분으로 설정된 시간
        if (map.containsKey(carNumber)) { 
            ParkFee parkFee = map.get(carNumber); // 차량 시간이 있다면 intime 값 변경
            parkFee.inTime = inTime;
        } else map.put(carNumber, new ParkFee(inTime)); // 없다면 인스턴스 생성
    }
    
    int out(String carNumber, int outTime) {     
        ParkFee parkFee = map.get(carNumber); 
        return outTime - parkFee.inTime; // 출차 - 입차 시간 계산
    }
    
    int calFee(int usedTime) {
        if (usedTime <= basicTime) return basicFee; // 만약 기본 제공 시간보다 적은 경우 기본 요금
        int additionalTime = (usedTime - basicTime) % unit == 0 ?  (usedTime - basicTime) / unit : ((usedTime - basicTime) / unit) + 1;   // 단위 계산을 위한 올림 계산
        return basicFee + additionalTime * unitFee; // 기본 요금 + 추가 시간 * 단위 비용
    }
    
    
    int[] apply (String[] records) {
        for (String record : records) {
            String[] rc = record.split(" ");
            String[] timeZone = rc[0].split(":");
            int time = parseInt(timeZone[0]) * 60 + parseInt(timeZone[1]);
            
            String carNumber = rc[1];
            boolean in = rc[2].equals("IN");
            
            if (in) add(carNumber, time);
            
            else {
                ParkFee parkFee = map.get(carNumber);
                parkFee.usedTime += out(carNumber, time); // 출차 - 입차로 누적 시간 업데이트
                parkFee.inTime = -1; // 00:00의 경우 inTime이 0이므로 -1로 설정
            }          
        }
        
        List<String> keys = new ArrayList<>();
        keys.addAll(map.keySet()); // ketSet (차 번호) 모두 keys에 업데이트
        keys.sort(Comparator.comparing(s -> s)); // 차 번호로 정렬
        
        int[] result = new int[keys.size()];
        
        for (int i = 0; i < keys.size(); i++) {
            ParkFee parkFee = map.get(keys.get(i));
           
            int fee = 0;
            if (parkFee.inTime != -1) {
                parkFee.usedTime += LAST_TIME - parkFee.inTime; // 누적 시간 업데이트
            } 
            
            result[i] = calFee(parkFee.usedTime); // 누적시간으로 금액 계산
        }
        
        return result;
    }
}