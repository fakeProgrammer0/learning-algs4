package com.green.learning.algs4.sort;

/**
 * standard merge sort reusing the auxiliary array
 *
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
        int l = low, r = mid + 1;
        int i = low;
        while (l <= mid && r <= high)
        {
            if (A[l].compareTo(A[r]) <= 0)
                aux[i++] = A[l++];
            else aux[i++] = A[r++];
        }
        
        if (l > mid)
            while (r <= high)
                aux[i++] = A[r++];
        else // if (r > high)
            while (l <= mid)
                aux[i++] = A[l++];
        
        for (i = low; i <= high; i++)
            A[i] = aux[i];
    }
}
