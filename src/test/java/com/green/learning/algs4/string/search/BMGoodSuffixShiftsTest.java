package com.green.learning.algs4.string.search;

import com.green.learning.algs4.string.Alphabet;
import com.green.learning.algs4.util.XTimer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class BMGoodSuffixShiftsTest
{
    @Test
    void test1()
    {
//        String pattern = "abbabab";
        String pattern = "BBBABBB";
        int[] shifts1 = BMGoodSuffixShifts.border(pattern);
        int[] shifts2 = BMGoodSuffixShifts.bruteForce(pattern);
        int[] shifts3 = BMGoodSuffixShifts.wiki(pattern);
        assertArrayEquals(shifts1, shifts2);
        assertArrayEquals(shifts1, shifts3);
    }
    
    @Test
    void test2()
    {
        String pattern = "BAOBAB";
        int[] shifts = BMGoodSuffixShifts.bruteForce(pattern);
        assertEquals(2, shifts[4]);
        assertEquals(5, shifts[3]);
        assertEquals(5, shifts[2]);
        assertEquals(5, shifts[1]);
        assertEquals(5, shifts[0]);
        assertArrayEquals(shifts, BMGoodSuffixShifts.border(pattern));
        assertArrayEquals(shifts, BMGoodSuffixShifts.wiki(pattern));
    }
    
    @Test
    void test3()
    {
        String pattern = "ABCBAB";
        int[] shifts = BMGoodSuffixShifts.bruteForce(pattern);
        assertEquals(2, shifts[4]);
        assertEquals(4, shifts[3]);
        assertEquals(4, shifts[2]);
        assertEquals(4, shifts[1]);
        assertEquals(4, shifts[0]);
        assertArrayEquals(shifts, BMGoodSuffixShifts.border(pattern));
        assertArrayEquals(shifts, BMGoodSuffixShifts.wiki(pattern));
    }
    
    @Test
    void testTime1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
//        Alphabet alphabet = new Alphabet("A");
//        Alphabet alphabet = new Alphabet("AB");
        Alphabet alphabet = new Alphabet("ABC");
    
        int[] lengths = {1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000};
        
        final int T = 10;
        
        final int METHOD_COUNT = 3;
        XTimer[] timers = new XTimer[METHOD_COUNT];
        timers[0] = new XTimer("Border");
        timers[1] = new XTimer("Wiki");
        timers[2] = new XTimer("Brute Force");
        
        Method[] methods = new Method[METHOD_COUNT];
        methods[0] = BMGoodSuffixShifts.class.getDeclaredMethod("border", String.class);
        methods[1] = BMGoodSuffixShifts.class.getDeclaredMethod("wiki", String.class);
        methods[2] = BMGoodSuffixShifts.class.getDeclaredMethod("bruteForce", String.class);
        
        for (int length: lengths)
        {
            for(int i = 0; i < METHOD_COUNT; i++)
                timers[i].clear();
            
            for(int t = 0; t < T; t++)
            {
                String pattern = alphabet.rndString(length);
//            String pattern = alphabet.rndString(MIN_LENGTH, MAX_LENGTH);
//            System.out.printf("%d) %s\n", t, pattern);
    
                int[][] shifts = new int[3][];
                for (int i = 0; i < METHOD_COUNT; i++)
                {
//                timers[i].start(pattern);
                    timers[i].start();
                    shifts[i] = (int[]) methods[i].invoke(null, pattern);
                    timers[i].stop();
                }
                assertArrayEquals(shifts[0], shifts[1]);
                assertArrayEquals(shifts[0], shifts[2]);
            }
            
            System.out.printf("length = %d%n", length);
            for(int i = 0; i < METHOD_COUNT; i++)
                System.out.printf("%s ; average %.2fms\n", timers[i].getName(), timers[i].avgIntervalMS());
            System.out.println();
        }
    }
    
    @Test
    void testTime4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
