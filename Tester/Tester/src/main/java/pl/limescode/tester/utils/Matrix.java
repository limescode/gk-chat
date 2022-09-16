package pl.limescode.tester.utils;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Matrix {

    public Integer[] splitArrayByNumber(Integer[] arr, Integer lookingNumber) {
        int lastPosition = -1;
        boolean found = false;
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i].compareTo(lookingNumber) == 0) {
                found = true;
                lastPosition = i;
                break;
            }
        }
        if (found) {
            Integer[] subArr = IntStream.range(lastPosition+1, arr.length)
                    .mapToObj(i -> arr[i])
                    .toArray(Integer[]::new);
            return subArr;
        } else
            throw new RuntimeException("Looking number not found");
    }

    public boolean findOneAndFour(Integer[] arr){
        return Arrays.asList(arr).contains(1) && Arrays.asList(arr).contains(4);
    }
}
