package com.green.learning.algs4.string.regexp;

import com.green.learning.algs4.util.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

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
    
    @DisplayName("test multi-way or")
    @Test
    void test5()
    {
        String regexp = ".*AB((C|D|E)F)*G";
        RegExpNFA pattern = new RegExpNFA(regexp);
        
        assertTrue(pattern.match("ABG"));
        assertTrue(pattern.match("ABCFG"));
        assertTrue(pattern.match("ABDFG"));
        assertTrue(pattern.match("ABEFG"));
    }
    
    @DisplayName("test ?")
    @Test
    void test6()
    {
        String regexp = ".*AB?((C|D|E)F)*G";
        RegExpNFA pattern = new RegExpNFA(regexp);
        
        assertTrue(pattern.match("AG"));
        assertTrue(pattern.match("ABG"));
        assertTrue(pattern.match("ABCFG"));
        assertTrue(pattern.match("ABDFG"));
        assertTrue(pattern.match("ABEFG"));
        assertTrue(pattern.match("AEFCFG"));
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
    
    @Test
    void testGrep2() throws FileNotFoundException
    {
        File ipFile = FileUtils.getFile("string", "ip.csv");
        
        String[] regexps = {
                "com|net",
                "(net)",
                "(com|net)",
                "?com|net",
                "com.*80",
                "1?63",
        };
        
        for(String regexp: regexps)
        {
            System.out.println("grep: \"" + regexp + "\"");
            Grep grep = new Grep(regexp);
            Scanner scanner = new Scanner(ipFile);
            grep.grepLine(scanner);
            System.out.println();
        }
    }
    
    @Test
    void testJDKPattern()
    {
        Pattern pattern1 = Pattern.compile(".*com|net.*");
        Pattern pattern2 = Pattern.compile(".*(com|net).*");
        
        Pattern pattern3 = Pattern.compile(".*?com|net.*");
        Pattern pattern4 = Pattern.compile(".*?(com|net).*");
        
        Pattern pattern5 = Pattern.compile(".*?com|net.*?");
        Pattern pattern6 = Pattern.compile(".*?(com|net).*?");
        
        String text = "fastclick.com,205.180.86.4";
        assertFalse(pattern1.matcher(text).matches());
        assertTrue(pattern2.matcher(text).matches());
        
        assertFalse(pattern3.matcher(text).matches());
        assertTrue(pattern4.matcher(text).matches());
        
        assertFalse(pattern5.matcher(text).matches());
        assertTrue(pattern6.matcher(text).matches());
    }
    
    @Test
    void testGreedy()
    {
        assertTrue(new RegExpNFA(".?").match("A"));
        assertFalse(new RegExpNFA(".?").match("AA"));
    
        assertTrue(Pattern.compile("A+?").matcher("A").matches());
        assertTrue(Pattern.compile("A+?").matcher("AA").matches());
        
        assertTrue(new RegExpNFA("A+?").match("A"));
        assertFalse(new RegExpNFA("A+?").match("AA"));
    
        
    
        assertFalse(new RegExpNFA(".*?").match("A"));
        assertFalse(new RegExpNFA(".*?").match("AA"));
    }
}