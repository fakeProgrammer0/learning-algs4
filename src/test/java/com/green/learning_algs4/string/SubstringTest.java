package com.green.learning_algs4.string;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class SubstringTest
{
    
    
    @Test
    void test1()
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
        do{
            System.out.print("pattern: ");
            pattern = scanner.nextLine();
            index = KMP.search(text, pattern);
            assertEquals(index,text.indexOf(pattern));
            System.out.printf("index: %d\n\n", index);
        }while (scanner.hasNext());
    }
    
    public static void main(String[]args)
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
        do{
            System.out.print("pattern: ");
            System.out.flush();
            pattern = scanner.nextLine();
            index = KMP.search(text, pattern);
            assertEquals(index,text.indexOf(pattern));
            System.out.printf("index: %d\n\n", index);
        }while (scanner.hasNext());
    }
}
