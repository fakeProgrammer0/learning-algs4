package com.green.learning.algs4.string.sort;

import com.green.learning.algs4.sort.InsertionSort;
import com.green.learning.algs4.util.ArrayUtils;

/**
 * Superior Performance handling string sort
 * @see edu.princeton.cs.algs4.Quick3string
 */
public class QuickSort3WayString
{
    private final static int INSERTION_CUTOFF = 8;
    
    public static void sort(String[] A)
    {
        sort(A, 0, A.length - 1, 0);
    }
    
    private static void sort(String[] A, int low, int high, int d)
    {
        if(low >= high) return;
        if (high - low < INSERTION_CUTOFF)
        {
            InsertionSort.sortStrings(A, low, high, d);
            return;
        }
        
        int c = chatAt(A[low], d);
        int lt = low, gt = high;
        int i = low + 1;
        while (i <= gt)
        {
            int cmp = chatAt(A[i], d) - c;
            if (cmp < 0) ArrayUtils.swap(A, lt++, i++);
            else if (cmp > 0) ArrayUtils.swap(A, i, gt--);
            else i++;
        }
        
        sort(A, low, lt - 1, d);
        if(c >= 0) // 防止因一组""空字符聚集产生死递归
            sort(A, lt, gt, d + 1);
        sort(A, gt + 1, high, d);
    }
    
    private static int chatAt(String str, int d)
    {
        if (d < str.length())
            return str.charAt(d);
        return -1;
    }
}
