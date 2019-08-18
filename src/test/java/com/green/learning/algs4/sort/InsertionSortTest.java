package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class InsertionSortTest
{
    @RepeatedTest(5)
    void testRndInts()
    {
        XTimer timer = new XTimer("Compare time efficiency among different insertion sort variants");
        Random rnd = new Random();
        final int N = 100_000;
        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        Integer[] B;
        
        B = Arrays.copyOf(A, N);
        timer.start("insertion sort");
        InsertionSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("optimized insertion sort");
        InsertionSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("binary insertion sort");
        BinaryInsertionSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
}