package com.green.learning_algs4.string.sort;

import com.green.learning_algs4.sort.InsertionSort;
import com.green.learning_algs4.string.Alphabet;

/**
 * Modified Significant Digit First String Sort
 * Uses a given alphabet rather than the whole unicode character
 * @see MSDStringSort
 * @see edu.princeton.cs.algs4.MSD
 */
public class MSDStringSortBounded
{
    private final Alphabet alphabet;
    private final int R;
    private final String[] A;
    private final String[] aux;
    
    private static final int INSERTION_CUTOFF = 15;
    
    public MSDStringSortBounded(Alphabet alphabet, String[] A)
    {
        this.alphabet = alphabet;
        this.R = alphabet.radix();
        this.A = A;
        this.aux = new String[A.length];
        sort(0, 0, A.length - 1);
    }
    
    public static void sort(Alphabet alphabet, String[] A)
    {
        new MSDStringSortBounded(alphabet, A);
    }
    
    private void sort(int d, int low, int high)
    {
        if (high - low < INSERTION_CUTOFF)
        {
            InsertionSort.sortStrings(A, low, high, d);
            return;
        }
        
        // -1 strings are excluded, no need to check lengths
//        if (low >= high || d > A[low].length()) return;
        
        // leave another entry for -1 character in array counts
        // unicode: 0, -1, \u0000, \u0001, \u0002, ...
        final int[] counts = new int[R + 2];
        for (int i = low; i <= high; i++)
            counts[chatAt(A[i], d) + 2]++;
        
        counts[0] = low;
        for (int i = 1; i < counts.length; i++)
            counts[i] += counts[i - 1];
        
        for (int i = low; i <= high; i++)
            aux[counts[chatAt(A[i], d) + 1]++] = A[i];
        
        for (int i = low; i <= high; i++)
            A[i] = aux[i];
        
        // exclude -1 sentinel strings
        for (int i = 1; i < counts.length; i++)
            if (counts[i] != counts[i - 1]) // optimized: low_new > high_new
                sort(d + 1, counts[i - 1], counts[i] - 1);
    }
    
    private int chatAt(String str, int d)
    {
        if (d < str.length())
            return alphabet.toIndex(str.charAt(d));
        return -1;
    }
}
