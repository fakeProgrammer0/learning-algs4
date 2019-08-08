package com.green.learning_algs4.string;

import com.green.learning_algs4.set.XLinkedHashSet;
import com.green.learning_algs4.set.XSet;
import com.green.learning_algs4.util.IterableUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TriesTest
{
    private TriesSet initTriesSet(String[] strings)
    {
        TriesSet triesSet = new TriesSet(Alphabet.LOWERCASE());
        for (int i = 0; i < strings.length; i++)
        {
            triesSet.add(strings[i]);
            assertTrue(triesSet.contains(strings[i]));
            assertEquals(i + 1, triesSet.size());
        }
        return triesSet;
    }
    
    @Test
    void testNodeSize()
    {
        String[] keys = {
                "by",
                "sea",
                "sells",
                "she",
                "shells",
                "shore",
                "the",
        };
        
        AbstractTries tries = initTriesSet(keys);
        for (int i = 0; i < keys.length; i++)
        {
            System.out.printf("remove <%s>\n", keys[i]);
            assertTrue(tries.remove(keys[i]));
            assertFalse(tries.contains(keys[i]));
            assertEquals(keys.length - 1 - i, tries.size());
        }
        
        assertTrue(tries.isEmpty());
        assertEquals(0, tries.size());
    }
    
    private static void checkWildMatch(AbstractTries tries, String pattern, String... expected)
    {
        checkIterableSetEquals(tries.keysMatch(pattern), expected);
    }
    
    private static void checkPrefixMatch(AbstractTries tries, String prefix, String... expected)
    {
        checkIterableSetEquals(tries.keysWithPrefix(prefix), expected);
    }
    
    private static void checkIterableSetEquals(Iterable<String> actual, String... expected)
    {
        XSet<String> set = new XLinkedHashSet<>();
        for (String str : expected)
            set.add(str);
        assertEquals(new XLinkedHashSet<>(actual), set);
    }
    
    @Test
    void testOrderedOps()
    {
        String[] keys = {
                "by",
                "sea",
                "sells",
                "she",
                "shells",
                "shore",
                "the",
        };
        
        AbstractTries tries = initTriesSet(keys);
        
        assertTrue(IterableUtils.isSorted(tries.orderKeys()));
        
        System.out.println("Wild Match");
        checkWildMatch(tries, ".he", "she", "the");
        checkWildMatch(tries, ".");
        checkWildMatch(tries, "s.");
        checkWildMatch(tries, "sh.", "she");
        checkWildMatch(tries, "b.", "by");
        checkWildMatch(tries, ".y", "by");
        checkWildMatch(tries, "the", "the");
        checkWildMatch(tries, "...",
                "sea", "she", "the");
        
        assertIterableEquals(tries.keysWithPrefix(""), tries.orderKeys());
        checkPrefixMatch(tries, "",
                "by", "sea", "sells", "she", "shells", "shore", "the");
        checkPrefixMatch(tries, "s",
                "sea", "sells", "she", "shells", "shore");
        checkPrefixMatch(tries, "sh",
                "she", "shells", "shore");
        checkPrefixMatch(tries, "b", "by");
        checkPrefixMatch(tries, "z");
        checkPrefixMatch(tries, "a");
        checkPrefixMatch(tries, "h");
        checkPrefixMatch(tries, "she",
                "she", "shells");
        
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
        assertEquals("", tries.longestCommonPrefix("abc"));
        
        
    }
}
