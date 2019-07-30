package com.green.learning_algs4.sort;

import com.green.learning_algs4.util.ArrayUtils;

/**
 * 3 way quick sort
 * @see edu.princeton.cs.algs4.Quick3way
 */
public class QuickSort3Way
{
    public static <E extends Comparable<E>> void sort(E[] a)
    {
        ArrayUtils.shuffle(a);
        sort(a, 0, a.length - 1);
    }
    
    private static <E extends Comparable<E>> void sort(E[] A, int low, int high)
    {
        if (low >= high) return;
    
        E val = A[low];
        int lt = low, gt = high;
        int i = low + 1; // i: iterator
        // use quick select to process A[low, high]:
        //      less than val: A[low, lt - 1]
        //       equal to val: A[lt, i - 1]
        //      to be process: A[i, gt]
        //   greater than val: A[gt + 1, high]
        while (i <= gt)
        {
            int cmp = A[i].compareTo(val);
            if (cmp < 0) ArrayUtils.swap(A, lt++, i++);
            else if (cmp > 0) ArrayUtils.swap(A, i, gt--);
            else i++;
        }
        // A[low, high] is partitioned by quick select into 3 parts :
        //      less than val: A[low, lt - 1]
        //       equal to val: A[lt, gt]
        //   greater than val: A[gt + 1, high]
        
        sort(A, low, lt - 1);
        sort(A, gt + 1, high);
    }
    
}
