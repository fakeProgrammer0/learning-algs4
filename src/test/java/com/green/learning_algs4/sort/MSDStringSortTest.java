package com.green.learning_algs4.sort;

import com.green.learning_algs4.string.Alphabet;
import com.green.learning_algs4.util.ArrayUtils;
import com.green.learning_algs4.util.XTimer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

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
//        String[] A = {
//                "sea",
//                "seashells",
//                "sells",
//                "she",
//                "", // empty string
//                "she",
//                "shells",
//                "shore",
//                "surely",
//        };
        
        String[] A = {
                "nnawe",
                "cfgf",
                "sxvoy",
                "",
                "pb",
                "ysvldqvmt",
                "qevmgofise",
                "xy",
                "tdopf",
                "xof",
                "cvf",
                "kpyd",
                "",
                "n",
                "wkmsvxd",
                "d",
                "x"
        };
        
//        MSDStringSort.sort(A);
//        MSDStringSortOpt.sort(A);
//        QuickSort3WayString.sort(A);
//        InplaceMSD.sort(A);
        MSDRadixSortInplace.sort(A);
        
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
        
        ArrayUtils.print(A,false);
        
//        MSDStringSort.sort(A);
//        MSDStringSortOpt.sort(A);
//        QuickSort3WayString.sort(A);
        MSDStringSortBounded.sort(Alphabet.LOWERCASE(), A);
        
        assertTrue(ArrayUtils.isSorted(A));
        ArrayUtils.print(A,false);
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
        timer.start("MSD Opt");
        MSDStringSortOpt.sort(B);
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
    
    @RepeatedTest(5)
    @DisplayName("Compare efficiencies")
    void test5()
    {
        final int R = Character.MAX_VALUE + 1;
//        final int N = 1_000_000;
//        final int N = 100_000;
        final int N = 10_000;
        final int MAX_LEN = 50; // exclusive
//        final int MAX_LEN = 500; // exclusive
        
        XTimer timer = new XTimer("Compare efficiency between MSD and merge sort");
        
        timer.start("init random strings");
        String[] A = new String[N];
        for(int i = 0; i < N; i++)
        {
            StringBuilder sb = new StringBuilder();
            int len = (int)(Math.random() * MAX_LEN); // [0, MAX_LEN)
            for(int d = 0; d < len; d++)
                sb.append((char) (Math.random() * R));
            A[i] = sb.toString();
        }
        timer.stop();
        
        String[] B = Arrays.copyOf(A, N);
        timer.start("MSD");
        MSDStringSort.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("inplace MSD");
        MSDRadixSortInplace.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("MSD Opt");
        MSDStringSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("merge sort");
        Arrays.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
        
        System.out.println(timer);
    }
    
    @RepeatedTest(5)
    @DisplayName("Compare efficiencies using different alphabets")
    void test6(TestInfo testInfo)
    {
//        Alphabet alphabet = Alphabet.LOWERCASE();
//        Alphabet alphabet = Alphabet.UPPERCASE();
        Alphabet alphabet = Alphabet.EXTENDED_ASCII();
        
        final int R = alphabet.radix();
        
        // debug
//        final int N = 16;
//        final int MAX_LEN = 5; // exclusive
        
        final int N = 1_000_000;
        final int MAX_LEN = 500; // exclusive
        
        XTimer timer = new XTimer(testInfo.getDisplayName());
        
        timer.start("init random strings");
        String[] A = new String[N];
        for(int i = 0; i < N; i++)
        {
            int len = (int)(Math.random() * MAX_LEN); // [0, MAX_LEN)
            A[i] = new String(alphabet.samples(len));
        }
        timer.stop();
        
//        ArrayUtils.print(A,false);
        
        String[] B = Arrays.copyOf(A, N);
        
        // very very slow
//        timer.start("MSD");
//        MSDStringSort.sort(B);
//        timer.stop();
//        assertTrue(ArrayUtils.isSorted(B));
        
        B = Arrays.copyOf(A, N);
        timer.start("MSD Opt");
        MSDStringSortOpt.sort(B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("Restricted MSD");
        MSDStringSortBounded.sort(alphabet, B);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(B));
    
        B = Arrays.copyOf(A, N);
        timer.start("3 way quick sort string");
        QuickSort3WayString.sort(B);
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