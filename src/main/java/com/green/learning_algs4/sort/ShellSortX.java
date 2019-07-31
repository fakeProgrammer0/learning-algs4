package com.green.learning_algs4.sort;

/**
 * Shellsort using a gap sequence of
 * N / 2, N / 4, ..., 4, 2, 1
 * @see ShellSort
 */
public class ShellSortX
{
    public static <E extends Comparable<E>> void sort(E[] A)
    {
        for (int gap = A.length / 2; gap >= 1; gap /= 2)
            for (int k = 0; k < gap; k++)
                for (int i = k + gap; i < A.length; i += gap)
                {
                    E val = A[i];
                    int j = i;
                    for (; j - gap >= 0 && val.compareTo(A[j - gap]) < 0; j -= gap)
                        A[j] = A[j - gap];
                    A[j] = val;
                }
    }
    
    // 带有4个循环的sort效率比带有3个循环的sort2稍微好一点点
    // 原因：sort2交替在多个子数列中走动，局部引用性不好，CPU缓存命中率低
    public static <E extends Comparable<E>> void sort2(E[] A)
    {
        for (int gap = A.length / 2; gap >= 1; gap /= 2)
            for (int i = gap; i < A.length; i++)
            {
                E val = A[i];
                int j = i;
                for (; j - gap >= 0 && val.compareTo(A[j - gap]) < 0; j -= gap)
                    A[j] = A[j - gap];
                A[j] = val;
            }
    }
    
}
