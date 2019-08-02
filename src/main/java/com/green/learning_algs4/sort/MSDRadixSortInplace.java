package com.green.learning_algs4.sort;

import com.green.learning_algs4.util.ArrayUtils;

/**
 * Most Significant Digit First String Sort Inplace Version
 * @see edu.princeton.cs.algs4.InplaceMSD
 */
public class MSDRadixSortInplace
{
    private final static int R = Character.MAX_VALUE + 1;
    
    private static final int INSERTION_CUTOFF = 15;
    
    public static void sort(String[] A)
    {
        sort(A, 0, 0, A.length - 1);
    }
    
    private static void sort(String[] A, int d, int low, int high)
    {
        // for debug
        if(low >= high) return;
        
        // cut off to insertion sort
//        if (high - low < INSERTION_CUTOFF)
//        {
//            InsertionSort.sortStrings(A, low, high, d);
//            return;
//        }
        
        // leave another entry for -1 character in array starts
        // unicode: 0, -1, \u0000, \u0001, \u0002, ...
        final int[] starts = new int[R + 2];
        final int[] ends = new int[R + 1];
        
        // 1.compute frequency starts
        for (int i = low; i <= high; i++)
            starts[chatAt(A[i], d) + 2]++;
        
        // 2.transform starts to indicies
        starts[0] = low;
        for (int r = 0; r < starts.length - 1; r++)
        {
            starts[r + 1] += starts[r];
            ends[r] = starts[r + 1];
        }
        
        for (int r = 0; r < starts.length - 1; r++)
            // (ends[r] - starts[r]) elements with key r are not sorted yet
            while (starts[r] != ends[r])
            {
                // A[start[r]] : the current element at start[r]
                int c = chatAt(A[starts[r]], d);
                while (c + 1 != r)
                {
                    // swap A[start[r]] to its proper place start[c+1]
                    // an element requires at most 2 swap to reach it proper place
                    ArrayUtils.swap(A, starts[r], starts[c + 1]++);
                    c = chatAt(A[starts[r]], d);
                }
                starts[r]++; // A[starts[r]] is in the proper place
            }
        
        // exclude -1 sentinel strings
        for (int i = 1; i < starts.length; i++)
            if (starts[i] > starts[i - 1]) // optimized: low_new > high_new
                sort(A, d + 1, starts[i - 1], starts[i] - 1);
    }
    
    private static int chatAt(String str, int d)
    {
        // assert d >= 0 && d <= str.length();
        if (d < str.length())
            return str.charAt(d);
        return -1;
    }
}

