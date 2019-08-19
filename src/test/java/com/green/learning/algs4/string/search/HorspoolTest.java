package com.green.learning.algs4.string.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HorspoolTest
{
    @Test
    void test1()
    {
        String text = "JIM_SAW_ME_IN_A_BARBERSHOP";
        String pattern = "BARBER";
        assertEquals(text.indexOf(pattern),  Horspool.search(text, pattern));
    }
}