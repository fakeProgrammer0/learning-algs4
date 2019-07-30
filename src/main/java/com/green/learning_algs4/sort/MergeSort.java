package com.green.learning_algs4.sort;

/**
 * standard merge sort reusing the auxiliary array
 * @see edu.princeton.cs.algs4.Merge
 */
public class MergeSort
{
    @SuppressWarnings("unchecked")
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        E[] aux = (E[]) new Comparable[A.length];
        sort(A, aux, 0, A.length - 1);
    }
    
    private static <E extends Comparable<E>> void sort(E[] A, E[] aux, int low, int high)
    {
        if (low >= high)
            return;
        
        int mid = (low + high) / 2;
        sort(A, aux, low, mid);
        sort(A, aux, mid + 1, high);
        merge(A, aux, low, mid, high);
    }
    
    private static <E extends Comparable<E>> void merge(E[] A, E[] aux, int low, int mid, int high)
    {
        int i = low;
        for (; i <= mid; i++)
            aux[i] = A[i];
        int j = high;
        for (; j > mid; i++, j--)
            aux[i] = A[j];
        
        int l = low, r = high;
        int idx = low;
        while (l <= r)
            if (aux[l].compareTo(aux[r]) <= 0)
                A[idx++] = aux[l++];
            else
                A[idx++] = aux[r--];
    }
}
