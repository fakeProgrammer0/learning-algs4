package com.green.learning_algs4.string.search;

import com.green.learning_algs4.string.Alphabet;
import com.green.learning_algs4.string.StringUtils;

public class BoyerMooreBadShift
{
    private final String pattern;
    private final Alphabet alphabet;
    private final int[] rightmostIndices;
    
    public BoyerMooreBadShift(String pattern)
    {
        if (StringUtils.isEmpty(pattern))
            throw new IllegalArgumentException("pattern should not be null or empty");
        this.pattern = pattern;
        alphabet = new Alphabet(pattern);
        rightmostIndices = new int[alphabet.radix()];
        for (int j = 0; j < pattern.length(); j++)
            rightmostIndices[alphabet.toIndex(pattern.charAt(j))] = j;
        
        
    }
    
    private int badSymbolShift(int j, char c)
    {
        int right = alphabet.contains(c) ?
                rightmostIndices[alphabet.toIndex(c)] : -1;
        return Math.max(1, j - right);
    }
    
    public int search(String text)
    {
        if (StringUtils.isEmpty(text))
            throw new IllegalArgumentException("text should not be null or empty");
        
        final int M = pattern.length(), N = text.length();
        for (int i = 0, shift = 0; i <= N - M; i += shift)
        {
            int j = M - 1;
            for (; pattern.charAt(j) == text.charAt(i + j); j--)
                if (j == 0) return i;
            
            shift = badSymbolShift(j, text.charAt(i + j));
        }
        
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new BoyerMooreBadShift(pattern).search(text);
    }
}
