package com.green.learning.algs4.list;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class XQueueTest
{
    @Test
    public void simpleTest()
    {
//        XQueue<Integer> queue = new XArrayQueue<>();
//        XQueue<Integer> queue = new XLinkedQueue<>();
        XQueue<Integer> queue = new XLinkedQueueX<>();
        
//        final int N = 10000;
        final int N = 1000;
//        final int N = 16;
        
        for(int i = 0; i < N; i++)
        {
            if(StdRandom.bernoulli(0.6))
                queue.enqueue(i);
            else queue.enqueue(null);
        }
        
//        System.out.println(queue);
        Assertions.assertEquals(N, queue.size());
        
        Iterator<Integer> iterator = queue.iterator();
        Assertions.assertThrows(UnsupportedOperationException.class, iterator::remove);
        int counter = 0;
        while (iterator.hasNext())
        {
            Integer c = iterator.next();
            Assertions.assertTrue(c == null || c == counter);
            counter++;
        }
        
        for(int i = 0; i < N / 2; i++)
        {
            Integer c = queue.peek();
            Assertions.assertTrue(c == null || c == i );
            System.out.print(c + " ");
            queue.dequeue();
        }
        System.out.println();
    
        iterator = queue.iterator();
        Assertions.assertTrue(iterator.hasNext());
        
        while (!queue.isEmpty())
            queue.dequeue();
        
        queue.enqueue(null);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        queue.clear();
        System.out.println(queue);
        
        iterator = queue.iterator();
        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertTrue(queue.isEmpty());
        Assertions.assertEquals(0, queue.size());
        Assertions.assertThrows(NoSuchElementException.class, queue::peek);
        Assertions.assertThrows(NoSuchElementException.class, queue::dequeue);
    }
    
    @Test
    void checkExpansionShrink()
    {
        final int N = 200;
        XArrayQueue<Integer> queue = new XArrayQueue<>();
        int capacity = queue.getCapacity();
        int from = 0, to;
        for (int i = 0; i < N; i++)
        {
            if (StdRandom.bernoulli(0.2))
                queue.enqueue(null);
            else queue.enqueue(i);
            
            System.out.printf("%d: capacity = %d\n", i, queue.getCapacity());
            
            if (i > 0 && capacity != queue.getCapacity())
            {
                to = i - 1;
                System.out.printf("from %d to %d, capacity = %d\n", from, to, capacity);
                capacity = queue.getCapacity();
                from = i;
            }
        }
        
        queue.trimToSize();
        Assertions.assertEquals(queue.size(), queue.getCapacity());
        
        queue.setAutoShrink(true);
        Assertions.assertTrue(queue.isAutoShrink());
        
        while (!queue.isEmpty())
        {
            queue.dequeue();
            System.out.printf("size = %d, capacity = %d\n", queue.size(), queue.getCapacity());
        }
    }
}
