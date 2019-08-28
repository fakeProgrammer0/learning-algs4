package com.green.learning.algs4.string.search;

import com.green.learning.algs4.list.XLinkedQueue;
import com.green.learning.algs4.list.XQueue;

import java.math.BigInteger;
import java.util.Random;

/**
 * Las Vegas version of Rabin Karp Algorithm
 * @see edu.princeton.cs.algs4.RabinKarp
 */
public class RabinKarpLasVegas
{
    private final int M;
    private final int R;
    private final long Q;
    
    /**
     * R ^ (M - 1) % Q
     */
    private final long RM_1;
    
    private final String pattern;
    private final long patternHash;
    
    public RabinKarpLasVegas(String pattern)
    {
        SubstringSearchs.checkPattern(pattern);
        
        M = pattern.length();
        R = Character.MAX_VALUE + 1;
        Q = longRndPrime();
        
//        R = 256;
//        Q = 1245128771;
        
        this.pattern = pattern;
        patternHash = hash(pattern, M);
        
        // precompute R^(m-1) % Q
        long RM = 1;
        for (int i = 1; i <= M - 1; i++)
            RM = (RM * R) % Q;
        this.RM_1 = RM;
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
            hash = (hash * R + key.charAt(i)) % Q;
        return hash;
    }
    
    private boolean checkEqual(String text, int i)
    {
        assert i + M <= text.length();
        for (int j = 0; j < M; j++, i++)
            if (pattern.charAt(j) != text.charAt(i))
                return false;
        return true;
    }
    
    public int search(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        if (N < M) return -1;
        
        long textHash = hash(text, M);
        if (textHash == patternHash && checkEqual(text, 0)) return 0;
        for (int i = 1; i <= N - M; i++)
        {
            // remove leading digit
            textHash = (textHash + Q - text.charAt(i - 1) * RM_1 % Q) % Q;
            // add trailing digit
            textHash = (textHash * R + text.charAt(i + M - 1)) % Q;
            
            if (textHash == patternHash && checkEqual(text, i))
                return i;
        }
        return -1;
    }
    
    public static int search(String text, String pattern)
    {
        return new RabinKarpLasVegas(pattern).search(text);
    }
    
    public Iterable<Integer> searchAll(String text)
    {
        SubstringSearchs.checkText(text);
        final int N = text.length();
        XQueue<Integer> queue = new XLinkedQueue<>();
        if (N < M) return queue;
        
        long textHash = hash(text, M);
        if (textHash == patternHash && checkEqual(text, 0))
            queue.enqueue(0);
        for (int i = 1; i <= N - M; i++)
        {
            // remove leading digit
            textHash = (textHash + Q - text.charAt(i - 1) * RM_1 % Q) % Q;
            // add trailing digit
            textHash = (textHash * R + text.charAt(i + M - 1)) % Q;
            
            if (textHash == patternHash && checkEqual(text, i))
                queue.enqueue(i);
        }
        return queue;
    }
    
    public static Iterable<Integer> searchAll(String text, String pattern)
    {
        return new RabinKarpLasVegas(pattern).searchAll(text);
    }
}
