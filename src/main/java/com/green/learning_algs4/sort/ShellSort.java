package com.green.learning_algs4.sort;

/**
 * Shellsort using a gap sequence of 3n + 1
 * 1, 4, 13, 40, 121, ...
 * @see edu.princeton.cs.algs4.Shell
 */
public class ShellSort
{
    public static <E extends Comparable<E>> void sort2(E[] A)
    {
        int N = A.length;
        
        int h = 1;
        while (h < N / 3)
            h = h * 3 + 1;
        // gap sequence: 1, 4, 13, 40, ...
        while (h >= 1)
        {
            // h-sort the array
            for (int i = h; i < N; i++)
            {
                int j = i;
                E val = A[i];
                for (; j >= h && val.compareTo(A[j - h]) < 0; j -= h)
                    A[j] = A[j - h];
                A[j] = val;
            }
            h /= 3;
        }
    }
    
    /**
     * make better use of CPU cache
     * seem to perform better than {@code sort2}
     */
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        int N = A.length;
        
        int h = 1;
        while (h < N / 3)
            h = h * 3 + 1;
        
        while (h >= 1)
        {
            // h-sort the array
            // at pass k, sort the k-th subarray
            // make better use of CPU cache
            for (int k = 0; k < h; k++)
                for (int i = k + h; i < N; i += h)
                {
                    int j = i;
                    E val = A[i];
                    for (; j >= h && val.compareTo(A[j - h]) < 0; j -= h)
                        A[j] = A[j - h];
                    A[j] = val;
                }
            h /= 3;
        }
    }
}
