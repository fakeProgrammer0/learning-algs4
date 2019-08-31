package com.green.learning.algs4.string.tries;

import com.green.learning.algs4.set.XLinkedHashSet;
import com.green.learning.algs4.set.XSet;
import com.green.learning.algs4.string.Alphabet;
import com.green.learning.algs4.util.IterableUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TriesTest
{
    private static TrieSet initTriesSet(Alphabet alphabet, String[] strings)
    {
        TrieSet triesSet = new TrieSet(alphabet);
        for (int i = 0; i < strings.length; i++)
        {
            triesSet.add(strings[i]);
            assertTrue(triesSet.contains(strings[i]));
            assertEquals(i + 1, triesSet.size());
        }
        return triesSet;
    }
    
    private static TernarySearchTrieSet initTSTSet(String[] strings)
    {
        TernarySearchTrieSet triesSet = new TernarySearchTrieSet();
        for (int i = 0; i < strings.length; i++)
        {
            triesSet.add(strings[i]);
            assertTrue(triesSet.contains(strings[i]));
            assertEquals(i + 1, triesSet.size());
        }
        return triesSet;
    }
    
    private static <V> TrieST<V> initTriesST(Alphabet alphabet, String[] keys, V[] values)
    {
        TrieST<V> triesST = new TrieST<>(alphabet);
        for (int i = 0; i < keys.length; i++)
        {
            triesST.put(keys[i], values[i]);
            assertTrue(triesST.contains(keys[i]));
            assertEquals(i + 1, triesST.size());
        }
        return triesST;
    }
    
    private static <V> TernarySearchTriesST<V> initTSTST(String[] keys, V[] values)
    {
        TernarySearchTriesST<V> triesST = new TernarySearchTriesST<>();
        for (int i = 0; i < keys.length; i++)
        {
            triesST.put(keys[i], values[i]);
            assertTrue(triesST.containsKey(keys[i]));
            assertEquals(i + 1, triesST.size());
        }
        return triesST;
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
        
//        TriesSet tries = initTriesSet(Alphabet.LOWERCASE(), keys);
//        TriesST<String> tries = initTriesST(Alphabet.LOWERCASE(), keys, keys);
        
//        TernarySearchTriesSet tries = initTSTSet(keys);
        TernarySearchTriesST<String> tries = initTSTST(keys, keys);
        
        for (int i = 0; i < keys.length; i++)
        {
            System.out.printf("remove <%s>\n", keys[i]);
//            assertTrue(tries.remove(keys[i]));
            tries.remove(keys[i]);
            assertFalse(tries.contains(keys[i]));
            assertEquals(keys.length - 1 - i, tries.size());
        }
        
        assertTrue(tries.isEmpty());
        assertEquals(0, tries.size());
    }
    
    private static void checkWildMatch(OrderedStringCollection tries, String pattern, String... expected)
    {
        checkIterableSetEquals(tries.keysMatch(pattern), expected);
    }
    
    private static void checkPrefixMatch(OrderedStringCollection tries, String prefix, String... expected)
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
        
//        AbstractTries tries = initTriesSet(Alphabet.LOWERCASE(), keys);
//        OrderedStringCollection tries = initTriesST(Alphabet.LOWERCASE(), keys, keys);
        
//        OrderedStringCollection tries = initTSTSet(keys);
        OrderedStringCollection tries = initTSTST(keys, keys);
        
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
