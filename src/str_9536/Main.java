package str_9536;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < T; i++) {
            HashMap<String, ArrayList<Integer>> map = new HashMap<>();
            String[] saying = br.readLine().split(" ");
            for (int c = 0; c < saying.length; c++) {
                if (!map.containsKey(saying[c])) map.put(saying[c], new ArrayList<>(List.of(c)));
                else map.get(saying[c]).add(c);
            }

            String str;
            while((str = br.readLine()) != null && !str.equals("what does the fox say?")) {
                String[] strArr = str.split(" ");
                String sound = strArr[strArr.length - 1];
                List<Integer> list = map.get(sound);
                for (Integer num : list) saying[num] = "";
            }

            for (String say : saying) {
                if (say != null && !say.equals("")) sb.append(say).append(" ");
            }
            sb.append("\n");
        }
        
        System.out.print(sb);
    }
}