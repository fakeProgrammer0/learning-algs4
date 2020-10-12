package com.green.learning.algs4.oj;

import com.green.learning.algs4.util.ArrayUtils;
import com.green.learning.algs4.util.XTimer;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MinTripleDistanceTest
{
    
    @Test
    void test1()
    {
        int[] A = {-1, 0, 9};
        int[] B = {-25, 10, 10, 11};
        int[] C = {2, 9, 17, 30, 41};
        final int minDist = 2;
        
        assertEquals(minDist, MinTripleDistance.solve(A, B, C));
        assertEquals(minDist, MinTripleDistance.bruteForce(A, B, C));
    }
    
    @RepeatedTest(10)
    void rndTest()
    {
        Random rnd = new Random();
        final int N = StdRandom.uniform(4, 1024);
        int n1 = StdRandom.uniform(3, N);
        int n2 = StdRandom.uniform(3, N);
        int n3 = StdRandom.uniform(3, N);
        
        final int L = -1024, U = 1024;
        
        int[] A = rnd.ints(n1, L, U).toArray();
        int[] B = rnd.ints(n2, L, U).toArray();
        int[] C = rnd.ints(n3, L, U).toArray();
        Arrays.sort(A);
        Arrays.sort(B);
        Arrays.sort(C);

//        ArrayUtils.print(A,false);
//        ArrayUtils.print(B,false);
//        ArrayUtils.print(C,false);
        
        assertEquals(
                MinTripleDistance.bruteForce(A, B, C),
                MinTripleDistance.solve(A, B, C)
        );
    }
    
    @Test
    void testTime()
    {
        Random rnd = new Random();
        final int T = 10;
        final int n1 = 1024;
        final int n2 = 1024;
        final int n3 = 1024;
        final int L = -65536, U = 65535;
        
        int[] A;
        int[] B;
        int[] C;
        
        XTimer timer1 = new XTimer("solve");
        XTimer timer2 = new XTimer("brute force");
        
        for (int t = 0; t < T; t++)
        {
            A = rnd.ints(n1, L, U).toArray();
            B = rnd.ints(n2, L, U).toArray();
            C = rnd.ints(n3, L, U).toArray();
            Arrays.sort(A);
            Arrays.sort(B);
            Arrays.sort(C);
            
            timer1.start();
            int d1 = MinTripleDistance.solve(A, B, C);
            timer1.stop();
            
            timer2.start();
            int d2 = MinTripleDistance.bruteForce(A, B, C);
            timer2.stop();
            
            assertEquals(d2, d1);
        }
        
        System.out.println("timer1 total ms: " + timer1.totalIntervalsMS());
        System.out.println("timer2 total ms: " + timer2.totalIntervalsMS());
        
        System.out.println();
        System.out.println(timer1.toString());
        System.out.println();
        System.out.println(timer2.toString());
    }
}