package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest
{
    @RepeatedTest(5)
    void testRndInts()
    {
        XTimer timer = new XTimer("Compare time efficiencies among quicksort variants");
        Random rnd = new Random();
        final int N = 10_000_000;
//        final int N = 1_000;
        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        Integer[] B;
        
        B = Arrays.copyOf(A, N);
        timer.start("standard quicksort");
        QuickSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("optimized 2 way quicksort");
        QuickSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("3 way quicksort");
        QuickSort3Way.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("optimized 3 way quicksort");
        QuickSort3WayOpt.sort(B);
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
    void testRndSmallInts()
    {
        XTimer timer = new XTimer("Compare time efficiencies among quicksort variants");
        Random rnd = new Random();
        final int N = 10_000_000;
        int L = 0, U = 65535;
        Integer[] A = rnd.ints(N, L, U).boxed().toArray(Integer[]::new);
        Integer[] B;
        
        B = Arrays.copyOf(A, N);
        timer.start("standard quicksort");
        QuickSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("optimized 2 way quicksort");
        QuickSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("3 way quicksort");
        QuickSort3Way.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("optimized 3 way quicksort");
        QuickSort3WayOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("mergesort in java SDK");
        Arrays.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
    
    @Test
    void debug3WayOpt1()
    {
        Integer[] A = Arrays.asList(79, 116, 54, 20, 40, 31, 121, 121, 7).toArray(new Integer[0]);
        
        QuickSort3WayOpt.sort(A);
        
        assertTrue(ArrayUtils.isSorted(A));
    }
    
    @Test
    void debug3WayOpt2()
    {
        int N = 16;
        Integer[] A = new Integer[N];
        Arrays.fill(A, 8);
        
        QuickSort3WayOpt.sort(A);
        
        assertTrue(ArrayUtils.isSorted(A));
    }
}