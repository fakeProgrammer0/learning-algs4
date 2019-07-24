package com.green.learning_algs4.sort;

import com.green.learning_algs4.util.ArrayUtils;

/**
 * a optimized version of quicksort
 * tip1: cutoff to insertion sort
 * tip2: using median-of-3 to choose the partitioning element
 *
 * @see edu.princeton.cs.algs4.QuickX
 */
public class QuickSortOpt
{
    private static final int INSERTION_CUT_OFF = 7;
    
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        ArrayUtils.shuffle(A);
        sort(A, 0, A.length - 1);
    }
    
    private static <E extends Comparable<E>> void sort(E[] A, int low, int high)
    {
        if (low >= high) return;
        
        if (high - low + 1 <= INSERTION_CUT_OFF)
        {
            InsertionSort.sort(A, low, high);
            return;
        }
        
        int pivot = partition(A, low, high);
        sort(A, low, pivot - 1);
        sort(A, pivot + 1, high);
    }
    
    private static <E extends Comparable<E>> int partition(E[] A, int low, int high)
    {
        // 三元取中法
        int pivot = ArrayUtils.medianIndex(A, low, (low + high) / 2, high);
        ArrayUtils.swap(A, pivot, low);
        
        int i = low, j = high + 1;
        E pivotVal = A[low];
        
        // 写法跟 edu.princeton.cs.algs4.QuickX 不一样
        // pivotVal is one of the largest elements
        while (A[++i].compareTo(pivotVal) <= 0)
            if (i == high)
            {
                // all elements are equal
                ArrayUtils.swap(A, i, low);
                return high;
            }
        
        // pivotVal is one of the smallest elements
        while (pivotVal.compareTo(A[--j]) <= 0)
            if (j == low)
                return low;  // all elements are equal
        
        while (i < j)
        {
            ArrayUtils.swap(A, i, j);
            // no need to check if i < high because there
            // exists some elements greater than pivotVal
            while (A[++i].compareTo(pivotVal) <= 0) ;
            
            // need not to check if j < low because there
            // exists some elements smaller than pivotVal
            while (pivotVal.compareTo(A[--j]) <= 0) ;
        }
        ArrayUtils.swap(A, j, low);
        
        return j;
    }
}
