package com.green.learning.algs4.sort.partition;

import com.green.learning.algs4.util.ArrayUtils;

import java.util.Arrays;

public class TopKQuickSelect
{
    /**
     * @return one of the top k elements in the array A
     * There may be more than one element with priority k.
     * This algorithm provided that there's always exactly k - 1 elements
     * smaller than the return value.
     */
    public static <E extends Comparable<E>> E topK(E[] A, int k)
    {
        ArrayUtils.checkNull(A);
        if (k < 1 || k > A.length)
            throw new IllegalArgumentException("k ∈ [1, A.length]");
        E[] cpy = Arrays.copyOf(A, A.length); // 避免破坏原数组
        return topK(cpy, k - 1, 0, A.length - 1);
    }
    
    /**
     * @param x: 0-based priority
     */
    private static <E extends Comparable<E>> E topK(E[] A, int x, int low, int high)
    {
        while (true)
        {
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
            
            if (lt <= x && x <= gt)
                return A[x];
            else if (x < lt)
                high = lt - 1;
            else low = gt + 1;
        }
    }
    
    
}
