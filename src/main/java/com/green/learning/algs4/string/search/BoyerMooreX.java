package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;
import com.green.learning.algs4.string.Alphabet;

public class BoyerMooreX
{
    private final String pattern;
    private final int M;
    private final Alphabet alphabet;
    private final int[] rightmostIndices;
    private final int[] goodSuffixShifts;
    
    public BoyerMooreX(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        this.M = pattern.length();
        alphabet = new Alphabet(pattern);
        rightmostIndices = getRightmostIndices();
        goodSuffixShifts = getGoodSuffixShifts();
    }
    
    private int[] getRightmostIndices()
    {
        int[] badSymbolShifts = new int[alphabet.radix()];
        for (int j = 0; j < M; j++)
            badSymbolShifts[alphabet.toIndex(pattern.charAt(j))] = j;
        return badSymbolShifts;
    }
    
    private int[] getGoodSuffixShifts()
    {
        /*
         f[i]: the rightmost starting position of the widest border of pattern[i ... M-1]
         */
        int[] f = new int[M + 1];
        int[] s = new int[M + 1];
        
        // case 1
        int i = M, j = M + 1;
        f[M] = M + 1;
        while (i > 0)
        {
            while (j <= M && pattern.charAt(i - 1) != pattern.charAt(j - 1))
            {
                if (s[j] == 0) s[j] = j - i;
                j = f[j];
            }
            i--;
            j--;
            f[i] = j;
        }
        
        // case 2
        j = f[0];
        for (i = 0; i <= M; i++)
        {
            if (s[i] == 0) s[i] = j;
            if (i == j) j = f[j];
        }
        
        return s;
    }
    
    private int badSymbolShift(int j, char c)
    {
        int rightmost = alphabet.contains(c) ?
                rightmostIndices[alphabet.toIndex(c)] : -1;
        return Math.max(1, j - rightmost);
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text, M);
        
        final int N = text.length();
        int d1, d2;
        for (int i = 0, shift = 0; i <= N - M; i += shift)
        {
            int j = M - 1;
            for (; pattern.charAt(j) == text.charAt(i + j); j--)
                if (j == 0) return i;
            d1 = badSymbolShift(j, text.charAt(i + j));
            d2 = goodSuffixShifts[j + 1];
            shift = Math.max(d1, d2);
        }
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new BoyerMooreX(pattern).search(text);
    }
    
    public Iterable<Integer> searchAll(String text)
    {
        SubstringSearchs.checkText(text, M);
        XQueue<Integer> occurIndices = new XLinkedQueue<>();
        final int N = text.length();
        for (int i = 0, j; i <= N - M; )
        {
            j = M - 1;
            for (; j >= 0 && pattern.charAt(j) == text.charAt(i + j); j--) ;
            if (j < 0)
            {
                occurIndices.enqueue(i);
                // 将pattern向右移动最长border的长度
                i += goodSuffixShifts[0];
            } else
            {
                i += Math.max(badSymbolShift(j, text.charAt(i + j)), goodSuffixShifts[j + 1]);
            }
        }
        return occurIndices;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        return new BoyerMooreX(pattern).searchAll(text);
    }
}