//        Alphabet alphabet = new Alphabet("A");
//        Alphabet alphabet = new Alphabet("AB");
        Alphabet alphabet = new Alphabet("ABC");
        
        int[] lengths = {1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000,
                1_000_000, 2_000_000, 4_000_000, 8_000_000, 16_000_000,
                100_000_000, 200_000_000, 400_000_000, 800_000_000, 1600_000_000,
        };
        
        final int T = 10;
        
        final int METHOD_COUNT = 2;
        XTimer[] timers = new XTimer[METHOD_COUNT];
        timers[0] = new XTimer("Border");
        timers[1] = new XTimer("Wiki");
        
        Method[] methods = new Method[METHOD_COUNT];
        methods[0] = BMGoodSuffixShifts.class.getDeclaredMethod("border", String.class);
        methods[1] = BMGoodSuffixShifts.class.getDeclaredMethod("wiki", String.class);
        
        for (int length: lengths)
        {
            for(int i = 0; i < METHOD_COUNT; i++)
                timers[i].clear();
            
            for(int t = 0; t < T; t++)
            {
                String pattern = alphabet.rndString(length);
//            String pattern = alphabet.rndString(MIN_LENGTH, MAX_LENGTH);
//            System.out.printf("%d) %s\n", t, pattern);
                
                int[][] shifts = new int[3][];
                for (int i = 0; i < METHOD_COUNT; i++)
                {
//                timers[i].start(pattern);
                    timers[i].start();
                    shifts[i] = (int[]) methods[i].invoke(null, pattern);
                    timers[i].stop();
                }
                assertArrayEquals(shifts[0], shifts[1]);
            }
            
            System.out.printf("length = %d%n", length);
            for(int i = 0; i < METHOD_COUNT; i++)
                System.out.printf("%s ; average %.2fms\n", timers[i].getName(), timers[i].avgIntervalMS());
            System.out.println();
        }
    }
    
    @Test
    @DisplayName("worst case time")
    void testTime2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        int[] lengths = {1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000};
        StringBuilder sb = new StringBuilder();
        char c = 'A';
        System.out.println("alphabet = {A}");
    
        final int METHOD_COUNT = 3;
        XTimer[] timers = new XTimer[METHOD_COUNT];
        timers[0] = new XTimer("Border");
        timers[1] = new XTimer("Wiki");
        timers[2] = new XTimer("Brute Force");
        
        Method[] methods = new Method[METHOD_COUNT];
        methods[0] = BMGoodSuffixShifts.class.getDeclaredMethod("border", String.class);
        methods[1] = BMGoodSuffixShifts.class.getDeclaredMethod("wiki", String.class);
        methods[2] = BMGoodSuffixShifts.class.getDeclaredMethod("bruteForce", String.class);
    
        int x = 0;
        for (int n: lengths)
        {
            for(; x < n; x++)
                sb.append(c);
            String pattern = sb.toString();
            
            int[][] shifts = new int[3][];
            for (int i = 0; i < METHOD_COUNT; i++)
            {
//                timers[i].start(pattern);
                timers[i].start();
                shifts[i] = (int[]) methods[i].invoke(null, pattern);
                timers[i].stop();
            }
            assertArrayEquals(shifts[0], shifts[1]);
            assertArrayEquals(shifts[0], shifts[2]);
    
            System.out.printf("length = %d%n", n);
            for(int i = 0; i < METHOD_COUNT; i++)
                System.out.printf("%s %dms\n", timers[i].getName(), timers[i].totalIntervalsMS());
            System.out.println();
        }
    }
    
    @Test
    @DisplayName("worst case time")
    void testTime3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        int[] lengths = {1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000};
        StringBuilder sb = new StringBuilder();
        char c = 'A';
        System.out.println("alphabet = {A}");
        
        final int METHOD_COUNT = 2;
        XTimer[] timers = new XTimer[METHOD_COUNT];
        timers[0] = new XTimer("Border");
        timers[1] = new XTimer("Wiki");
//        timers[2] = new XTimer("Brute Force");
        
        Method[] methods = new Method[METHOD_COUNT];
        methods[0] = BMGoodSuffixShifts.class.getDeclaredMethod("border", String.class);
        methods[1] = BMGoodSuffixShifts.class.getDeclaredMethod("wiki", String.class);
//        methods[2] = BMGoodSuffixShifts.class.getDeclaredMethod("bruteForce", String.class);
        
        int x = 0;
        for (int n: lengths)
        {
            for(; x < n; x++)
                sb.append(c);
            String pattern = sb.toString();
            
            int[][] shifts = new int[3][];
            for (int i = 0; i < METHOD_COUNT; i++)
            {
//                timers[i].start(pattern);
                timers[i].start();
                shifts[i] = (int[]) methods[i].invoke(null, pattern);
                timers[i].stop();
            }
            assertArrayEquals(shifts[0], shifts[1]);
//            assertArrayEquals(shifts[0], shifts[2]);
            
            System.out.printf("length = %d%n", n);
            for(int i = 0; i < METHOD_COUNT; i++)
                System.out.printf("%s %dms\n", timers[i].getName(), timers[i].totalIntervalsMS());
            System.out.println();
        }
    }
}