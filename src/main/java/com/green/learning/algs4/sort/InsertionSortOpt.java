package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;

/**
 * Insertion Sort with a little optimization
 * @see edu.princeton.cs.algs4.InsertionX
 */
public class InsertionSortOpt
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        int exchanges = 0;
        // push the smallest element to the head of the array
        for (int i = A.length - 1; i > 0; i--)
            if (A[i].compareTo(A[i - 1]) < 0)
            {
                ArrayUtils.swap(A, i, i - 1);
                exchanges++;
            }
        if(exchanges == 0) return;
        
        for (int i = 1; i < A.length; i++)
        {
            E val = A[i];
            int j = i;
            // need not to check whether j - 1 > 0
            // since no element will be inserted before the smallest head element
            for (; A[j - 1].compareTo(val) > 0; j--)
                A[j] = A[j - 1]; // half-exchange
            A[j] = val;
        }
    }
}
