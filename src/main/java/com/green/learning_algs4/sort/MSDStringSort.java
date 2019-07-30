package com.green.learning_algs4.sort;

/**
 * Most Significant Digit First String Sort
 *
 * @see edu.princeton.cs.algs4.MSD
 * @see edu.princeton.cs.algs4.InplaceMSD
 */
public class MSDStringSort
{
    private final static int R = Character.MAX_VALUE + 1;
    
    public static void sort(String[] A)
    {
        String[] aux = new String[A.length];
        sort(A, aux, 0, 0, A.length - 1);
    }
    
    private static void sort(String[] A, String[] aux, int d, int low, int high)
    {
        // also handles empty strings
        if (low >= high) return;
    
        // -1 strings are excluded, no need to check lengths
//        if (low >= high || d > A[low].length()) return;
        
        // leave another entry for -1 character in array counts
        // unicode: 0, -1, \u0000, \u0001, \u0002, ...
        final int[] counts = new int[R + 2];
        for (int i = low; i <= high; i++)
            counts[chatAt(A[i], d) + 2]++;
        
        for (int i = 1; i < counts.length; i++)
            counts[i] += counts[i - 1];
        
        for (int i = low; i <= high; i++)
            aux[low + counts[chatAt(A[i], d) + 1]++] = A[i];
        
        for (int i = low; i <= high; i++)
            A[i] = aux[i];
    
        // exclude -1 sentinel strings
        for (int i = 1; i < counts.length; i++)
            if (counts[i] != counts[i - 1]) // optimized: low_new > high_new
                sort(A, aux, d + 1, low + counts[i - 1], low + counts[i] - 1);
    }
    
    private static int chatAt(String str, int d)
    {
        if (d < str.length())
            return str.charAt(d);
        return -1;
    }
}
