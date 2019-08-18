package com.green.learning.algs4.string.sort;

import java.util.Arrays;

/**
 * Least Significant Digit First Sorting for Strings
 * @see edu.princeton.cs.algs4.LSD
 */
public class LSDStringSort
{
    private static final int R = Character.MAX_VALUE + 1;
    
    private static void checkEqualLengths(String[] A)
    {
        int w = A[0].length();
        for (int i = 1; i < A.length; i++)
            if (A[i].length() != w)
                throw new IllegalArgumentException("All strings must have the same length!");
    }
    
    public static void sort(String[] A)
    {
        if (A == null) throw new IllegalArgumentException();
        if (A.length == 0) return;
        checkEqualLengths(A);
        
        final int w = A[0].length();
        final int N = A.length;
        int[] counts = new int[R + 1];
        String[] aux = new String[N];
        for (int d = w - 1; d >= 0; d--)
        {
            Arrays.fill(counts, 0);
            for (int i = 0; i < N; i++)
                counts[A[i].charAt(d) + 1]++;
            
            for (int i = 0; i < R; i++)
                counts[i + 1] += counts[i];
            
            for (int i = 0; i < N; i++)
                aux[counts[A[i].charAt(d)]++] = A[i];
            
            for (int i = 0; i < N; i++)
                A[i] = aux[i];
        }
    }
}
