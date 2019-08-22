package com.green.learning.algs4.string.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KMPBorderTest
{
    
    @Test
    void testSearchAll1()
    {
        String text = "Now is the time for all people to come to the aid of their party. Now is the time for all good people to" +
                "come to the aid of their party. Now is the time for many good people to come to the aid of their party." +
                "Now is the time for all good people to come to the aid of their party. Now is the time for a lot of good" +
                "people to come to the aid of their party. Now is the time for all of the good people to come to the aid of" +
                "their party. Now is the time for all good people to come to the aid of their party. Now is the time for" +
                "each good person to come to the aid of their party. Now is the time for all good people to come to the aid" +
                "of their party. Now is the time for all good Republicans to come to the aid of their party. Now is the" +
                "time for all good people to come to the aid of their party. Now is the time for many or all good people to" +
                "come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now" +
                "is the time for all good Democrats to come to the aid of their party. Now is the time for all people to" +
                "come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now" +
                "is the time for many good people to come to the aid of their party. Now is the time for all good people to" +
                "come to the aid of their party. Now is the time for a lot of good people to come to the aid of their" +
                "party. Now is the time for all of the good people to come to the aid of their party. Now is the time for" +
                "all good people to come to the aid of their attack at dawn party. Now is the time for each person to come" +
                "to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is" +
                "the time for all good Republicans to come to the aid of their party. Now is the time for all good people" +
                "to come to the aid of their party. Now is the time for many or all good people to come to the aid of their" +
                "party. Now is the time for all good people to come to the aid of their party. Now is the time for all good" +
                "Democrats to come to the aid of their party.";
    
        String[] patterns = {
                "attack at dawn",
                "party",
                "all",
                "to the aid of",
                "Democrats to come",
                "Democrats",
                "Democratstocome",
                "Republican",
                "all good people"
        };
        
        for(String pattern: patterns)
        {
            System.out.printf("pattern: %s\n", pattern);
            int lastOccurIndex = -1;
            Iterable<Integer> iterable = KMPBorder.searchAll(text, pattern);
            for(int index: iterable)
            {
                System.out.printf("-> %d\n", index);
                assertEquals(text.indexOf(pattern, lastOccurIndex + 1), index);
                lastOccurIndex = index;
            }
            assertEquals(text.indexOf(pattern, lastOccurIndex + 1), -1);
            System.out.println();
            
            assertIterableEquals(
                    iterable,
                    KMP.searchAll(text,pattern));
            
            assertIterableEquals(iterable, BoyerMooreX.searchAll(text, pattern));
        }
    }
}