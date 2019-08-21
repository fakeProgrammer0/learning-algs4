package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;

/**
 * Knuth-Morris-Pratt algorithm for substring search
 * <a href="http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm#"></a>
 */
public class KMP
{
    private final String pattern;
    private final int M;
    private final int[] next;
    
    public KMP(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        this.M = pattern.length();
        next = new int[M + 1];
        initNext();
    }
    
    /**
     * Calculate the next table
     */
    private void initNext()
    {
        next[0] = -1;
        int j = 1, b = 0;
        // b = b[j], but there's no need to store the array b[]
        while (j < M)
        {
            // update next[j] in last iteration
            next[j] = pattern.charAt(b) == pattern.charAt(j) ? next[b] : b;
            while (b >= 0 && pattern.charAt(b) != pattern.charAt(j))
                b = next[b];
            j++; b++;
        }
        next[j] = b;
    }
    
    /**
     * Calculate the next table
     */
    private void initNext0()
    {
        int j = 0, b = -1;
        // b = b[j], but there's no need to store the array b[]
        next[j] = b;
        while (j < M)
        {
            while (b >= 0 && pattern.charAt(b) != pattern.charAt(j))
                b = next[b];
            j++; b++;
            if(j < M && pattern.charAt(b) == pattern.charAt(j))
                // 递推（数学归纳），尝试更短的border，由于next[b]此时已计算完成，所以直接设置为next[b]即可
                next[j] = next[b];
            else next[j] = b;
        }
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text, M);
        final int N = text.length();
        int i = 0, j = 0;
        while (i < N)
        {
            while (j >= 0 && pattern.charAt(j) != text.charAt(i))
                j = next[j];
            i++; j++;
            if(j == M) return i - M;
        }
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new KMP(pattern).search(text);
    }
    
    public Iterable<Integer> searchAll(String text)
    {
        SubstringSearchs.checkText(text, M);
        XQueue<Integer> occurIndices = new XLinkedQueue<>();
        final int N = text.length();
        int i = 0, j = 0;
        while (i < N)
        {
            while (j >= 0 && pattern.charAt(j) != text.charAt(i))
                j = next[j];
            i++; j++;
            if(j == M)
            {
                occurIndices.enqueue(i - M);
                // 最大限度移动pattern，继续查找text中其余pattern
                j = next[M];
            }
        }
        return occurIndices;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        return new KMP(pattern).searchAll(text);
    }
}
