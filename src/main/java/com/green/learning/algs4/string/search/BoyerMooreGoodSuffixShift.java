package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;

public class BoyerMooreGoodSuffixShift
{
    private final int M;
    private final String pattern;
    private final int[] shifts;
    
    public BoyerMooreGoodSuffixShift(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        this.M = pattern.length();
        shifts = getShifts();
    }
    
    private int[] getShifts()
    {
        int[] s = new int[M + 1];
        int[] f = new int[M + 1];
        
        // case 1
        int i = M, j = M + 1;
        f[i] = j;
        while (i > 0)
        {
            while (j <= M && pattern.charAt(i - 1) != pattern.charAt(j - 1))
            {
                if(s[j] == 0) s[j] = j - i;
                j = f[j];
            }
            f[--i] = --j;
        }
        
        // case 2
        j = f[0];
        for(i = 0; i < M; i++)
        {
            if(s[i] == 0) s[i] = j;
            if(i == j) j = f[j];
        }
        
        return s;
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        if (N >= M)
        {
            for (int i = 0, j; i <= N - M; )
            {
                j = M - 1;
                for (; text.charAt(i + j) == pattern.charAt(j); j--)
                    if (j == 0) return i;
                i += shifts[j + 1];
            }
        }
        
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new BoyerMooreGoodSuffixShift(pattern).search(text);
    }
    
    public Iterable<Integer> searchAll(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        XQueue<Integer> occurIndices = new XLinkedQueue<>();
        if (M <= N)
        {
            for (int i = 0, j; i <= N - M; )
            {
                for (j = M - 1; pattern.charAt(j) == text.charAt(i + j); j--)
                    if (j == 0)
                    {
                        occurIndices.enqueue(i);
                        break;
                    }
                i += shifts[j + 1];
            }
        }
        return occurIndices;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        return new BoyerMooreGoodSuffixShift(pattern).searchAll(text);
    }
}
