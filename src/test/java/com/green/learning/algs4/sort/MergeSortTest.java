package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest
{
    @RepeatedTest(5)
    void testRndInts()
    {
        XTimer timer = new XTimer("Compare time efficiencies among mergesort variants");
        Random rnd = new Random();
        final int N = 10_000_000;
        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        Integer[] B;
        
        B = Arrays.copyOf(A, N);
        timer.start("mergesort with array creations overhead");
        MergeSortX.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("mergesort reusing the auxiliary array");
        MergeSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("optimized mergesort");
        MergeSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("bottom-up mergesort");
        MergeSortBU.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("mergesort in java SDK");
        Arrays.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
    
    @RepeatedTest(5)
    void testTimeEfficiency()
    {
        XTimer timer = new XTimer("Compare time efficiency between mergesort and quicksort");
        Random rnd = new Random();
        final int N = 10_000_000;
        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        
        Integer[] B = Arrays.copyOf(A, N);
        timer.start("mergesort");
        MergeSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("quicksort");
        QuickSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
}
