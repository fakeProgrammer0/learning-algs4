package com.green.learning.algs4.string.search;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SubstringSearchTest
{
    
    
    @Test
    void test1()
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
                "Democrats to come",
                "Democrats",
                "Democratstocome",
                "Republican",
                "all good people"
        };
        
        for (String pattern : patterns)
        {
            System.out.printf("pattern: %s\n", pattern);
            int index = text.indexOf(pattern);
            
            assertEquals(index, BruteForceSubstringSearch.search(text,pattern));
            assertEquals(index, BruteForceSubstringSearchX.search(text,pattern));
            
            assertEquals(index, KMP.search(text,pattern));
            assertEquals(index, KMPBorder.search(text,pattern));
            assertEquals(index, KMPDFA.search(text,pattern));
            
            assertEquals(index, BoyerMooreX.search(text,pattern));
            assertEquals(index, BoyerMoore.search(text,pattern));
            assertEquals(index, BoyerMooreGoodSuffixShift.search(text,pattern));
            assertEquals(index, BoyerMooreBadSymbolShift.search(text,pattern));
            
            assertEquals(index, Horspool.search(text,pattern));
            
//            assertEquals(index, new edu.princeton.cs.algs4.RabinKarp(pattern).search(text));
            assertEquals(index, RabinKarpLasVegas.search(text,pattern));
            assertEquals(index, RabinKarpMonteCarlo.search(text,pattern));
            
            System.out.printf("index: %d\n\n", index);
        }
    }
    
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
            
            assertIterableEquals(iterable, BruteForceSubstringSearch.searchAll(text,pattern));
            assertIterableEquals(iterable, BruteForceSubstringSearchX.searchAll(text,pattern));
            
            assertIterableEquals(iterable, KMP.searchAll(text,pattern));
            assertIterableEquals(iterable, KMPDFA.searchAll(text,pattern));
            
            assertIterableEquals(iterable, BoyerMoore.searchAll(text, pattern));
            assertIterableEquals(iterable, BoyerMooreX.searchAll(text, pattern));
            assertIterableEquals(iterable, BoyerMooreGoodSuffixShift.searchAll(text, pattern));
            assertIterableEquals(iterable, BoyerMooreBadSymbolShift.searchAll(text, pattern));
            
            assertIterableEquals(iterable, Horspool.searchAll(text, pattern));
            
            assertIterableEquals(iterable,RabinKarpLasVegas.searchAll(text,pattern));
            assertIterableEquals(iterable,RabinKarpMonteCarlo.searchAll(text,pattern));
        }
    }
    
    public static void main(String[] args)
    {
        String text = "Now is the time for all people to come to the aid of their party. Now is the time for all good people to\n" +
                "come to the aid of their party. Now is the time for many good people to come to the aid of their party.\n" +
                "Now is the time for all good people to come to the aid of their party. Now is the time for a lot of good\n" +
                "people to come to the aid of their party. Now is the time for all of the good people to come to the aid of\n" +
                "their party. Now is the time for all good people to come to the aid of their party. Now is the time for\n" +
                "each good person to come to the aid of their party. Now is the time for all good people to come to the aid\n" +
                "of their party. Now is the time for all good Republicans to come to the aid of their party. Now is the\n" +
                "time for all good people to come to the aid of their party. Now is the time for many or all good people to\n" +
                "come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now\n" +
                "is the time for all good Democrats to come to the aid of their party. Now is the time for all people to\n" +
                "come to the aid of their party. Now is the time for all good people to come to the aid of their party. Now\n" +
                "is the time for many good people to come to the aid of their party. Now is the time for all good people to\n" +
                "come to the aid of their party. Now is the time for a lot of good people to come to the aid of their\n" +
                "party. Now is the time for all of the good people to come to the aid of their party. Now is the time for\n" +
                "all good people to come to the aid of their attack at dawn party. Now is the time for each person to come\n" +
                "to the aid of their party. Now is the time for all good people to come to the aid of their party. Now is\n" +
                "the time for all good Republicans to come to the aid of their party. Now is the time for all good people\n" +
                "to come to the aid of their party. Now is the time for many or all good people to come to the aid of their\n" +
                "party. Now is the time for all good people to come to the aid of their party. Now is the time for all good\n" +
                "Democrats to come to the aid of their party.";
        
        String pattern = "attack at dawn";
        
        int index = KMPDFA.search(text, pattern);
        assertEquals(index, text.indexOf(pattern));
        
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.print("pattern: ");
            System.out.flush();
            pattern = scanner.nextLine();
            index = KMPDFA.search(text, pattern);
            assertEquals(index, text.indexOf(pattern));
            System.out.printf("index: %d\n\n", index);
        } while (scanner.hasNext());
    }
    
   
}
