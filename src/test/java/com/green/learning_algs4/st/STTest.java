package com.green.learning_algs4.st;

import com.green.learning_algs4.set.XSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Iterator;

public class STTest
{
    public static int charCount(String str, char c)
    {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == c)
                count++;
        return count;
    }
    
    @ParameterizedTest(name = "{displayName}/{index}")
    @ValueSource(strings = {"The model. We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)",
            "Percolation. Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations."})
    public void wordCountTest(String word, TestInfo testInfo)
    {
        System.out.println(testInfo.getDisplayName());
        ST<Character, Integer> word2Count = new LinkedHashST<>();
        Assertions.assertTrue(word2Count.isEmpty());
        Assertions.assertEquals(0, word2Count.size());
        int tokenCount = 0;
        for (int i = 0; i < word.length(); i++)
        {
            char token = word.charAt(i);
            if (!word2Count.containsKey(token))
            {
                word2Count.put(token, 1);
                tokenCount++;
            }
            else word2Count.put(token, word2Count.get(token) + 1);
        }
    
        XSet<Character> tokens = word2Count.keys();
        Assertions.assertEquals(tokenCount, word2Count.size());
        Assertions.assertEquals(tokenCount, tokens.size());
        
        for (ST.Entry<Character, Integer> wordCountPair : word2Count)
        {
            Assertions.assertTrue(wordCountPair.getValue() >= 1);
            System.out.println(wordCountPair.getKey() + " : " + wordCountPair.getValue());
            Assertions.assertEquals(charCount(word, wordCountPair.getKey()), wordCountPair.getValue());
        }
    
        for(char c: tokens)
        {
            if(!('a' <= c && c <= 'z'|| 'A' <= c && c <= 'Z' ))
            {
                word2Count.remove(c);
                Assertions.assertFalse(word2Count.containsKey(c));
            }
        }

        tokens = word2Count.keys();
        for(char c: tokens)
        {
            Assertions.assertTrue('a' <= c && c <= 'z'|| 'A' <= c && c <= 'Z' );
        }
        System.out.println();
        System.out.println("keys: " + tokens);
        System.out.println();
        System.out.println(word2Count);
        
        word2Count.clear();
        Assertions.assertTrue(word2Count.isEmpty());
        
        System.out.println();
    }
    
    
}
