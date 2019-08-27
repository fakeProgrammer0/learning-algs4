package com.green.learning.algs4.string.search;

import com.green.learning.algs4.string.Alphabet;

/**
 * A simplified version of BoyerMoore
 * <a href="https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore%E2%80%93Horspool_algorithm"></a>
 * @see BoyerMooreBadSymbolShift for a Boyer Moore variant using only bad symbol shifts
 * @see BoyerMoore for a full implementation of Boyer Moore
 */
public class Horspool
{
    private final String pattern;
    private final int M;
    private final Alphabet alphabet;
    private final int[] shifts;
    
    public Horspool(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        M = pattern.length();
        alphabet = new Alphabet(pattern);
        
        shifts = new int[alphabet.radix()];
        for (int j = 0; j < M - 1; j++)
            shifts[alphabet.toIndex(pattern.charAt(j))] = M - 1 - j;
        int rightmostCharIndex = alphabet.toIndex(pattern.charAt(M - 1));
        if (shifts[rightmostCharIndex] == 0)
            shifts[rightmostCharIndex] = M;
    }
    
    private int badShift(char c)
    {
        if (alphabet.contains(c))
            return shifts[alphabet.toIndex(c)];
        return M;
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        if(N < M) return -1;
        
        for (int i = 0; i <= N - M; )
        {
            for (int j = M - 1; pattern.charAt(j) == text.charAt(i + j); j--)
                if (j == 0) return i;
            i += badShift(text.charAt(i + M - 1));
        }
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        Horspool horspool = new Horspool(pattern);
        return horspool.search(text);
    }
}
