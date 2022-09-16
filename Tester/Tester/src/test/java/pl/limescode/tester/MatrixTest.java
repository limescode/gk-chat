package pl.limescode.tester;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.limescode.tester.utils.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MatrixTest {
    private Matrix matrix;

    @BeforeEach
    public void init() {
        matrix = new Matrix();
    }

    @Test
    public void testSplit1() {
        Integer[] arr = {12, 45, 3, 3, 5, 3, 3, 3};
        Integer[] arrCut = {3, 3, 3};
        Assertions.assertArrayEquals(arrCut, matrix.splitArrayByNumber(arr, 5));
    }

    @Test
    public void testSplit2() {
        Integer[] arr = {2, 12, 1, 7, 3, 4, 5, 4, 7, 8};
        Integer[] arrCut = {7, 8};
        Assertions.assertArrayEquals(arrCut, matrix.splitArrayByNumber(arr, 4));
    }

    @Test
    public void testSplit3() {
        Integer[] arr = {1, 7, 3, 4, 5, 4, 7, 8, 15, 4};
        Integer[] arrCut = {};
        Assertions.assertArrayEquals(arrCut, matrix.splitArrayByNumber(arr, 4));
    }

    @Test
    public void testSplit4() {
        Integer[] arr = {1, 7, 3, 4, 5, 4, 7, 8};
        Assertions.assertThrows(RuntimeException.class, () -> matrix.splitArrayByNumber(arr, 2));
    }

    @ParameterizedTest
    @MethodSource("dataForArraySearch")
    public void testSearch(Integer[] arr, boolean result){
        Assertions.assertEquals(result, matrix.findOneAndFour(arr));
    }

    public static Stream<Arguments> dataForArraySearch() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new Integer[] { 1, 4 }, true));
        out.add(Arguments.arguments(new Integer[] { 1, 1, 4 }, true));
        out.add(Arguments.arguments(new Integer[] { 2, 2, 2 }, false));
        out.add(Arguments.arguments(new Integer[] { 1, 2, 3 }, false));
        out.add(Arguments.arguments(new Integer[] { 10, 10, 4 }, false));
        return out.stream();
    }

}
