package com.green.learning.algs4.string.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoyerMooreTest
{
    @Test
    void test1()
    {
        String pattern = "BAOBAB";
        String text = "BESS_KNEW_ABOUT_BAOBABS";
        int index = text.indexOf(pattern);
        assertEquals(index, BoyerMoore.search(text,pattern));
        assertEquals(index, BoyerMooreX.search(text,pattern));
    }
    
    @Test
    void test2()
    {
        String pattern = "EXAMPLE";
        String text = "HERE IS A SIMPLE EXAMPLE";
        int index = text.indexOf(pattern);
        assertEquals(index, BoyerMoore.search(text,pattern));
        assertEquals(index, BoyerMooreX.search(text,pattern));
    }
}