package com.green.learning.algs4.list;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Logger;

public class XStackTest
{
    private Logger logger = Logger.getLogger(XStackTest.class.getName());
    
    @Test
    void simpleTest()
    {
//        XStack<Integer> stack = new XArrayStack<>();
        XStack<Integer> stack = new XLinkedStack<>();
        final int N = 128;
        final int count = 1024;
        Random random = new Random();
        for(int i = 0; i < count; i++)
        {
            if(StdRandom.bernoulli(0.6))
                stack.push(random.nextInt(N));
            else stack.push(null);
        }
        logger.info(stack.toString());
        Assertions.assertEquals(count, stack.size());
    
        Iterator<Integer> iterator = stack.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next() + " ");
        System.out.println();
        
        for(int i = 0; i < count / 2; i++)
            System.out.print(stack.pop() + " ");
        Assertions.assertFalse(stack.isEmpty());
        stack.clear();
        Assertions.assertFalse(stack.iterator()::hasNext);
        Assertions.assertEquals(0, stack.size());
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertThrows(NoSuchElementException.class, stack::peek);
        Assertions.assertThrows(NoSuchElementException.class, stack::pop);
    }
    
    @Test
    void checkExpansionShrink()
    {
        final int N = 200;
        XArrayStack<Integer> stack = new XArrayStack<>();
        int capacity = stack.getCapacity();
        int from = 0, to;
        for (int i = 0; i < N; i++)
        {
            if (StdRandom.bernoulli(0.2))
                stack.push(null);
            else stack.push(i);
            
            System.out.printf("%d: capacity = %d\n", i, stack.getCapacity());
            
            if (i > 0 && capacity != stack.getCapacity())
            {
                to = i - 1;
                System.out.printf("from %d to %d, capacity = %d\n", from, to, capacity);
                capacity = stack.getCapacity();
                from = i;
            }
        }
        
        stack.trimToSize();
        Assertions.assertEquals(stack.size(), stack.getCapacity());
        
        stack.setAutoShrink(true);
        Assertions.assertTrue(stack.isAutoShrink());
        
        while (!stack.isEmpty())
        {
            stack.pop();
            System.out.printf("size = %d, capacity = %d\n", stack.size(), stack.getCapacity());
        }
    }
}
