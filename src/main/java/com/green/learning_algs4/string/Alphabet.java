package com.green.learning_algs4.string;

import java.util.*;

/**
 * A restricted alphabet used for applications which
 * only use a limited range of characters rather than
 * the unicode set defined by java char.
 * immutable
 * @see edu.princeton.cs.algs4.Alphabet
 */
public class Alphabet
{
    private final int R; // the size of the Alphabet
    private final char[] chars; // forward index
    private final Map<Character, Integer> char2Index; // reverted index
    
    public Alphabet(String tokens)
    {
        this(tokens.toCharArray());
    }
    
    public Alphabet(char[] tokens)
    {
        if (tokens == null || tokens.length == 0)
            throw new IllegalArgumentException("null or empty tokens");
        
        Map<Character, Integer> invertedIndex = new HashMap<>();
        for (int i = 0; i < tokens.length; )
        {
            char token = tokens[i];
            
            if (invertedIndex.containsKey(token))
//                throw new IllegalArgumentException("duplicate token <" + token + ">: tokens["
//                        + invertedIndex.get(token) + "] and tokens[" + i + "]");
                continue;
            invertedIndex.put(token, i++);
        }
        
        char2Index = Collections.unmodifiableMap(invertedIndex);
        R = char2Index.size();
        chars = new char[R];
        for (Map.Entry<Character, Integer> entry : char2Index.entrySet())
            chars[entry.getValue()] = entry.getKey();
    }
    
    public boolean contains(char c)
    {
        return char2Index.containsKey(c);
    }
    
    private void validateChar(char c)
    {
        if (!contains(c))
            throw new IllegalArgumentException("token <" + c + "> isn't contained in the alphabet");
    }
    
    private void validateIndex(int index)
    {
        if (index < 0 || index >= R)
            throw new IndexOutOfBoundsException("valid index: [0, " + (R - 1) + "];"
                    + " index out of bound: " + index);
    }
    
    public char toChar(int index)
    {
        validateIndex(index);
        return chars[index];
    }
    
    public int toIndex(char c)
    {
        validateChar(c);
        return char2Index.get(c);
    }
    
    public int R()
    {
        return R;
    }
    
    public int lgR()
    {
        return (int) Math.ceil(Math.log(R));
    }
    
    public int[] toIndices(char[] tokens)
    {
        int[] indices = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++)
        {
            char c = tokens[i];
            validateChar(c);
            indices[i] = char2Index.get(c);
        }
        return indices;
    }
    
    public int[] toIndices(String tokens)
    {
        return toIndices(tokens.toCharArray());
    }
    
    public char[] toChars(int[] indices)
    {
        char[] chars = new char[indices.length];
        for (int i = 0; i < indices.length; i++)
        {
            int index = indices[i];
            validateIndex(index);
            chars[i] = this.chars[i];
        }
        
        return chars;
    }
    
    public String toString(int[] indices)
    {
        return new String(toChars(indices));
    }
    
    public char[] getChars()
    {
        return Arrays.copyOf(chars, R);
    }
    
    private static Alphabet ASCII;
    private static Alphabet EXTENDED_ASCII;
    private static Alphabet UNICODE;
    private static Alphabet LOWERCASE;
    private static Alphabet UPPERCASE;
    
    public static Alphabet ASCII()
    {
        if(ASCII == null)
        {
            int R = 2 << 7;
            char[] tokens = new char[R];
            for (int i = 0; i < R; i++)
                tokens[i] = (char) i;
            ASCII = new Alphabet(tokens);
        }
        return ASCII;
    }
    
    public static Alphabet EXTENDED_ASCII()
    {
        if(EXTENDED_ASCII == null)
        {
            int R = 2 << 8;
            char[] tokens = new char[R];
            for (int i = 0; i < R; i++)
                tokens[i] = (char) i;
            EXTENDED_ASCII = new Alphabet(tokens);
        }
        return EXTENDED_ASCII;
    }
    
    public static Alphabet UNICODE()
    {
        if(UNICODE == null)
        {
            int R = Character.MAX_VALUE + 1;
            char[] tokens = new char[R];
            for (int i = 0; i < R; i++)
                tokens[i] = (char) i;
            UNICODE = new Alphabet(tokens);
        }
        return UNICODE;
    }
    
    public static Alphabet LOWERCASE()
    {
        if(LOWERCASE == null)
        {
            int R = 26;
            char[] tokens = new char[R];
            for (int i = 0; i < R; i++)
                tokens[i] = (char) ('a' + i);
            LOWERCASE = new Alphabet(tokens);
        }
        return LOWERCASE;
    }
    
    public static Alphabet UPPERCASE()
    {
        if(UPPERCASE == null)
        {
            int R = 26;
            char[] tokens = new char[R];
            for (int i = 0; i < R; i++)
                tokens[i] = (char) ('A' + i);
            UPPERCASE = new Alphabet(tokens);
        }
        return UPPERCASE;
    }
    
    public char sample()
    {
        int rndIndex = (int)(Math.random() * R);
        return chars[rndIndex];
    }
    
    /**
     * @param N the sample size
     * @return an array of N random characters
     */
    public char[] samples(int N)
    {
        char[] samples = new char[N];
        Random rnd = new Random();
        for(int i = 0; i < N; i++)
            samples[i] = chars[rnd.nextInt(R)];
        
        return samples;
    }
}