package com.green.learning.algs4.sort;

import com.green.learning.algs4.string.sort.DistributionCountingSort;
import com.green.learning.algs4.string.sort.DistributionCountingSortX;
import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DistributionCountingSortTest
{
    @ParameterizedTest
    @DisplayName("Compare mergesort & distributed counting sort")
    @ValueSource(ints = {1000_000, 10_000_000})
    void test1(int N)
    {
        final int L = 0, U = 1000_000;
        final int epoches = 5;
        Random rnd = new Random();
    
        XTimer timer = new XTimer("Compare mergesort & distributed counting sort");
        
        int distriSortBeat = 0, mergeSortBeat = 0, draw = 0;
        for (int i = 0; i < epoches; i++)
        {
            Integer[] A = rnd.ints(N, L, U).boxed().toArray(Integer[]::new);
            Integer[] B;
        
            B = Arrays.copyOf(A, A.length);
            timer.start("distributed counting sort");
            DistributionCountingSort.sort(B);
            timer.stop();
            assertTrue(ArrayUtils.isSorted(B));
            final long distriConsume = timer.lastIntervalMs();
        
            B = Arrays.copyOf(A, A.length);
            timer.start("merge sort");
            Arrays.sort(B);
            timer.stop();
            assertTrue(ArrayUtils.isSorted(B));
            final long mergeConsume = timer.lastIntervalMs();
        
            System.out.println("At iteration " + i);
            if (distriConsume < mergeConsume)
            {
                distriSortBeat++;
                System.out.println("distributed sort beats!");
            } else if (distriConsume > mergeConsume)
            {
                mergeSortBeat++;
                System.out.println("merge sort beats!");
            } else
            {
                draw++;
                System.out.println("draw");
            }
        }
    
        System.out.println("\ntotal: " + (distriSortBeat + mergeSortBeat + draw));
        System.out.println("distributed sort beats " + distriSortBeat + " times");
        System.out.println("merge sort beats " + mergeSortBeat + " times\n");
        
        System.out.println(timer);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {10_000, 100_000, 1000_000, 10_000_000})
    @DisplayName("Compare mergesort & distributed counting sort (Unbound Range)")
    void test2(int N)
    {
        Random rnd = new Random();
        
        XTimer timer = new XTimer("Compare mergesort & distributed counting sort");

        Integer[] A = rnd.ints(N).boxed().toArray(Integer[]::new);
        Integer[] B;
        
        B = Arrays.copyOf(A, A.length);
        timer.start("distributed counting sort");
        DistributionCountingSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, A.length);
        timer.start("merge sort");
        Arrays.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
    
    @Test
    @DisplayName("Large Int Range")
    void test3()
    {
        Integer[] A = {Integer.MAX_VALUE, 1, Integer.MIN_VALUE, 0};
        DistributionCountingSort.sort(A);
        assertTrue(ArrayUtils.isSorted(A));
        ArrayUtils.print(A,false);
    }
    
    @Test
    @DisplayName("Large Int Range")
    void test4()
    {
        Integer[] A = {Integer.MAX_VALUE, 1, Integer.MIN_VALUE, 0};
        assertThrows(IllegalArgumentException.class, ()-> DistributionCountingSortX.sort(A));
    }
    
    @Test
    void checkMemory()
    {
        Integer[] ints = new Integer[1 << 28];
        System.out.printf("free memory: %.2fMB\n", Runtime.getRuntime().freeMemory() * 1.0 / 1024 / 1024);
        System.out.printf("max JVM memory: %.2fMB\n", Runtime.getRuntime().maxMemory() * 1.0 / 1024 / 1024);
        System.out.printf("total JVM memory: %.2fMB\n", Runtime.getRuntime().totalMemory() * 1.0 / 1024 / 1024);
        System.out.println(ints[1]);
    }
}