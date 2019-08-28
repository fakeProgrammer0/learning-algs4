package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;
import com.green.learning.algs4.string.Alphabet;

public class BoyerMoore
{
    private final String pattern;
    private final int M;
    private final Alphabet alphabet;
    private final int[] badSymbolShifts;
    private final int[] goodSuffixShifts;
    private int shiftAfterMatch;
    
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
     * suppose the current text pointer's position is {@code i}
     * and current pattern pointer's position is {@code j}.
     * <p>
     * Notice that this method may return wrong shift value if the last index of
     * the mismatch character in the pattern is larger than {@code j}. However,
     * in this case, the good suffix shift will make sure the maximum shift value
     * is always correct.
     *
     * @param c mismatch character
     * @return bad symbol shift value of the text point
     */
    private int badSymbolShift(char c)
    {
        return alphabet.contains(c) ? badSymbolShifts[alphabet.toIndex(c)] : M;
    }
    
    /**
     * @return the good suffix shifts table
     */
    private int[] getGoodSuffixShifts()
    {
        int[] s = new int[M];
    
        /*
        f[i] means the right starting index of the widest border of pattern[i ... M-1]
         */
        int[] f = new int[M + 1];
        
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
            i--;
            j--;
            f[i] = j;
        }
        
        // case 2
        shiftAfterMatch = f[0];
        j = f[0];
        for (i = 0; i < M; i++)
        {
            if (i == j) j = f[j];
            if (s[i] == 0) s[i] = j;
        }
        
        // search phase: move text point back to M - 1
        for (int x = 0; x < M; x++)
            s[x] += M - 1 - x;
        
        return s;
    }
    
    public BoyerMoore(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        this.pattern = pattern;
        this.M = pattern.length();
        alphabet = new Alphabet(pattern);
        badSymbolShifts = getBadSymbolShifts();
        goodSuffixShifts = getGoodSuffixShifts();
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        if (N < M) return -1;
        
        int d1, d2;
        for (int i = M - 1, j; i < N; )
        {
            for (j = M - 1; pattern.charAt(j) == text.charAt(i); i--, j--)
                if (j == 0) return i;
//            d1 = badSymbolShift(j, text.charAt(i));
            d1 = badSymbolShift(text.charAt(i));
            d2 = goodSuffixShifts[j];
            i += Math.max(d1, d2);
        }
        
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new BoyerMoore(pattern).search(text);
    }
    
    public Iterable<Integer> searchAll(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        XQueue<Integer> occurIndices = new XLinkedQueue<>();
        if (N < M) return occurIndices;
        
        for (int i = M - 1, j; i < N;)
        {
            for (j = M - 1; j >= 0 && text.charAt(i) == pattern.charAt(j); j--, i--) ;
            
            if (j < 0)
            {
                occurIndices.enqueue(i + 1);
                i += 1 + shiftAfterMatch;
            } else
                i += Math.max(badSymbolShift(text.charAt(i)), goodSuffixShifts[j]);
        }
        
        return occurIndices;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        return new BoyerMoore(pattern).searchAll(text);
    }
}
