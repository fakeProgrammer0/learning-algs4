package com.green.learning_algs4.sort;

import com.green.learning_algs4.util.ArrayUtils;

/**
 * In-place Heap Sort
 *  time efficiency: O(NlogN)
 * space efficiency: O(1)
 * Unstable!!!
 * @see edu.princeton.cs.algs4.Heap
 */
public class HeapSort
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        // 1.Construct a maximum heap with 0-based index
        for (int i = A.length / 2 - 1; i >= 0; i--)
            sink(A, A.length, i);
        
        // 2.In loop {@code i}, constantly exchange
        // * the current root with the last {@code i} element,
        // * and then sink the new root.
        // * Repeat {@code N - 1} loop
        for (int i = A.length - 1; i > 0; i--)
        {
            ArrayUtils.swap(A, i, 0);
            sink(A, i, 0);
        }
    }
    
    /**
     * sink the element at index {@code k} of a maximum heap {@code A} with length
     * {@code N}
     *
     * @param A   the maximum heap
     * @param N   the length of the maximum heap
     * @param k   the index of the element to be sank
     * @param <E> the type of the elements
     */
    private static <E extends Comparable<E>> void sink(E[] A, int N, int k)
    {
        E element = A[k];
        int j = 2 * k + 1;
        while (j < N)
        {
            if (j < N - 1 && A[j + 1].compareTo(A[j]) > 0)
                j++;
            if (element.compareTo(A[j]) >= 0) break;
            A[k] = A[j];
            k = j;
            j = 2 * k + 1;
        }
        A[k] = element;
    }
}
