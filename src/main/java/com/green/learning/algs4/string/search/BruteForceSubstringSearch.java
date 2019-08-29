package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;

public class BruteForceSubstringSearch
{
    private final String pattern;
    
    public BruteForceSubstringSearch(String pattern)
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
        for (int i = 0, j; i <= N - M; i++)
        {
            for (j = 0; pattern.charAt(j) == text.charAt(i + j); j++)
                if (j == M - 1) return i;
        }
        return -1;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        SubstringSearchs.checkText(text);
        final int M = pattern.length(), N = text.length();
        XQueue<Integer> occurIndices = new XLinkedQueue<>();
        for (int i = 0, j; i <= N - M; i++)
        {
            for (j = 0; pattern.charAt(j) == text.charAt(i + j); j++)
                if (j == M - 1)
                {
                    occurIndices.enqueue(i);
                    break;
                }
        }
        return occurIndices;
    }
}
