package com.green.learning.algs4.string.search;

import com.green.learning.algs4.string.StringUtils;

import java.math.BigInteger;
import java.util.Random;

/**
 * @see edu.princeton.cs.algs4.RabinKarp
 */
public class RabinKarp
{
    private final int M;
    private final int R;
    private final long Q;
    private final long RM; // R ^ (M - 1) % Q
    
    private final String pattern;
    private final long patternHash;
    
    public RabinKarp(String pattern)
    {
        if (StringUtils.isEmpty(pattern))
            throw new IllegalArgumentException("pattern should not be null or empty");
        
        M = pattern.length();
        R = Character.MAX_VALUE + 1;
        Q = longRndPrime();
        
        this.pattern = pattern;
        patternHash = hash(pattern, M);
        
        long RM = 1;
        for (int i = 1; i <= M - 1; i++)
            RM = (RM * R) % Q;
        this.RM = RM;
    }
    
    private static long longRndPrime()
    {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }
    
    /**
     * @param key the string to hash
     * @param end the end index, exclusive
     * @return the hash value of key[0 : end - 1]
     */
    private long hash(String key, int end)
    {
        long hash = 0;
        for (int i = 0; i < end; i++)
            hash += (hash * R + key.charAt(i)) % Q;
        return hash;
    }
    
    private boolean checkEqual(String text, int i)
    {
        for (int j = 0; j < M; j++, i++)
            if (pattern.charAt(j) != text.charAt(i))
                return false;
        return true;
    }
    
    public int search(String text)
    {
        if (StringUtils.isEmpty(text))
            throw new IllegalArgumentException("text should not be null or empty");
        final int N = text.length();
        if (N < M) return -1;
        
        long textHash = hash(text, M);
        if (textHash == patternHash && checkEqual(text, 0)) return 0;
        for (int i = 1; i <= N - M; i++)
        {
            textHash = textHash * R - text.charAt(i - 1) * RM + text.charAt(i);
            if (textHash == patternHash && checkEqual(text, i))
                return i;
        }
        return -1;
    }
}
