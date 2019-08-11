package com.green.learning_algs4.string.sort;

/**
 * Key Indexed Counting Sort for characters
 * @see DistributionCountingSort
 */
public class KeyIndexedCountingSort
{
    /**
     * the size of the unicode alphabet
     */
    private static final int R = Character.MAX_VALUE + 1;
    
    /**
     * time efficiency: O(N), where N is the length of the {@code chars} array
     * space efficiency: O(R), where {@code R} is the size of the unicode alphabet
     * @param chars characters to be sorted
     */
    public static void sort(char[] chars)
    {
        int[] counts = new int[R + 1];
        for (int i = 0; i < chars.length; i++)
            counts[chars[i] + 1]++;
        
        for (int i = 2; i < counts.length; i++)
            counts[i] += counts[i - 1];
        
        char[] aux = new char[chars.length];
        for (int i = 0; i < chars.length; i++)
            aux[counts[chars[i]]++] = chars[i];
        
        for (int i = 0; i < chars.length; i++)
            chars[i] = aux[i];
    }
}
