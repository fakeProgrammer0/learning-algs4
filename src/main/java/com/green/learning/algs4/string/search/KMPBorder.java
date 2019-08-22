package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XArrayList;
import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XList;
import com.green.learning.algs4.list.XQueue;

/**
 * A modified Knuth-Morris-Pratt (KMP) algorithm for substring search, whose
 * efficiency is lower than the original KMP algorithm.
 * <a href="http://www.iti.fh-flensburg.de/lang/algorithmen/pattern/kmpen.htm#"></a>
 * @see KMP for a optimized version
 */
public class KMPBorder
{
    private final String pattern;
    private final int M;
    private final int[] widestBorderLens;
    
    public KMPBorder(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        this.M = pattern.length();
        widestBorderLens = new int[M + 1];
        preprocess();
    }
    
    /**
     * Calculate the widest border length of all prefixes of pattern.
     * Initialize array {@code widestBorderLens}
     */
    private void preprocess()
    {
        int j = 0, b = -1;
        // widestBorderLens[0] = -1; // 设置空字符串ε的最长border长度为-1
        widestBorderLens[j] = b;
        while (j < M)
        {
            /*
            String x = pattern.substring(0, j);
            每一轮迭代开始，b = widestBorderLens[j]，j表示长度为i的前缀x的最长border的长度
            
            char a = pattern.chatAt(j);
            case 1: b > 0 && pattern.chatAt(b) == pattern.chatAt(j)
                找到border v，使得va是xa的最长border
                |v|=b，|va|=b+1，即设置 widestBorderLens[++j]=++b
            case 2: b == 0 && pattern.chatAt(0) == pattern.chatAt(j)
                a是xa的最长border，此时v=ε，长度为0
                |v|=0，|va|=|a|=1，即设置 widestBorderLens[++j]=1=++b
            case 3: b == -1
                找不到xa的border，即xa的最长border为ε，长度为0
                设置 widestBorderLens[++j]=0=++b
            */
            while (b >= 0 && pattern.charAt(b) != pattern.charAt(j))
                b = widestBorderLens[b];
            j++; b++;
            widestBorderLens[j] = b;
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
                j = widestBorderLens[j];
            i++; j++;
            if(j == M) return i - M;
        }
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new KMPBorder(pattern).search(text);
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
                j = widestBorderLens[j];
            i++; j++;
            if(j == M)
            {
                occurIndices.enqueue(i - M);
                // 最大限度移动pattern，继续查找text中其余pattern
                j = widestBorderLens[M];
            }
        }
        return occurIndices;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        return new KMPBorder(pattern).searchAll(text);
    }
}
