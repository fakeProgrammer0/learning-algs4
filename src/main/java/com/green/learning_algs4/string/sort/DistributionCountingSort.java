package com.green.learning_algs4.string.sort;

import java.util.TreeMap;

/**
 * 分布计数排序 / 桶排序
 * Alias for "bin sort" or "bucket sort"
 */
public class DistributionCountingSort
{
    private final static double MEMORY_OCCUPIED = 0.8;
    private final static long MAX_AVAILABLE_MEMORY = (long) (MEMORY_OCCUPIED * Runtime.getRuntime().maxMemory());
    private final static long MAX_INT_RANGE = MAX_AVAILABLE_MEMORY >>> 2;
    
    /**
     * Ihe algorithm requires O(d) space, where <b>d</b> is the difference of the
     * maximum integer value and the minimum integer value in {@code A}.
     *
     * The side effect is that the references of integers in the original array
     * will be changed. That means entries of the {@code A} will references
     * new integer objects in the memory.
     * @param A the integer array to be sorted
     */
    public static void sort(Integer[] A)
    {
        int maxVal = A[0], minVal = A[0];
        for (int i = 1; i < A.length; i++)
        {
            if (A[i].compareTo(maxVal) > 0)
                maxVal = A[i];
            if (A[i].compareTo(minVal) < 0)
                minVal = A[i];
        }

        long len = 1L + maxVal - minVal;
        if(len <= MAX_INT_RANGE)
        {
            // time efficiency: O(N)
    
            // count the frequencies of integer values
            // integer elements are aligned to the length of the new array
            int[] count = new int[(int)len];
            for (int i = 0; i < A.length; i++)
                count[A[i] - minVal]++;
    
            int j = 0;
            for (int i = 0; i < count.length; i++)
                while (count[i] != 0)
                {
                    // Integers are immutable objects, so it's just ok to copy directly.
                    // The side effect is that the references of the original array will be changed.
                    A[j++] = i + minVal; // restore the integer value
                    count[i]--;
                }
        }else{
            // The range of integers is too large.
            // An array in java with max length of {@code Integer.MAX_VALUE} doesn't have enough space.
            
            // use a TreeMap to substitute the original array
            // then the algorithm remains the same
            // time efficiency: O(NlogN)
    
            TreeMap<Integer, Integer> value2Count = new TreeMap<>();
            for(Integer v : A)
                if(!value2Count.containsKey(v))
                    value2Count.put(v, 1);
                else value2Count.put(v, value2Count.get(v) + 1);
                
            int j = 0;
            for(Integer v: value2Count.navigableKeySet())
            {
                int count = value2Count.get(v);
                while (count > 0)
                {
                    A[j++] = v;
                    count--;
                }
            }
        }
    }
}
