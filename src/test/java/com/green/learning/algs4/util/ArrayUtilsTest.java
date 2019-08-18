package com.green.learning.algs4.util;

import com.green.learning.algs4.sort.*;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtilsTest
{
    @RepeatedTest(5)
    void testShuffle()
    {
        final int N = 16;
        final int L = 0, U = 127;
        Random rnd = new Random();
        Integer[] A = rnd.ints(N, L, U).boxed().toArray(Integer[]::new);
        Integer[] B = Arrays.copyOf(A, N);
        ArrayUtils.shuffle(B);
        ArrayUtils.UnBufferedPrint(A);
        ArrayUtils.UnBufferedPrint(B);
        InsertionSortOpt.sort(B);
        InsertionSortOpt.sort(A);
        assertArrayEquals(A, B);
    }
    
    @Test
    void testPrintArray()
    {
        final int N = 16;
        final int elementsPerLine = 8;
        Integer[] A = new Integer[N];
        
        for (int i = 0; i < N; i++)
            A[i] = i;
        
        A[StdRandom.uniform(N)] = null;
        A[StdRandom.uniform(N)] = null;
        
        System.out.println("print array without buffer");
        ArrayUtils.UnBufferedPrint(A);
        
        System.out.println("\nprint array with buffer");
        ArrayUtils.bufferedPrint(A);
        
        System.out.println("\nelements per line: " + elementsPerLine);
        ArrayUtils.print(A, false, elementsPerLine);
        
        System.out.println("\nelements per line: " + elementsPerLine);
        ArrayUtils.print(A, true, elementsPerLine);
    }
    
    
    
    
}
