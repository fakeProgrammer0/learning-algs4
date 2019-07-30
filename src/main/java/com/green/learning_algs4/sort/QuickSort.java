package com.green.learning_algs4.sort;

import com.green.learning_algs4.util.ArrayUtils;

/**
 * standard quick sort
 * @see edu.princeton.cs.algs4.Quick
 * @see edu.princeton.cs.algs4.QuickX
 */
public class QuickSort
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        ArrayUtils.shuffle(A);
        sort(A, 0, A.length - 1);
    }
    
    private static <E extends Comparable<E>> void sort(E[] A, int low, int high)
    {
        if (high > low)
        {
            int pivot = partition(A, low, high);
            sort(A, low, pivot - 1);
            sort(A, pivot + 1, high);
        }
    }
    
    private static <E extends Comparable<E>> int partition(E[] A, int low, int high)
    {
        int i = low, j = high + 1;
        E pivotVal = A[low];
        do
        {
            // 对优先级的定义要一致，代码可读性才好一些
            while (j > low && pivotVal.compareTo(A[--j]) <= 0) ;
            
            while (i < j && A[++i].compareTo(pivotVal) <= 0) ;
            ArrayUtils.swap(A, i, j);
        } while (i < j);
        ArrayUtils.swap(A, i, low);
        
        return i;
    }
}
