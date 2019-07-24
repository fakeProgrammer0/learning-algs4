package com.green.learning_algs4.sort;

import com.green.learning_algs4.util.ArrayUtils;
import com.green.learning_algs4.util.XTimer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MSDStringSortTest
{
    @Test
    void test1()
    {
        String[] A = {
                "dab",
                "add",
                "cab",
                "fad",
                "fee",
                "bad",
                "dad",
                "bee",
                "fed",
                "bed",
                "1ebb",
                "1ace",
        };
        
        MSDStringSort.sort(A);
        assertTrue(ArrayUtils.isSorted(A));
        ArrayUtils.print(A, false);
    }
    
    @Test
    void test2()
    {
        String[] A = {
                "sea",
                "seashells",
                "sells",
                "she",
                "", // empty string
                "she",
                "shells",
                "shore",
                "surely",
        };
        
        MSDStringSort.sort(A);
        assertTrue(ArrayUtils.isSorted(A));
        ArrayUtils.print(A, false);
    }
    
    @RepeatedTest(1000)
    void test3()
    {
        final int R = Character.MAX_VALUE + 1;
        final int N = 16;
        final int MAX_LEN = 11; // exclusive
        String[] A = new String[N];
        
        for(int i = 0; i < N; i++)
        {
            StringBuilder sb = new StringBuilder();
            int len = (int)(Math.random() * MAX_LEN); // [0, MAX_LEN)
            for(int d = 0; d < len; d++)
            {
                sb.append((char) ('a' + Math.random() * ('z' - 'a' + 1)));
            }
            A[i] = sb.toString();
        }
        
//        ArrayUtils.print(A,false);
        MSDStringSort.sort(A);
        assertTrue(ArrayUtils.isSorted(A));
//        ArrayUtils.print(A,false);
    }
    
    @RepeatedTest(5)
    @DisplayName("Compare efficiency between MSD and merge sort")
    void test4()
    {
        final int R = Character.MAX_VALUE + 1;
        final int N = 100_000;
        final int MAX_LEN = 50; // exclusive
    
        XTimer timer = new XTimer("Compare efficiency between MSD and merge sort");
        
        timer.start("init random strings");
        String[] A = new String[N];
        for(int i = 0; i < N; i++)
        {
            StringBuilder sb = new StringBuilder();
            int len = (int)(Math.random() * MAX_LEN); // [0, MAX_LEN)
            for(int d = 0; d < len; d++)
            {
//                sb.append((char) (Math.random() * R));
                sb.append((char) ('a' + Math.random() * ('z' - 'a' + 1)));
            }
            A[i] = sb.toString();
        }
        timer.stop();
        
        String[] B = Arrays.copyOf(A, N);
        timer.start("MSD");
        MSDStringSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("algs4 MSD");
        edu.princeton.cs.algs4.MSD.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("merge sort");
        Arrays.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
}