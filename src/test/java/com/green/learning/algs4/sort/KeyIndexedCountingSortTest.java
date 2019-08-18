package com.green.learning.algs4.sort;

import com.green.learning.algs4.string.sort.KeyIndexedCountingSort;
import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class KeyIndexedCountingSortTest
{
    
    @Test
    void test1()
    {
        int R = Character.MAX_VALUE;
        System.out.println("The size of unicode alphabet: " + R);
        
        final int N = 100;
        Random rnd = new Random();
        char[] chars = new char[N];
        for(int i = 0; i < chars.length; i++)
            chars[i] = (char)rnd.nextInt(R);
        
        System.out.println("Before sorted: ");
        ArrayUtils.printChars(chars);
        
        KeyIndexedCountingSort.sort(chars);
    
        System.out.println("After sorted: ");
        ArrayUtils.printChars(chars);
        assertTrue(ArrayUtils.isSorted(chars));
    }
    
    @DisplayName("Compare key indexed counting sort with quick sort")
    @ParameterizedTest
    @ValueSource(ints = {1_000, 1000_000, 10_000_000, 100_000_000})
    void test2(int N)
    {
        int R = Character.MAX_VALUE;
        Random rnd = new Random();
        char[] chars = new char[N];
        for(int i = 0; i < chars.length; i++)
            chars[i] = (char)rnd.nextInt(R);
    
        XTimer timer = new XTimer("chars sorting");
        
        char[] A = Arrays.copyOf(chars, N);
        timer.start("key indexed counting sort");
        KeyIndexedCountingSort.sort(A);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(A));
    
        A = Arrays.copyOf(chars, N);
        timer.start("quick sort");
        Arrays.sort(A);
        timer.stop();
        assertTrue(ArrayUtils.isSorted(A));
        
        System.out.println(timer.toString());
    }
    
}
