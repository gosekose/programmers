package pro_77486;

import java.util.*;

class Person {
    String name;
    Person parent;
    int profit;
    
    public Person(String name, Person parent, int profit) {
        this.name = name;
        this.parent = parent;
        this.profit = profit;
    }
    
    public void calcProfit(int profit) {
        int profitToParent = profit / 10;
        this.profit += profit - profitToParent;
        if (this.parent != null && profitToParent >= 1) {
            this.parent.calcProfit(profitToParent);
        } 
    }
}


class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        HashMap<String, Person> persons = new HashMap<>(); 
        
        for (String name : enroll) {
            persons.put(name, new Person(name, null, 0));
        }
        
        for (int i = 0; i < enroll.length; i++) {
            if (referral.equals("-")) continue;
            persons.get(enroll[i]).parent = persons.get(referral[i]);
        }
        
        for (int i = 0; i < seller.length; i++) 
            persons.get(seller[i]).calcProfit(amount[i] * 100);
        
        
        int[] result = new int[enroll.length];
        
        for (int i = 0; i < result.length; i++)
            result[i] = persons.get(enroll[i]).profit;
        
        return result;
    }
}