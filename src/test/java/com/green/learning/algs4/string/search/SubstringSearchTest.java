package com.green.learning.algs4.string.search;

import com.green.learning.algs4.string.search.BoyerMoore;
import com.green.learning.algs4.string.search.BoyerMooreX;
import com.green.learning.algs4.string.search.Horspool;
import com.green.learning.algs4.string.search.KMP;
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
            int index = KMP.search(text, pattern);
            assertEquals(index, text.indexOf(pattern));
            assertEquals(index, BoyerMoore.search(text,pattern));
            assertEquals(index, Horspool.search(text,pattern));
            System.out.printf("index: %d\n\n", index);
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
        
        int index = KMP.search(text, pattern);
        assertEquals(index, text.indexOf(pattern));
        
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.print("pattern: ");
            System.out.flush();
            pattern = scanner.nextLine();
            index = KMP.search(text, pattern);
            assertEquals(index, text.indexOf(pattern));
            System.out.printf("index: %d\n\n", index);
        } while (scanner.hasNext());
    }
    
    @Test
    void testBuildGoodSuffixShifts()
    {
        String pat1 = "ABCBAB";
        int[] goodSuffixShifts = BoyerMooreX.buildGoodSuffixShifts(pat1);
        assertEquals(2, goodSuffixShifts[4]);
        assertEquals(4, goodSuffixShifts[3]);
        assertEquals(4, goodSuffixShifts[2]);
        assertEquals(4, goodSuffixShifts[1]);
        assertEquals(4, goodSuffixShifts[0]);
    }
    
    @Test
    void test2()
    {
        String pat1 = "BAOBAB";
        int[] goodSuffix = BoyerMooreX.buildGoodSuffixShifts(pat1);
        assertEquals(2, goodSuffix[4]);
        assertEquals(5, goodSuffix[3]);
        assertEquals(5, goodSuffix[2]);
        assertEquals(5, goodSuffix[1]);
        assertEquals(5, goodSuffix[0]);
    }
    
    @Test
    void testBoyerMoore1()
    {
        String pattern = "BAOBAB";
        String text = "BESS_KNEW_ABOUT_BAOBABS";
        assertEquals(text.indexOf(pattern), BoyerMoore.search(text,pattern));
        assertEquals(text.indexOf(pattern), BoyerMooreX.search(text,pattern));
    }
    
    @Test
    void testBoyerMoore2()
    {
        String pattern = "EXAMPLE";
        String text = "HERE IS A SIMPLE EXAMPLE";
        assertEquals(text.indexOf(pattern), BoyerMoore.search(text,pattern));
        assertEquals(text.indexOf(pattern), BoyerMooreX.search(text,pattern));
    }
}
