package com.green.learning.algs4.sort;

/**
 * Bottom up merge sort ((non-recursive)
 * @see edu.princeton.cs.algs4.MergeBU
 */
public class MergeSortBU
{
    @SuppressWarnings("unchecked")
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        final int N = A.length;
        E[] aux = (E[]) new Comparable[N];
        // two intervals: A[low, mid], A[mid + 1, high]
        // halfLen equals half of the length of the interval A[low, high]
        // halfLen = high - mid OR halfLen = mid - low + 1
        for (int halfLen = 1, len = 2; len <= N; halfLen = len)
        {
            len = halfLen << 1;
            for (int low = 0; low + halfLen < N; low += len)
            {
                // A[low, mid] and A[mid + 1, high] are both sorted respectively
                int mid = low + halfLen - 1;
                int high = Math.min(mid + halfLen, N - 1);
                merge(A, aux, low, mid, high);
            }
        }
    }
    
    private static <E extends Comparable<E>> void merge(E[] A, E[] aux, int low, int mid, int high)
    {
//        assert ArrayUtils.isSorted(A, low, mid);
//        assert ArrayUtils.isSorted(A, mid + 1, high);
        
        for (int i = low; i <= high; i++)
            aux[i] = A[i];
        
        int l = low, r = mid + 1;
        int idx = low;
        while (l <= mid && r <= high)
        {
            if (aux[l].compareTo(aux[r]) <= 0)
                A[idx++] = aux[l++];
            else A[idx++] = aux[r++];
        }
        
        if (l > mid)
            while (r <= high)
                A[idx++] = aux[r++];
        else // if (r > high)
            while (l <= mid)
                A[idx++] = aux[l++];
        
//        assert ArrayUtils.isSorted(A, low, high);
    }
    
}
