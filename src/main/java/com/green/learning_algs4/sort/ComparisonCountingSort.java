package com.green.learning_algs4.sort;

/**
 * A sorting method with very very poor performance
 * Time Efficiency: O(N^2); Space Efficiency: O(N)
 */
public class ComparisonCountingSort
{
    @SuppressWarnings("unchecked")
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        // counts[x] calculates the number of elements
        // supposed to be put in front of A[x]
        int[] counts = new int[A.length];
        for (int i = 0; i < A.length - 1; i++)
            for (int j = i + 1; j < A.length; j++)
                if (A[i].compareTo(A[j]) <= 0) // for stable, use <= instead of <
                    counts[j]++;
                else counts[i]++;
        
        E[] S = (E[]) new Comparable[A.length];
        
        for (int i = 0; i < A.length; i++)
            S[counts[i]] = A[i];
        
        System.arraycopy(S, 0, A, 0, S.length);
    }
}
