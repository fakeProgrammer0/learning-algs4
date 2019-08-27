package com.green.learning.algs4.string.search;

import com.green.learning.algs4.string.Alphabet;

public class BoyerMooreBadSymbolShift
{
    private final String pattern;
    private final Alphabet alphabet;
    private final int[] rightmostIndices;
    
    public BoyerMooreBadSymbolShift(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
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
        final int M = pattern.length();
        SubstringSearchs.checkText(text);
        final int N = text.length();
        if(N < M) return -1;
        
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
        return new BoyerMooreBadSymbolShift(pattern).search(text);
    }
}
