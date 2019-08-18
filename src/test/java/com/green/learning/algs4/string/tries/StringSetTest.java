package com.green.learning.algs4.string.tries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringSetTest
{
    @Test
    void testTST()
    {
        TernarySearchTriesSet tries = new TernarySearchTriesSet();
        assertTrue(tries.isEmpty());
        
        String[] keys = {
                "by",
                "sea",
                "sells",
                "she",
                "shells",
                "shore",
                "the",
        };
        
        for (int i = 0; i < keys.length; i++)
        {
            tries.add(keys[i]);
            assertTrue(tries.contains(keys[i]));
            assertEquals(i + 1, tries.size());
        }
        
        System.out.println("Wild Match");
        String[] patterns = {".he", ".", "s.", "sh.", "b.", ".y", "the", "..."};
        for (String pattern : patterns)
        {
            System.out.print("match pattern <" + pattern + ">: ");
            for (String str : tries.keysMatch(pattern))
                System.out.print(str + " ");
            System.out.println();
        }
        System.out.println();
        
        assertEquals("b", tries.longestCommonPrefix("bat"));
        assertEquals("by", tries.longestCommonPrefix("bye"));
        assertEquals("sea", tries.longestCommonPrefix("seam"));
        assertEquals("she", tries.longestCommonPrefix("she"));
        assertNotEquals("she", tries.longestCommonPrefix("shell"));
        assertEquals("shell", tries.longestCommonPrefix("shell"));
        assertEquals("", tries.longestCommonPrefix("abc"));
        
        assertEquals("", tries.longestPrefixKeyMatch("bat"));
        assertEquals("by", tries.longestPrefixKeyMatch("bye"));
        assertEquals("sea", tries.longestPrefixKeyMatch("seam"));
        assertEquals("she", tries.longestPrefixKeyMatch("she"));
        assertEquals("she", tries.longestPrefixKeyMatch("shell"));
        assertNotEquals("shell", tries.longestPrefixKeyMatch("shell"));
        assertEquals("", tries.longestPrefixKeyMatch("abc"));
        
        assertEquals(keys.length, tries.size());
        
        tries.remove("galjglkdajdgklajs");
        assertEquals(keys.length, tries.size());
        
        assertEquals("", tries.longestCommonPrefix("abc"));
        
        System.out.println("Prefix Match");
        String[] prefixs = {"s", "sh", "z", "a", "b", "h", ""};
        for (String prefix : prefixs)
        {
            System.out.printf("keys with prefix <%s>: ", prefix);
            for (String k : tries.keysWithPrefix(prefix))
                System.out.printf("<%s> ", k);
            System.out.println();
        }
        
        for (int i = 0; i < keys.length; i++)
        {
            tries.remove(keys[i]);
            assertFalse(tries.contains(keys[i]));
            assertEquals(keys.length - i - 1, tries.size());
        }
        
        assertTrue(tries.isEmpty());
        assertEquals(0, tries.size());
    }
}
