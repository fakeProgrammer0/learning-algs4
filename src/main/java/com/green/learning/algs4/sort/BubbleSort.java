package com.green.learning.algs4.sort;

import com.green.learning.algs4.util.ArrayUtils;

public class BubbleSort
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        for (int i = 0; i < A.length - 1; i++)
            for (int j = 0; j < A.length - i - 1; j++)
                if (A[j].compareTo(A[j + 1]) > 0)
                    ArrayUtils.swap(A, j, j + 1);
    }
}
