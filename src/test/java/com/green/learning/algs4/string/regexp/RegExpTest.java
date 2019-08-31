package com.green.learning.algs4.string.regexp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegExpTest
{
    @Test
    void test1()
    {
        String regexp = "(A|B)*A";
        RegExpNFA pattern = new RegExpNFA(regexp);
        
        assertTrue(pattern.match("AAAA"));
        assertTrue(pattern.match("AAABABABAA"));
        assertFalse(pattern.match("AAABABABAAB"));
    }
    
    @Test
    void test3()
    {
        String regexp = "(AB)*A";
        RegExpNFA pattern = new RegExpNFA(regexp);
        
        assertFalse(pattern.match("AAAA"));
        assertTrue(pattern.match("ABA"));
        assertFalse(pattern.match("ABBBBA"));
        assertTrue(pattern.match("A"));
    }
    
    @Test
    void test4()
    {
        String regexp = "A(AB)*";
        RegExpNFA pattern = new RegExpNFA(regexp);
        
        assertFalse(pattern.match("A)"));
    }
    
    @Test
    void test2()
    {
        String regexp = "A+B";
        RegExpNFA pattern = new RegExpNFA(regexp);
        
        assertFalse(pattern.match("A"));
        assertFalse(pattern.match("B"));
        assertTrue(pattern.match("AB"));
    }
    
    @Test
    void testGrep1()
    {
        String regexp = "201(8|9).*12";
        RegExpNFA pattern = new RegExpNFA(regexp);
        Grep grep = new Grep(regexp);
        
        String text1 = "2018 12";
        assertTrue(pattern.match(text1));
        assertTrue(grep.search(text1));
    
        String text2 = "2018 12 25";
        assertFalse(pattern.match(text2));
        assertTrue(grep.search(text2));
    
        String text3 = "2019 12 21 - 2019 12 22";
        assertFalse(pattern.match(text3));
        assertTrue(grep.search(text3));
    
        String text4 = "2019 09 01 - 2019 12 20";
        assertFalse(pattern.match(text4));
        assertTrue(grep.search(text4));
    }
}