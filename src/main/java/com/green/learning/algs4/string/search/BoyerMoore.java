package com.green.learning.algs4.string.search;

import com.green.learning.algs4.string.Alphabet;

public class BoyerMoore
{
    private final String pattern;
    private final int M;
    private final Alphabet alphabet;
    private final int[] badSymbolShifts;
    private final int[] goodSuffixShifts;
    
    private int[] getBadSymbolShifts()
    {
        int[] badSymbolShifts = new int[alphabet.radix()];
        for (int j = 0; j < M; j++)
            badSymbolShifts[alphabet.toIndex(pattern.charAt(j))] = M - 1 - j;
        return badSymbolShifts;
    }
    
    private int badSymbolShift(int j, char c)
    {
        if (!alphabet.contains(c)) return M;
        int shift = badSymbolShifts[alphabet.toIndex(c)];
        return Math.max(shift, M - j);
    }
    
    /**
     * a brute-force method calculate good suffix shifts table
     *
     * @return the good suffix shifts table
     */
    private int[] getGoodSuffixShifts()
    {
        int[] goodSuffixShifts = new int[M];
        int lastPrefixPos = M;
        goodSuffixShifts[M - 1] = M;
        for (int j = M - 2; j >= 0; j--)
        {
            if (isPrefix(j + 1))
                lastPrefixPos = j + 1;
            goodSuffixShifts[j] = M - 1 - j + lastPrefixPos;
        }
        
        for (int k = 0; k < M - 1; k++)
        {
            int suffixLen = suffixLength(k);
            goodSuffixShifts[M - 1 - suffixLen] = M - 1 - k + suffixLen;
        }
        
        return goodSuffixShifts;
    }
    
    private void checkGoodSuffixShifts()
    {
        int[] goodShifts = BoyerMooreX.buildGoodSuffixShifts(pattern);
        for(int j = 0; j < M - 1; j++)
            assert goodSuffixShifts[j] == goodShifts[j] + M - 1 - j;
    }
    
    /**
     * Is pattern[rightStart : M-1] a prefix of pattern?
     */
    private boolean isPrefix(int rightStart)
    {
        assert rightStart >= 0 && rightStart < M;
        for (int le = M - 1 - rightStart, r = M - 1; le >= 0; le--, r--)
            if (pattern.charAt(le) != pattern.charAt(r))
                return false;
        
        return true;
    }
    
    /**
     * @return the maximum length of the substring ends at leftEnd and is a suffix.
     */
    private int suffixLength(int leftEnd)
    {
        int len = 0;
        for (int le = leftEnd, r = M - 1;
             le >= 0 && pattern.charAt(le) == pattern.charAt(r);
             le--, r--)
            len++;
        return len;
    }
    
    public BoyerMoore(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        this.M = pattern.length();
        alphabet = new Alphabet(pattern);
        badSymbolShifts = getBadSymbolShifts();
        goodSuffixShifts = getGoodSuffixShifts();
        checkGoodSuffixShifts();
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text, M);
        final int N = text.length();
        if (N < M) return -1;
        
        int d1, d2;
        for (int i = M - 1, j; i < N; )
        {
            for (j = M - 1; pattern.charAt(j) == text.charAt(i); i--, j--)
                if (j == 0) return i;
            d1 = badSymbolShift(j, text.charAt(i));
            d2 = goodSuffixShifts[j];
            i += Math.max(d1, d2);
        }
        
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new BoyerMoore(pattern).search(text);
    }
}
