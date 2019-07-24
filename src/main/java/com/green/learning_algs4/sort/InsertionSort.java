package com.green.learning_algs4.sort;

/**
 * @see edu.princeton.cs.algs4.Insertion
 */
public class InsertionSort
{
//    private InsertionSort() {}
    
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        sort(A, 0, A.length - 1);
    }
    
    public static <E extends Comparable<E>> void sort(E[] A, int low, int high)
    {
        for (int i = low + 1; i <= high; i++)
        {
            E val = A[i];
            int j = i;
            for (; j > low && A[j - 1].compareTo(val) > 0; j--)
                A[j] = A[j - 1]; // half-exchange
            A[j] = val;
        }
    }
}
