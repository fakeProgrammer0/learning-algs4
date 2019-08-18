package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShellSortTest
{
    @RepeatedTest(10)
    void testRndInts()
    {
        XTimer timer = new XTimer("");
        Random rnd = new Random();
        final int N = 10_000_000;
        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        
        Integer[] B = Arrays.copyOf(A, N);
        timer.start("ShellSort.sort");
        ShellSort.sort(B);
        timer.stop();
    
        B = Arrays.copyOf(A, N);
        timer.start("ShellSort.sort2");
        ShellSort.sort2(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("ShellSortX.sort");
        ShellSortX.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("ShellSortX.sort2");
        ShellSortX.sort2(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
    
    // 简直被完虐。。
    @RepeatedTest(5)
    void testTimeEfficiency()
    {
        XTimer timer = new XTimer("Compare time efficiency between shellsort and quicksort");
        Random rnd = new Random();
        final int N = 10_000_000;
        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        
        Integer[] B = Arrays.copyOf(A, N);
        timer.start("shellsort");
        ShellSort.sort(B);
        timer.stop();
        
        B = Arrays.copyOf(A, N);
        timer.start("quicksort");
        QuickSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
}