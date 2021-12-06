import java.util.*;

class Day6Part2 {
    public static void main(String[] args) {
        List<Integer> input = new ArrayList<>(List.of(3,4,3,1,2));
        List<Integer> appendices = new ArrayList<>();
        int days = 256;
        while (days > 0) {
            int len = input.size();
            for (int i = 0; i < len; i++) {
                int age = input.get(i);
                if (age == 0) {
                    input.set(i, 6);
                    appendices.add(8);
                } else {
                    input.set(i, age - 1);
                }
                if (appendices.size() > 0) {
                    input.addAll(appendices);
                }
                appendices = new ArrayList<>();
            }
            days--;
        }
        System.out.println(input.size());
    }
}