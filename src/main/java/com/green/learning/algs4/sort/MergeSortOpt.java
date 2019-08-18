package com.green.learning.algs4.sort;

/**
 * an optimized version of mergesort, which outperform the
 * original version of mergesort.
 *  tip1: use insertion sort to deal with small subarrays
 *  tip2: check if the specific subarray is sorted before merge
 *  tip3: reuse the whole auxiliary array
 * @see edu.princeton.cs.algs4.MergeX
 */
public class MergeSortOpt
{
    private static final int INSERTION_CUTOFF = 7;
    
    @SuppressWarnings("unchecked")
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        E[] aux = (E[]) new Comparable[A.length];
        System.arraycopy(A, 0, aux, 0, A.length);
        sort(A, aux, 0, A.length - 1);
    }
    
    private static <E extends Comparable<E>> void sort(E[] A, E[] aux, int low, int high)
    {
        // tip1：小数组采用插排
        if (high - low + 1 <= INSERTION_CUTOFF)
        {
            InsertionSort.sort(A, low, high);
            return;
        }
        
        if (low >= high) return;
        int mid = (low + high) / 2;
        sort(aux, A, low, mid);
        sort(aux, A, mid + 1, high);
        // 此时，aux[low, mid], aux[mid+1, high]已排序，A因为作为辅助数组，所以A[low, high]顺序较乱
    
        // tip2：检查是否已排好序
        if (aux[mid].compareTo(aux[mid + 1]) <= 0)
        {
            System.arraycopy(aux, low, A, low, high - low + 1);
            return;
        }
        merge(A, aux, low, mid, high); // 从aux归并到A中
    }
    
    private static <E extends Comparable<E>> void merge(E[] A, E[] aux, int low, int mid, int high)
    {
//        assert ArrayUtils.isSorted(aux, low, mid);
//        assert ArrayUtils.isSorted(aux, mid + 1, high);
        
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
