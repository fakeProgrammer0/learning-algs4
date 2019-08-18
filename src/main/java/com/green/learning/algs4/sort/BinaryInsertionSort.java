package com.green.learning.algs4.sort;

/**
 * average case O(nlogn)
 * worst case O(n^2)
 * @see edu.princeton.cs.algs4.BinaryInsertion
 */
public class BinaryInsertionSort
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        for (int i = 1; i < A.length; i++)
        {
            E val = A[i];
            
            // since the subarray (0, i) is in order
            // use binary search to find where to insert A[i]
            int low = 0, high = i - 1;
            while (low <= high)
            {
                int mid = (low + high) / 2;
                if (val.compareTo(A[mid]) < 0) high = mid - 1;
                else if (val.compareTo(A[mid]) > 0) low = mid + 1;
                else
                {
                    // keep the sorting stable
                    while (mid < i && val.compareTo(A[mid]) == 0)
                        mid++;
                    low = mid;
                }
            }
            
            for (int j = i; j > low; j--)
                A[j] = A[j - 1]; // half exchanges
            A[low] = val;
        }
    }
}
