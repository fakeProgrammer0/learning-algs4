package com.green.learning.algs4.string.search;

import com.green.learning.algs4.string.StringUtils;

public class SubstringSearchs
{
    static void checkPattern(String pattern)
    {
        if (StringUtils.isEmpty(pattern))
            throw new IllegalArgumentException("pattern should not be null or empty");
    }
    
    static void checkText(String text)
    {
        if (StringUtils.isEmpty(text))
            throw new IllegalArgumentException("text should not be null or empty");
    }
    
    @Deprecated
    static void checkText(String text, int M)
    {
        if (StringUtils.isEmpty(text))
            throw new IllegalArgumentException("text should not be null or empty");
        if(text.length() < M)
            throw new IllegalArgumentException("text's length is smaller than pattern's");
    }
    
    public static int boyerMoore(String text, String pattern)
    {
        return BoyerMoore.search(text, pattern);
    }
    
    public static int horspool(String text, String pattern)
    {
        return Horspool.search(text, pattern);
    }
    
    public static int kmpDFA(String text, String pattern)
    {
        return KMPDFA.search(text, pattern);
    }
    
    public static int kmp(String text, String pattern)
    {
        return KMP.search(text, pattern);
    }
}
