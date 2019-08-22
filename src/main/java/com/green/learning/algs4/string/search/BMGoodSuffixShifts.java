package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedList;

import java.util.Arrays;
import java.util.Iterator;

class BMGoodSuffixShifts
{
    static int[] bruteForce(String pattern)
    {
        final int M = pattern.length();
        int[] shifts = new int[M];
        Arrays.fill(shifts, M);
        
        final int rightEnd = M - 1;
        shifts[rightEnd] = M;
        
        XLinkedList<Integer> leftEnds = new XLinkedList<>();
        char endChar = pattern.charAt(rightEnd);
        for (int j = 0; j < rightEnd; j++)
            if (pattern.charAt(j) == endChar)
                leftEnds.addFirst(j);
            else shifts[rightEnd] = M - 1 - j;
        
        if (leftEnds.isEmpty())
        {
            // endChar is unique in pattern
            // 根据good suffix的定义，设置 shifts[rightEnd]=1
            // 虽然从bad suffix的角度来讲，最大限度的移动是 M
            shifts[rightEnd] = 1;
            return shifts;
        }
        
        for (int k = M - 2; k >= 0; k--)
        {
            int rightStart = k + 1;
            for (int leftEnd : leftEnds)
            {
                int le, r;
                for (le = leftEnd - 1, r = rightEnd - 1;
                     le >= 0 && r >= rightStart
                             && pattern.charAt(le) == pattern.charAt(r);
                     le--, r--)
                    ;
                if (le < 0
                        || r < rightStart && pattern.charAt(le) != pattern.charAt(r))
                {
                    shifts[k] = rightEnd - leftEnd;
                    break;
                }
            }
            // if shifts[k] is unset, it remains with value M
        }
        return shifts;
    }
    
    static int[] border(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        final int M = pattern.length();
        
        int[] f = new int[M + 1];
        int[] s = new int[M];
        
        // case 1
        int i = M, j = M + 1;
        f[i] = j;
        while (i > 0)
        {
            while (j <= M && pattern.charAt(i - 1) != pattern.charAt(j - 1))
            {
                if (s[j - 1] == 0) s[j - 1] = j - i;
                j = f[j];
            }
            f[--i] = --j;
        }
        
        // case 2
        j = f[0];
        for (i = 1; i <= M; i++)
        {
            if (s[i - 1] == 0) s[i - 1] = j;
            if (i == j) j = f[j];
        }
        
        return s;
    }
    
    static int[] border0(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        final int M = pattern.length();
        
        int[] f = new int[M + 1];
        int[] s = new int[M + 1];
        
        // case 1
        int i = M, j = M + 1;
        f[i] = j;
        while (i > 0)
        {
            while (j <= M && pattern.charAt(i - 1) != pattern.charAt(j - 1))
            {
                if (s[j] == 0) s[j] = j - i;
                j = f[j];
            }
            f[--i] = --j;
        }
        
        // case 2
        j = f[0];
        for (i = 0; i <= M; i++)
        {
            if (s[i] == 0) s[i] = j;
            if (i == j) j = f[j];
        }
        
        int[] shifts = new int[M];
        System.arraycopy(s, 1, shifts, 0, M);
        return shifts;
    }
    
    /**
     * <a href="https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_string-search_algorithm"></a>
     */
    static int[] wiki(String pattern)
    {
        final int M = pattern.length();
        int[] shifts = new int[M];
        int lastPrefixPos = M;
        shifts[M - 1] = M;
        for (int j = M - 2; j >= 0; j--)
        {
            if (isPrefix(pattern, j + 1))
                lastPrefixPos = j + 1;
            shifts[j] = lastPrefixPos;
        }
        
        for (int k = 0; k < M - 1; k++)
        {
            int suffixLen = suffixLength(pattern, k);
            shifts[M - 1 - suffixLen] = M - 1 - k;
        }
        
        return shifts;
    }
    
    private static boolean isPrefix(String pattern, int rightStart)
    {
        final int M = pattern.length();
        assert rightStart >= 0 && rightStart < M;
        for (int le = M - 1 - rightStart, r = M - 1; le >= 0; le--, r--)
            if (pattern.charAt(le) != pattern.charAt(r))
                return false;
        
        return true;
    }
    
    /**
     * @return the maximum length of the substring ends at leftEnd and is a suffix.
     */
    private static int suffixLength(String pattern, int leftEnd)
    {
        int len = 0;
        for (int le = leftEnd, r = pattern.length() - 1;
             le >= 0 && pattern.charAt(le) == pattern.charAt(r);
             le--, r--)
            len++;
        return len;
    }
}
