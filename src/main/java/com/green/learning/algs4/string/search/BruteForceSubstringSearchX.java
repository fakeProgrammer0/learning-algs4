package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;

public class BruteForceSubstringSearchX
{
    private final String pattern;
    
    public BruteForceSubstringSearchX(String pattern)
    {
        this.pattern = pattern;
    }
    
    public int search(String text)
    {
        return search(text, pattern);
    }
    
    public Iterable<Integer> searchAll(String text)
    {
        return searchAll(text, pattern);
    }
    
    public static int search(String text, String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        SubstringSearchs.checkText(text);
        final int M = pattern.length(), N = text.length();
        for (int i = M - 1, j; i < N ; i++)
        {
            for (j = 0; pattern.charAt(M - 1 - j) == text.charAt(i - j); j++)
                if (j == M - 1) return i - j;
        }
        return -1;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        SubstringSearchs.checkText(text);
        final int M = pattern.length(), N = text.length();
        XQueue<Integer> occurIndices = new XLinkedQueue<>();
        for (int i = M - 1, j; i < N; i++)
        {
            for (j = 0; pattern.charAt(M - 1 - j) == text.charAt(i - j); j++)
                if (j == M - 1)
                {
                    occurIndices.enqueue(i - j);
                    break;
                }
        }
        return occurIndices;
    }
}
