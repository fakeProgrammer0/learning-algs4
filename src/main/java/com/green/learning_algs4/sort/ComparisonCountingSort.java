package com.green.learning_algs4.sort;

public class ComparisonCountingSort
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        // counts[i] calculates the number of elements
        // supposed to be put in front of A[i]
        int[] counts = new int[A.length];
        for (int i = 0; i < A.length - 1; i++)
            for (int j = i + 1; j < A.length; j++)
                if (A[i].compareTo(A[j]) < 0)
                    counts[j]++;
                else counts[i]++;
        
        Comparable[] S = new Comparable[A.length];
        
        for (int i = 0; i < A.length; i++)
            S[counts[i]] = A[i];
        
        System.arraycopy(S, 0, A, 0, S.length);
    }
}
