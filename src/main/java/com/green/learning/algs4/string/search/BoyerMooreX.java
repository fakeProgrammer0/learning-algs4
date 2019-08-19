package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedList;
import com.green.learning.algs4.string.Alphabet;

import java.util.Arrays;
import java.util.Iterator;

public class BoyerMooreX
{
    private final String pattern;
    private final Alphabet alphabet;
    private final int[] rightmostIndices;
    private final int[] goodSuffixShifts;
    
    private int[] getRightmostIndices()
    {
        int[] badSymbolShifts = new int[alphabet.radix()];
        final int M = pattern.length();
        for (int j = 0; j < M; j++)
            badSymbolShifts[alphabet.toIndex(pattern.charAt(j))] = j;
        return badSymbolShifts;
    }
    
    /**
     * a brute-force method calculate good suffix shifts table
     * @param pattern the pattern
     * @return the good suffix shifts table
     */
    public static int[] buildGoodSuffixShifts(String pattern)
    {
        final int M = pattern.length();
        int[] goodSuffixShifts = new int[M];
        Arrays.fill(goodSuffixShifts, M);
        
        int rightEnd = M - 1;
        goodSuffixShifts[rightEnd] = 1;
        
        XLinkedList<Integer> leftEnds = new XLinkedList<>();
        char endChar = pattern.charAt(rightEnd);
        for(int j = 0; j < rightEnd; j++)
            if(pattern.charAt(j) == endChar)
                leftEnds.addFirst(j);
        if(leftEnds.isEmpty())
        {
            // endChar is unique in pattern
            // set all goodSuffixSifts[i] to M
            goodSuffixShifts[rightEnd] = M;
            return goodSuffixShifts;
        }
        
        for (int k = M - 2; k >= 0; k--)
        {
            int rightStart = k + 1;
            Iterator<Integer> leftEndIterator = leftEnds.iterator();
            while (leftEndIterator.hasNext())
            {
                int leftEnd = leftEndIterator.next();
                int le, r;
                for (le = leftEnd, r = rightEnd;
                     le >= 0 && r >= rightStart
                             && pattern.charAt(le) == pattern.charAt(r);
                     le--, r--)
                    ;
                if (le < 0 || r < rightStart)
                {
                    goodSuffixShifts[k] = rightEnd - leftEnd;
                    break;
                }
            }
            // if goodSuffixShifts[k] is unset, it remains with value M
        }
        return goodSuffixShifts;
    }
    
    public BoyerMooreX(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        alphabet = new Alphabet(pattern);
        rightmostIndices = getRightmostIndices();
        goodSuffixShifts = buildGoodSuffixShifts(pattern);
    }
    
    private int badSymbolShift(int j, char c)
    {
        int rightmost = alphabet.contains(c) ?
                rightmostIndices[alphabet.toIndex(c)] : -1;
        return Math.max(1, j - rightmost);
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text);
        
        final int M = pattern.length(), N = text.length();
        int d1, d2;
        for (int i = 0, shift = 0; i <= N - M; i += shift)
        {
            int j = M - 1;
            for (; pattern.charAt(j) == text.charAt(i + j); j--)
                if (j == 0) return i;
            d1 = badSymbolShift(j, text.charAt(i + j));
            d2 = goodSuffixShifts[j];
            shift = Math.max(d1, d2);
        }
        
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new BoyerMooreX(pattern).search(text);
    }
}
