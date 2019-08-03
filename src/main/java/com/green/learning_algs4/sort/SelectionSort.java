package com.green.learning_algs4.sort;

/**
 * selection sort
 *  time efficiency: O(N^2)
 * space efficiency: O(1)
 * Unstable!!!
 */
public class SelectionSort
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        for (int i = 0; i < A.length - 1; i++)
        {
            E minVal = A[i];
            int minIdx = i;
            for (int j = i + 1; j < A.length; j++)
                if (A[j].compareTo(minVal) < 0)
                {
                    minVal = A[j];
                    minIdx = j;
                }
            
            A[minIdx] = A[i];
            A[i] = minVal;
        }
    }
}
