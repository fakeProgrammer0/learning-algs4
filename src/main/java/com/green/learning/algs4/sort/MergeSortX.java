package com.green.learning.algs4.sort;

/**
 * merge sort with array creations overhead
 * use special type of merge, causing the whole merge sort to be unstable
 * @see edu.princeton.cs.algs4.Merge
 */
public class MergeSortX
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        sort(A, 0, A.length - 1);
    }
    
    private static <E extends Comparable<E>> void sort(E[] A, int low, int high)
    {
        if (low >= high)
            return;
        
        int mid = (low + high) / 2;
        sort(A, low, mid);
        sort(A, mid + 1, high);
        merge(A, low, mid, high);
    }
    
    @SuppressWarnings("unchecked")
    private static <E extends Comparable<E>> void merge(E[] A, int low, int mid, int high)
    {
        E[] aux = (E[]) new Comparable[high - low + 1];
        // two pointers iterate to the center
        // use this case of merge make the whole merge sort unstable!!!
        int i = 0, j = low;
        for (; j <= mid; i++, j++)
            aux[i] = A[j];
        j = high;
        for (; j > mid; i++, j--)
            aux[i] = A[j];
        
        int l = 0, r = aux.length - 1;
        int idx = low;
        while (l <= r)
            if (aux[l].compareTo(aux[r]) <= 0)
                A[idx++] = aux[l++];
            else
                A[idx++] = aux[r--];
    }
}
