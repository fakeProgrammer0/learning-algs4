package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;

/**
 * a optimized version of 3-way quick sort
 *
 * @see edu.princeton.cs.algs4.QuickBentleyMcIlroy
 */
public class QuickSort3WayOpt
{
    private static final int INSERTION_CUTOFF = 7;
    
    private static final int MEDIAN_OF_3_CUTOFF = 40;
    
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        ArrayUtils.shuffle(A);
        sort(A, 0, A.length - 1);
    }
    
    private static <E extends Comparable<E>> void sort(E[] A, int low, int high)
    {
        if (low >= high) return;
        int len = high - low + 1;
        int mid = (low + high) / 2;
        if (len <= INSERTION_CUTOFF)
        {
            InsertionSort.sort(A, low, high);
            return;
        } else if (len <= MEDIAN_OF_3_CUTOFF)
        {
            int pivot = ArrayUtils.medianIndex(A, low, mid, high);
            ArrayUtils.swap(A, low, pivot);
        } else
        {
            // use Tukey ninther as partitioning element
            int eps = len / 8;
            int m1 = ArrayUtils.medianIndex(A, low, low + eps, low + eps + eps);
            int m2 = ArrayUtils.medianIndex(A, mid - eps, mid, mid + eps);
            int m3 = ArrayUtils.medianIndex(A, high - eps - eps, high - eps, high);
            int ninether = ArrayUtils.medianIndex(A, m1, m2, m3);
            ArrayUtils.swap(A, low, ninether);
        }
        
        E pivotVal = A[low];
        
        // use Bentley-McIlroy 3-way partitioning
        int i = low, j = high + 1;
        int p = low, q = high + 1;
        while (true)
        {
            while (A[++i].compareTo(pivotVal) < 0 && i != high) ;
            while (A[--j].compareTo(pivotVal) > 0 && j != low) ;
            if (i == j && A[i].compareTo(pivotVal) == 0)
                ArrayUtils.swap(A, i, ++p);
            if (i >= j) break;
            ArrayUtils.swap(A, i, j);
            if (A[i].compareTo(pivotVal) == 0) ArrayUtils.swap(A, i, ++p);
            if (A[j].compareTo(pivotVal) == 0) ArrayUtils.swap(A, j, --q);
        }
        
        i = j + 1;
        while (p >= low)
            ArrayUtils.swap(A, j--, p--);
        while (q <= high)
            ArrayUtils.swap(A, i++, q++);
        
        sort(A, low, j);
        sort(A, i, high);
    }
    
}
