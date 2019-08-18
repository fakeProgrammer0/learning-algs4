package com.green.learning.algs4.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlphabetTest
{
    @Test
    void printAlphabet()
    {
//        Alphabet alphabet = Alphabet.BASE64();
        Alphabet alphabet = Alphabet.UPPERCASE();
        
        for(int r = 0; r < alphabet.radix(); r++)
            System.out.printf("%d : %c\n", r, alphabet.toChar(r));
    }
    
    @Test
    void testEmptyTokens()
    {
       Alphabet alphabet = Alphabet.BINARY();
       
       // empty token to 0 length indices
       assertEquals(0, alphabet.toIndices("").length);
       assertEquals(0, alphabet.toIndices(new char[0]).length);
       
       // 0 length indices to empty string
       assertEquals(0, alphabet.toChars(new int[0]).length);
       assertEquals("", alphabet.toString(new int[0]));
       
       assertEquals("", new String(new char[0]));
    }
}
