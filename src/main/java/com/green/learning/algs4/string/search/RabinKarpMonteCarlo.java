package com.green.learning.algs4.string.search;

import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;

import java.math.BigInteger;
import java.util.Random;

/**
 * Monte Carlo version of Rabin Karp Algorithm
 *
 * @see edu.princeton.cs.algs4.RabinKarp
 */
public class RabinKarpMonteCarlo
{
    private final int M;
    private final int R;
    
    private static final int HASH_COUNT = 3;
    private final long[] Qs;
    
    /**
     * R ^ (M - 1) % Q
     */
    private final long[] RM_1s;
    
    private final long[] patternHashs;
    
    public RabinKarpMonteCarlo(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        
        M = pattern.length();
        R = Character.MAX_VALUE + 1;
        
        Qs = new long[HASH_COUNT];
        XSet<Long> QSet = new XLinkedHashSet<>(HASH_COUNT);
        for (int x = 0; x < HASH_COUNT; x++)
        {
            long q;
            do
            {
                q = longRndPrime();
            } while (QSet.contains(q));
            QSet.add(q);
            Qs[x] = q;
        }
        
        patternHashs = new long[HASH_COUNT];
        for (int x = 0; x < HASH_COUNT; x++)
            patternHashs[x] = hash(pattern, M, Qs[x]);
        
        // precompute R^(m-1) % Q
        this.RM_1s = new long[HASH_COUNT];
        for (int x = 0; x < HASH_COUNT; x++)
        {
            long RM = 1;
            for (int i = 1; i <= M - 1; i++)
                RM = (RM * R) % Qs[x];
            this.RM_1s[x] = RM;
        }
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
    private long hash(String key, int end, long Q)
    {
        long hash = 0;
        for (int i = 0; i < end; i++)
            hash = (hash * R + key.charAt(i)) % Q;
        return hash;
    }
    
    private boolean checkEqual(long[] textHashs)
    {
        for (int x = 0; x < HASH_COUNT; x++)
            if (patternHashs[x] != textHashs[x])
                return false;
        return true;
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        if (N < M) return -1;
        
        long[] textHashs = new long[HASH_COUNT];
        for (int x = 0; x < HASH_COUNT; x++)
            textHashs[x] = hash(text, M, Qs[x]);
        if (checkEqual(textHashs)) return 0;
    
        for (int i = 1; i <= N - M; i++)
        {
            for (int x = 0; x < HASH_COUNT; x++)
            {
                long Q = Qs[x];
                // remove leading digit
                textHashs[x] = (textHashs[x] + Q - text.charAt(i - 1) * RM_1s[x] % Q) % Q;
                // add trailing digit
                textHashs[x] = (textHashs[x] * R + text.charAt(i + M - 1)) % Q;
            }
        
            if (checkEqual(textHashs))
                return i;
        }
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new RabinKarpMonteCarlo(pattern).search(text);
    }
}
