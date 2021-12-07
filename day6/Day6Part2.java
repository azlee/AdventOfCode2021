import java.util.ArrayList;
import java.util.List;

class Day6Part2 {
    public static void main(String[] args) {
        List<Integer> input = new ArrayList<>(List.of(5,1,5,3,2,2,3,1,1,4,2,4,1,2,1,4,1,1,5,3,5,1,5,3,1,2,4,4,1,1,3,1,1,3,1,1,5,1,5,4,5,4,5,1,3,2,4,3,5,3,5,4,3,1,4,3,1,1,1,4,5,1,1,1,2,1,2,1,1,4,1,4,1,1,3,3,2,2,4,2,1,1,5,3,1,3,1,1,4,3,3,3,1,5,2,3,1,3,1,5,2,2,1,2,1,1,1,3,4,1,1,1,5,4,1,1,1,4,4,2,1,5,4,3,1,2,5,1,1,1,1,2,1,5,5,1,1,1,1,3,1,4,1,3,1,5,1,1,1,5,5,1,4,5,4,5,4,3,3,1,3,1,1,5,5,5,5,1,2,5,4,1,1,1,2,2,1,3,1,1,2,4,2,2,2,1,1,2,2,1,5,2,1,1,2,1,3,1,3,2,2,4,3,1,2,4,5,2,1,4,5,4,2,1,1,1,5,4,1,1,4,1,4,3,1,2,5,2,4,1,1,5,1,5,4,1,1,4,1,1,5,5,1,5,4,2,5,2,5,4,1,1,4,1,2,4,1,2,2,2,1,1,1,5,5,1,2,5,1,3,4,1,1,1,1,5,3,4,1,1,2,1,1,3,5,5,2,3,5,1,1,1,5,4,3,4,2,2,1,3));
        long[] ages = new long[9];
        long[] ages2 = new long[9];
        for (int age : input) {
            ages[age]++;
        }
        int days = 256;
        while (days > 0) {
            for (int i = 0; i < ages.length; i++) {
                ages2[i] = ages[(i + 1) % 9];
            }
            ages2[6] += ages[0];
            long[] temp = ages;
            ages = ages2;
            ages2 = temp;
            days--;
        }
        
        long numFish = 0;
        for (int i = 0; i < ages.length; i++) {
            numFish += ages[i];
        }
        System.out.println("numFish is " + numFish);
    }
}