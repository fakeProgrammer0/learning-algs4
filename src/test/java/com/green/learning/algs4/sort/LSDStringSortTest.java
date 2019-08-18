package com.green.learning.algs4.sort;

import com.green.learning.algs4.string.sort.LSDStringSort;
import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LSDStringSortTest
{
    
    @Test
    void testSort1()
    {
        String[] A = {"ABC", "abc", "a"};
        assertThrows(IllegalArgumentException.class, ()-> LSDStringSort.sort(A));
    }
    
    @Test
    void testSort2()
    {
        String[] A = {"XYZ", "ABC", "abc", "123", "0X6"};
        ArrayUtils.UnBufferedPrint(A);
        assertDoesNotThrow(()-> LSDStringSort.sort(A));
        assertTrue(ArrayUtils.isSorted(A));
        ArrayUtils.UnBufferedPrint(A);
    }
    
    @Test
    void testSort3()
    {
        XTimer timer = new XTimer("LSD sort timer");
        
        timer.start("init rnd data");
//        final int N = 1_000_000;
        final int N = 100_000_000;
//        final int N = 100;
        final int d = 5;
        String[] A = new String[N];
        
        char[] chars = new char[d];
        for(int i = 0; i < N; i++)
        {
            for (int j = 0; j < d; j++)
                chars[j] = (char)(Math.random() * ('z' - 'a') + 'a');
            A[i] = new String(chars);
        }
        timer.stop();
    
        timer.start("LSD sort");
//        LSDStringSort.sort(A);
        Arrays.sort(A);
        timer.stop();
        
        System.out.println(timer.toString());
        
        assertTrue(ArrayUtils.isSorted(A));
    }
}
