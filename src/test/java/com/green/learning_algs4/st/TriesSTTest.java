package com.green.learning_algs4.st;

import com.green.learning_algs4.list.XArrayList;
import com.green.learning_algs4.list.XList;
import com.green.learning_algs4.string.Alphabet;
import com.green.learning_algs4.string.OrderedStringST;
import com.green.learning_algs4.string.TernarySearchTriesST;
import com.green.learning_algs4.string.TriesST;
import com.green.learning_algs4.util.ArrayUtils;
import com.green.learning_algs4.util.FileUtils;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.TST;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TriesSTTest
{
    
    @Test
    void testPutGet()
    {
        OrderedStringST<Integer> tries = new TriesST<>(Alphabet.LOWERCASE());
        
        assertTrue(tries.isEmpty());
        
        String[] keys = {
                "",
                "by",
                "sea",
                "sells",
                "she",
                "shells",
                "shore",
                "the",
        };
        
        int[] values = {
                0,
                4,
                6,
                1,
                0,
                3,
                7,
                5,
        };
        
        for (int i = 0; i < keys.length; i++)
        {
            tries.put(keys[i], values[i]);
            assertEquals(values[i], tries.get(keys[i]));
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
        
        tries.remove("");
        assertEquals(keys.length - 1, tries.size());
        assertEquals("", tries.longestCommonPrefix("abc"));
        tries.put("", 11);
        assertEquals(11, tries.get(""));
        
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
            assertFalse(tries.containsKey(keys[i]));
            assertEquals(keys.length - i - 1, tries.size());
        }
        
        assertTrue(tries.isEmpty());
        assertEquals(0, tries.size());
    }
    
    @Test
    void testTST1()
    {
//        OrderedStringST<Integer> tries = new TriesST<>(Alphabet.LOWERCASE());
        OrderedStringST<Integer> tries = new TernarySearchTriesST<>();
        
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
        
        int[] values = {
                4,
                6,
                1,
                0,
                3,
                7,
                5,
        };
        
        for (int i = 0; i < keys.length; i++)
        {
            tries.put(keys[i], values[i]);
            assertEquals(values[i], tries.get(keys[i]));
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
            assertFalse(tries.containsKey(keys[i]));
            assertEquals(keys.length - i - 1, tries.size());
        }
        
        assertTrue(tries.isEmpty());
        assertEquals(0, tries.size());
    }
    
    @Test
    void testPut1()
    {
        TriesST<Integer> tries = new TriesST<>(Alphabet.LOWERCASE());
        
        assertThrows(IllegalArgumentException.class, () -> tries.put("a", null));
        assertThrows(IllegalArgumentException.class, () -> tries.put("123", 1));
    }
    
    //    @Test
    @RepeatedTest(1000)
    void testCRUD() throws FileNotFoundException
    {
//        TriesST<String> tries = new TriesST<>(Alphabet.ASCII());
//        TernarySearchTriesST<String> tries = new TernarySearchTriesST<>();
        OrderedStringST<String> tries = new TernarySearchTriesST<>();
        
        ST<String, String> st = new LinkedHashST<>();
        
        Random rnd = new Random();
        
        XList<String> hosts = new XArrayList<>();
        XList<String> ips = new XArrayList<>();
        
        File ipCsv = FileUtils.getFile("string", "ip.csv");
        Scanner scanner = new Scanner(ipCsv);
        scanner.nextLine();
        while (scanner.hasNext())
        {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            String[] tokens = line.split(",");
            String host = tokens[0], ip = tokens[1];
            hosts.append(host);
            ips.append(ip);
            
            tries.put(host, ip);
            st.put(host, ip);
            assertEquals(st.size(), tries.size());
            
            if (StdRandom.bernoulli(0.2))
            {
                String rndHost = hosts.get(rnd.nextInt(hosts.size()));
                int cnt = 0;
                while (!tries.containsKey(rndHost) && cnt < 10)
                {
                    cnt++;
                    assertFalse(st.containsKey(rndHost));
                    rndHost = hosts.get(rnd.nextInt(hosts.size()));
                    tries.remove(rndHost);
                    st.remove(rndHost);
                    assertEquals(st.size(), tries.size());
                }
                
                tries.remove(rndHost);
                st.remove(rndHost);
                assertEquals(st.size(), tries.size());
            }
            
            assertEquals(st.size(), tries.size());
        }
        
        XList<String> keys = new XArrayList<>(tries.size());
        for (String key : tries.orderKeys())
        {
            assertTrue(st.containsKey(key));
            keys.append(key);
//            System.out.println(key);
        }
        assertTrue(ArrayUtils.isSorted(keys.toArray(new String[0])));

//        System.out.println("\n-------\n");
        
        for (String host : hosts)
        {
            assertEquals(tries.containsKey(host), st.containsKey(host));
            assertEquals(tries.get(host), st.get(host));
            
            String ip = tries.get(host);
//            System.out.println(host + " : " + ip);
        }
        
    }
    
    @Test
    void testTSTDelete()
    {
        // The whole test routine passes, but something is wrong...
        
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        tst.put("b", 2);
        tst.put("c", 3);
        assertEquals(3, tst.size());
        
        /*
        use put method to remove b from the TST, as suggested by the
        api contract "If the value is {@code null}, this effectively
        deletes the key from the symbol table."
         */
        tst.put("b", null);
        
        // expected tst.size() == 2, but was 3
        assertEquals(3, tst.size());
        assertNull(tst.get("b"));
        
        int cnt = 0;
        for (String key : tst.keys())
        {
            cnt++;
            assertNotEquals("b", key);
            System.out.print(key + " ");
        }
        // only get 2 keys, inconsistent with size == 3
        assertEquals(2, cnt);
        assertEquals(3, tst.size());
    }
    
    @Test
    void testTSTDeleteAfterFix()
    {
        // The whole test routine passes
        
        TST<Integer> tst = new TST<>();
        tst.put("a", 1);
        tst.put("b", 2);
        tst.put("c", 3);
        assertEquals(3, tst.size());
        
        /*
        use put method to remove b from the TST, as suggested by the
        api contract "If the value is {@code null}, this effectively
        deletes the key from the symbol table."
         */
        tst.put("b", null);
        
        // expected tst.size() == 2, but was 3
        assertEquals(2, tst.size());
        assertNull(tst.get("b"));
        
        int cnt = 0;
        for (String key : tst.keys())
        {
            cnt++;
            assertNotEquals("b", key);
            System.out.print(key + " ");
        }
        // only get 2 keys, inconsistent with size == 3
        assertEquals(2, cnt);
        assertEquals(2, tst.size());
    }
}