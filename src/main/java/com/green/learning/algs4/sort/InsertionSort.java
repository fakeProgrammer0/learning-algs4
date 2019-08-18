package com.green.learning.algs4.sort;

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
    
    public static void sortStrings(String[] A, int low, int high, int d)
    {
        for (int i = low + 1; i <= high; i++)
        {
            String str = A[i];
            int j = i;
            for (; j > low && compare(str, A[j - 1], d) < 0; j--)
                A[j] = A[j - 1];
            A[j] = str;
        }
    }
    
    private static int compare(String str1, String str2, int d)
    {
        int len1 = str1.length(), len2 = str2.length();
        // assert len1 >= d && len2 >= d;
        
        // method 1: overhead to create substrings
//        return str1.substring(d).compareTo(str2.substring(d));
        
        // method 2: modified {@code String.compare()}
        int minLen = Math.min(len1, len2);
        for (int k = d; k < minLen; k++)
        {
            char c1 = str1.charAt(k);
            char c2 = str2.charAt(k);
            if (c1 != c2)
                return c1 - c2;
        }
        return len1 - len2;
    }
}
