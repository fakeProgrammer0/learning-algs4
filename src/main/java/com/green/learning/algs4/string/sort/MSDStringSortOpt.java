package com.green.learning.algs4.string.sort;

import com.green.learning.algs4.sort.InsertionSort;

/**
 * Most Significant Digit First String Sort Optimized Version
 *
 * @see edu.princeton.cs.algs4.MSD
 */
public class MSDStringSortOpt
{
    private final static int R = Character.MAX_VALUE + 1;
    
//    private static final int INSERTION_CUTOFF = 7;
    private static final int INSERTION_CUTOFF = 15;
    
    public static void sort(String[] A)
    {
        String[] aux = new String[A.length];
        sort(A, aux, 0, 0, A.length - 1);
    }
    
    private static void sort(String[] A, String[] aux, int d, int low, int high)
    {
        // cut off to insertion sort
        if (high - low < INSERTION_CUTOFF)
        {
            InsertionSort.sortStrings(A, low, high, d);
            return;
        }
        
        // leave another entry for -1 character in array counts
        // unicode: 0, -1, \u0000, \u0001, \u0002, ...
        final int[] counts = new int[R + 2];
    
        // 1.compute frequency counts
        for (int i = low; i <= high; i++)
            counts[chatAt(A[i], d) + 2]++;
    
        // 2.transform counts to indicies
        counts[0] = low;
        for (int i = 1; i < counts.length; i++)
            counts[i] += counts[i - 1];
    
        // 3.distribute
        for (int i = low; i <= high; i++)
            aux[counts[chatAt(A[i], d) + 1]++] = A[i];
    
        // 4.copy back
        for (int i = low; i <= high; i++)
            A[i] = aux[i];
        
        // exclude -1 sentinel strings
        for (int i = 1; i < counts.length; i++)
            if (counts[i] > counts[i - 1]) // optimized: low_new > high_new
                sort(A, aux, d + 1, counts[i - 1], counts[i] - 1);
    }
    
    private static int chatAt(String str, int d)
    {
        // assert d >= 0 && d <= str.length();
        if (d < str.length())
            return str.charAt(d);
        return -1;
    }
}
