package com.green.learning.algs4.list;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class XBagTest
{
    @Test
    public void simpleTest()
    {
        XBag<Integer> XBag = new XBag<>();
        Assertions.assertEquals(0, XBag.size());
        Assertions.assertTrue(XBag.isEmpty());
        System.out.println(XBag);
        
        Assertions.assertFalse(XBag.contains(0));
        
        final int N = 100;
        for (int i = 0; i < N; i++)
        {
            if(StdRandom.bernoulli(0.6))
                XBag.add(i);
            else XBag.add(null);
        }
        
        Assertions.assertFalse(XBag.contains(N));
        Assertions.assertTrue(XBag.contains(null));
        Assertions.assertEquals(N, XBag.size());
        Assertions.assertFalse(XBag.isEmpty());
        System.out.println(XBag);
    
        XBag.add(555);
        Assertions.assertTrue(XBag.contains(555));
        
        for (Integer i : XBag)
            System.out.print(i + " ");
        
    }
}
